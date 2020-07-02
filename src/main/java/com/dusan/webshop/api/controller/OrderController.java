package com.dusan.webshop.api.controller;

import com.dusan.webshop.dto.request.CreateOrderRequest;
import com.dusan.webshop.dto.request.UpdateOrderStatusRequest;
import com.dusan.webshop.dto.response.OrderResponse;
import com.dusan.webshop.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Tag(name = "Order")
@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@Valid @RequestBody CreateOrderRequest request) {
        orderService.createOrder(1, request);
    }

    @PutMapping(value = "/{orderId}/status", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateOrderStatus(@PathVariable long orderId, @Valid @RequestBody UpdateOrderStatusRequest request) {
        orderService.updateOrderStatus(orderId, request);
    }

    @GetMapping(value = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderResponse getOrder(@PathVariable long orderId) {
        return orderService.findOrderById(orderId);
    }
}
