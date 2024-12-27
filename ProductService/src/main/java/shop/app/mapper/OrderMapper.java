package shop.app.mapper;

import org.springframework.stereotype.Component;
import shop.app.dto.ProductInfo;
import shop.app.dto.order.CreateOrderRequest;
import shop.app.dto.order.OrderResponseDto;
import shop.app.dto.order.OrderedProductsDto;
import shop.app.dto.product.UpdateProductInfo;
import shop.app.entity.*;
import shop.app.repository.projection.ProductsView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    public OrderEntity fillOrderEntity(CustomerEntity customerEntity, CreateOrderRequest orderRequest) {
        return OrderEntity.builder()
                .uuid(null)
                .customer(customerEntity)
                .deliveryAddress(orderRequest.getDeliveryAddress())
                .orderStatus(OrderStatus.CREATED)
                .createdAt(LocalDate.now())
                .build();
    }

    public OrderedProductEntity fillOrderedProductEntity(OrderEntity order, ProductInfo product, ProductEntity productEntity) {
        return OrderedProductEntity.builder()
                .id(OrderProductId.builder()
                        .orderId(order.getUuid())
                        .productId(product.getProductId())
                        .build())
                .product(productEntity)
                .order(order)
                .quantity(product.getQuantity())
                .frozenPrice(productEntity.getPrice())
                .build();
    }

    public OrderedProductEntity fillUpdateProductEntity(OrderEntity order, UpdateProductInfo product, ProductEntity productEntity) {
        return OrderedProductEntity.builder()
                .id(OrderProductId.builder()
                        .orderId(order.getUuid())
                        .productId(product.getProductId())
                        .build())
                .quantity(product.getQuantity())
                .product(productEntity)
                .order(order)
                .frozenPrice(productEntity.getPrice())
                .build();
    }

    public OrderResponseDto toOrderResponseDto(List<ProductsView> productsView, UUID orderId) {
        return OrderResponseDto.builder()
                .orderId(orderId)
                .orderedProducts(productsView.stream()
                        .map(p -> OrderedProductsDto.builder()
                                .productId(p.getProductUUID())
                                .name(p.getName())
                                .quantity(p.getQuantity())
                                .price(p.getPrice())
                                .build()).collect(Collectors.toList()))
                .totalPrice(productsView.stream()
                        .map(ProductsView::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .build();
    }
}
