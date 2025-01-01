package shop.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.app.dto.order.*;
import shop.app.service.OrderService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<UUID> createOrder(@RequestHeader("customerId") Long customerId,
                                            @RequestBody CreateOrderRequest orderResponse
    ) {
        UUID createdOrder = orderService.createOrder(customerId, orderResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @PatchMapping("/order/{orderId}")
    public ResponseEntity<UUID> updateOrder(@RequestHeader("customerId") Long customerId,
                                            @PathVariable("orderId") UUID orderId,
                                            @RequestBody UpdateOrderRequest orderRequest
    ) {
        UUID orderResponse = orderService.updateOrder(customerId, orderId, orderRequest);
        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrder(@RequestHeader("customerId") Long customerId,
                                                     @PathVariable("orderId") UUID orderId
    ) {
        OrderResponseDto orderRequest = orderService.getOrderByCustomerId(customerId, orderId);
        return ResponseEntity.ok(orderRequest);
    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<Void> deleteOrder(@RequestHeader("customerId") Long customerId,
                                            @PathVariable("orderId") UUID orderId
    ) {
        orderService.deleteOrder(customerId, orderId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/order/{orderId}/confirm")
    public ResponseEntity<OrderResponse> createOrder(@RequestHeader("customerId") Long customerId,
                                                     @PathVariable("orderId") UUID orderId
    ) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/order/{orderId}/status")
    public ResponseEntity<StatusRequest> updateOrderStatus(@PathVariable("orderId") UUID orderId,
                                                           @RequestBody StatusRequest statusRequest
    ) {
        orderService.updateOrderStatus(orderId, statusRequest);
        return ResponseEntity.ok().build();
    }
}
