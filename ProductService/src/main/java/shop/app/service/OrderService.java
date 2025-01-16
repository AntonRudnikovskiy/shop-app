package shop.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.app.dto.CustomerInfo;
import shop.app.dto.order.CreateOrderRequest;
import shop.app.dto.order.OrderInfo;
import shop.app.dto.order.OrderResponseDto;
import shop.app.dto.order.StatusRequest;
import shop.app.dto.order.UpdateOrderRequest;
import shop.app.dto.product.ProductInfo;
import shop.app.entity.CustomerEntity;
import shop.app.entity.OrderEntity;
import shop.app.entity.OrderStatus;
import shop.app.entity.OrderedProductEntity;
import shop.app.entity.ProductEntity;
import shop.app.exception.OrderNotFoundException;
import shop.app.exception.OrderUpdateException;
import shop.app.exception.ProductNotFoundException;
import shop.app.exception.UserNotFoundException;
import shop.app.mapper.OrderMapper;
import shop.app.repository.CustomerRepository;
import shop.app.repository.OrderRepository;
import shop.app.repository.ProductRepository;
import shop.app.repository.projection.ProductsView;
import shop.app.validator.OrderValidator;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final OrderMapper orderMapper;
    private final OrderValidator orderValidator;
    private final AccountServiceClient accountServiceClient;
    private final CrmServiceClient crmServiceClient;

    @Transactional
    public UUID createOrder(Long customerId, CreateOrderRequest orderRequest) {
        CustomerEntity customerEntity = customerRepository.findById(customerId)
                .orElseThrow(() -> new UserNotFoundException("This user doesn't exist"));

        OrderEntity orderEntity = orderMapper.fillOrderEntity(customerEntity, orderRequest);
        List<OrderedProductEntity> orderedProduct = orderRequest.getProducts().stream()
                .map(product -> {
                    List<UUID> productIds = orderRequest.getProducts().stream()
                            .map(ProductInfo::getProductId)
                            .collect(Collectors.toList());

                    Map<UUID, ProductEntity> productEntityMap = productRepository.findAllById(productIds).stream()
                            .collect(Collectors.toMap(ProductEntity::getUuid, Function.identity()));

                    ProductEntity productEntity = productEntityMap.get(product.getProductId());
                    orderValidator.validateProduct(productEntity);
                    orderValidator.validateProductAvailability(productEntity);
                    orderValidator.validateProductQuantity(productEntity);
                    productEntity.setQuantity(productEntity.getQuantity() - product.getQuantity());

                    return orderMapper.fillOrderedProductEntity(orderEntity, product, productEntity);
                }).collect(Collectors.toList());

        orderEntity.setOrderedProducts(orderedProduct);
        orderRepository.saveAndFlush(orderEntity);
        return orderEntity.getUuid();
    }

    @Transactional
    public UUID updateOrder(Long customerId, UUID orderId, UpdateOrderRequest orderRequest) {
        OrderEntity orderEntity = orderRepository.findOrderWithProducts(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        orderValidator.validateOrderAccess(customerId, orderEntity);

        if (orderEntity.getOrderStatus() != OrderStatus.CREATED) {
            throw new OrderUpdateException("Order cannot be updated");
        }

        List<OrderedProductEntity> newOrderedProducts = orderRequest.getProducts().stream()
                .map(product -> {
                    List<ProductEntity> productEntities = orderEntity.getOrderedProducts().stream()
                            .map(OrderedProductEntity::getProduct)
                            .collect(Collectors.toList());

                    ProductEntity productEntity = productEntities.stream()
                            .filter(p -> p.getUuid().equals(product.getProductId()))
                            .findFirst()
                            .orElseThrow(() -> new ProductNotFoundException("Product doesn't exist or not available"));

                    boolean isNewProduct = orderEntity.getOrderedProducts().stream()
                            .anyMatch(op -> op.getProduct().getUuid().equals(product.getProductId()));

                    orderValidator.validateProduct(productEntity);
                    orderValidator.validateProductAvailability(productEntity);
                    orderValidator.validateProductQuantity(productEntity);
                    orderValidator.validateNewProduct(isNewProduct);

                    OrderedProductEntity orderedProductEntity = orderMapper.fillUpdateProductEntity(orderEntity,
                            product, productEntity);
                    productEntity.setQuantity(productEntity.getQuantity() - product.getQuantity());
                    return orderedProductEntity;
                }).collect(Collectors.toList());

        orderEntity.getOrderedProducts().addAll(newOrderedProducts);
        orderRepository.save(orderEntity);
        return orderEntity.getUuid();
    }

    @Transactional(readOnly = true)
    public OrderResponseDto getOrderByCustomerId(Long customerId, UUID orderId) {
        OrderEntity orderEntity = orderRepository.findOrderWithProducts(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        orderValidator.validateOrderAccess(customerId, orderEntity);

        List<ProductsView> productsView = orderEntity.getOrderedProducts().stream()
                .map(p -> ProductsView.builder()
                        .productUUID(p.getProduct().getUuid())
                        .name(p.getProduct().getName())
                        .quantity(p.getQuantity())
                        .price(p.getFrozenPrice())
                        .build()
                ).collect(Collectors.toList());
        return orderMapper.toOrderResponseDto(productsView, orderId);
    }

    @Transactional
    public Map<UUID, OrderInfo> getGroupCustomerOrdersByProductId() {
        List<OrderEntity> orderEntity = orderRepository.findAllOrdersByStatusIn(
                List.of(OrderStatus.CREATED, OrderStatus.CONFIRMED));

        List<String> customerLogins = orderEntity.stream()
                .map(order -> order.getCustomer().getLogin())
                .distinct()
                .toList();

        CompletableFuture<Map<String, Integer>> accountNumber = accountServiceClient.getCustomerAccountNumber(customerLogins);
        CompletableFuture<Map<String, Integer>> inn = crmServiceClient.getCustomerAccountNumber(customerLogins);
        return CompletableFuture.allOf(accountNumber, inn).thenApply(result -> orderEntity.stream()
                        .flatMap(order -> order.getOrderedProducts().stream()
                                .map(orderedProduct ->
                                        Map.entry(orderedProduct.getProduct().getUuid(), OrderInfo.builder()
                                                .id(orderedProduct.getOrder().getUuid())
                                                .customer(CustomerInfo.builder()
                                                        .id(orderedProduct.getOrder().getCustomer().getId())
                                                        .accountNumber(accountNumber.join().get(order.getCustomer().getLogin()))
                                                        .email(orderedProduct.getOrder().getCustomer().getEmail())
                                                        .inn(inn.join().get(order.getCustomer().getLogin()))
                                                        .build())
                                                .status(orderedProduct.getOrder().getOrderStatus())
                                                .deliveryAddress(orderedProduct.getOrder().getDeliveryAddress())
                                                .quantity(orderedProduct.getQuantity())
                                                .build())))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
                .join();
    }

    @Transactional
    public void deleteOrder(Long customerId, UUID orderId) {
        OrderEntity orderEntity = orderRepository.findOrderWithProducts(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        orderValidator.validateOrderAccess(customerId, orderEntity);

        if (orderEntity.getOrderStatus() == OrderStatus.CREATED) {
            orderEntity.setOrderStatus(OrderStatus.CANCELLED);

            for (OrderedProductEntity orderedProduct : orderEntity.getOrderedProducts()) {
                ProductEntity productEntity = orderedProduct.getProduct();
                productEntity.setQuantity(productEntity.getQuantity() + orderedProduct.getQuantity());
            }
            productRepository.saveAll(orderEntity.getOrderedProducts()
                    .stream()
                    .map(OrderedProductEntity::getProduct)
                    .collect(Collectors.toList())
            );
        }
    }

    @Transactional
    public void updateOrderStatus(UUID orderId, StatusRequest statusRequest) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        orderEntity.setOrderStatus(statusRequest.getOrderStatus());
    }
}
