package com.dusan.webshop.api.controller;

import com.dusan.webshop.dto.request.CreateOrderRequest;
import com.dusan.webshop.dto.request.OrderFilterParams;
import com.dusan.webshop.dto.request.OrderPageParams;
import com.dusan.webshop.dto.request.UpdateOrderStatusRequest;
import com.dusan.webshop.dto.response.OrderItemResponse;
import com.dusan.webshop.dto.response.OrderResponse;
import com.dusan.webshop.dto.response.PageResponseWrapper;
import com.dusan.webshop.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Tag(name = "Order")
@AllArgsConstructor
@RestController
public class OrderController {

    private OrderService orderService;

    @PostMapping(value = "/orders", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@Valid @RequestBody CreateOrderRequest request) {
        orderService.createOrder(1, request);
    }

    @PutMapping(value = "/orders/{orderId}/status", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateOrderStatus(@PathVariable long orderId, @Valid @RequestBody UpdateOrderStatusRequest request) {
        orderService.updateOrderStatus(orderId, request);
    }

    @GetMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponseWrapper<OrderResponse> getAllOrders(
            @ParameterObject OrderFilterParams filterParams,
            @ParameterObject OrderPageParams pageParams) {
        Page<OrderResponse> page = orderService.findAllOrders(filterParams, pageParams);
        return ControllerUtils.createPageResponseWrapper(page);
    }

    @GetMapping(value = "/orders/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderResponse getOrder(@PathVariable long orderId) {
        return orderService.findOrderById(orderId);
    }

    @GetMapping(value = "/orders/{orderId}/items", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderItemResponse> getOrderItems(@PathVariable long orderId) {
        return orderService.findAllOrderItems(orderId);
    }

    @GetMapping(value = "/customers/{customerId}/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponseWrapper<OrderResponse> getCustomerOrders(
            @PathVariable long customerId,
            @ParameterObject  OrderFilterParams filterParams,
            @ParameterObject OrderPageParams pageParams) {
        Page<OrderResponse> page = orderService.findAllCustomerOrders(customerId, filterParams, pageParams);
        return ControllerUtils.createPageResponseWrapper(page);
    }
}
