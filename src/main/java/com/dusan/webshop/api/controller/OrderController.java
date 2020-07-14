package com.dusan.webshop.api.controller;

import com.dusan.webshop.dto.request.CreateOrderRequest;
import com.dusan.webshop.dto.request.params.OrderFilterParams;
import com.dusan.webshop.dto.request.params.OrderPageParams;
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
import org.springframework.security.core.Authentication;
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
    public void createOrder(@Valid @RequestBody CreateOrderRequest request, Authentication auth) {
        long customerId = (long) auth.getPrincipal();
        orderService.createOrder(customerId, request);
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

    @GetMapping(value = "/customers/me/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponseWrapper<OrderResponse> getAuthenticatedCustomerOrders(
            @ParameterObject OrderFilterParams filterParams,
            @ParameterObject OrderPageParams pageParams,
            Authentication auth) {
        long customerId = (long) auth.getPrincipal();
        Page<OrderResponse> page = orderService.findAllCustomerOrders(customerId, filterParams, pageParams);
        return ControllerUtils.createPageResponseWrapper(page);
    }

    @GetMapping(value = "/customers/me/orders/{orderId}/items")
    public List<OrderItemResponse> getAuthenticatedCustomerOrderItems(@PathVariable long orderId, Authentication auth) {
        long customerId = (long) auth.getPrincipal();
        return orderService.findAllOrderItems(customerId, orderId);
    }
}
