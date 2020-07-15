package com.dusan.webshop.api.controller;

import com.dusan.webshop.api.docs.Descriptions;
import com.dusan.webshop.dto.request.CreateOrderRequest;
import com.dusan.webshop.dto.request.params.OrderFilterParams;
import com.dusan.webshop.dto.request.params.OrderPageParams;
import com.dusan.webshop.dto.request.UpdateOrderStatusRequest;
import com.dusan.webshop.dto.response.OrderItemResponse;
import com.dusan.webshop.dto.response.OrderResponse;
import com.dusan.webshop.dto.response.PageResponseWrapper;
import com.dusan.webshop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Create a new order", description = Descriptions.CREATE_NEW_ORDER,
                responses = {@ApiResponse(responseCode = "201", description = "successful operation")})
    @PostMapping(value = "/orders", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@Valid @RequestBody CreateOrderRequest request, Authentication auth) {
        long customerId = (long) auth.getPrincipal();
        orderService.createOrder(customerId, request);
    }

    @Operation(summary = "Update an order status", description = Descriptions.UPDATE_ORDER_STATUS,
                responses = {@ApiResponse(responseCode = "200", description = "successful operation"),
                             @ApiResponse(responseCode = "403", description = "unauthorized access"),
                             @ApiResponse(responseCode = "404", description = "order not found")}
    )
    @PutMapping(value = "/orders/{orderId}/status", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateOrderStatus(@PathVariable long orderId, @Valid @RequestBody UpdateOrderStatusRequest request) {
        orderService.updateOrderStatus(orderId, request);
    }

    @Operation(summary = "Get all orders", description = Descriptions.GET_ALL_ORDERS,
                responses = {@ApiResponse(responseCode = "200", description = "successful operation"),
                             @ApiResponse(responseCode = "403", description = "unauthorized access")})
    @GetMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponseWrapper<OrderResponse> getAllOrders(
            @ParameterObject OrderFilterParams filterParams,
            @ParameterObject OrderPageParams pageParams) {
        Page<OrderResponse> page = orderService.findAllOrders(filterParams, pageParams);
        return ControllerUtils.createPageResponseWrapper(page);
    }

    @Operation(summary = "Get an order by id", description = Descriptions.GET_ORDER,
                responses = {@ApiResponse(responseCode = "200", description = "successful operation"),
                             @ApiResponse(responseCode = "403", description = "unauthorized access"),
                             @ApiResponse(responseCode = "404", description = "order is not found")})
    @GetMapping(value = "/orders/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderResponse getOrder(@PathVariable long orderId) {
        return orderService.findOrderById(orderId);
    }

    @Operation(summary = "Get order items", description = Descriptions.GET_ORDER_ITEMS,
                responses = {@ApiResponse(responseCode = "200", description = "successful operation"),
                             @ApiResponse(responseCode = "403", description = "unauthorized access"),
                             @ApiResponse(responseCode = "404", description = "order is not found")})
    @GetMapping(value = "/orders/{orderId}/items", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderItemResponse> getOrderItems(@PathVariable long orderId) {
        return orderService.findAllOrderItems(orderId);
    }

    @Operation(summary = "Get all orders of specific customer", description = Descriptions.GET_CUSTOMER_ORDERS,
                responses = {@ApiResponse(responseCode = "200", description = "successful operation"),
                             @ApiResponse(responseCode = "403", description = "unauthorized access")})
    @GetMapping(value = "/customers/{customerId}/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponseWrapper<OrderResponse> getCustomerOrders(
            @PathVariable long customerId,
            @ParameterObject  OrderFilterParams filterParams,
            @ParameterObject OrderPageParams pageParams) {
        Page<OrderResponse> page = orderService.findAllCustomerOrders(customerId, filterParams, pageParams);
        return ControllerUtils.createPageResponseWrapper(page);
    }

    @Operation(summary = "Get all orders of an authenticated customer",
                responses = {@ApiResponse(responseCode = "200", description = "successful operation"),
                             @ApiResponse(responseCode = "403", description = "unauthorized access")})
    @GetMapping(value = "/customers/me/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponseWrapper<OrderResponse> getAuthenticatedCustomerOrders(
            @ParameterObject OrderFilterParams filterParams,
            @ParameterObject OrderPageParams pageParams,
            Authentication auth) {
        long customerId = (long) auth.getPrincipal();
        Page<OrderResponse> page = orderService.findAllCustomerOrders(customerId, filterParams, pageParams);
        return ControllerUtils.createPageResponseWrapper(page);
    }

    @Operation(summary = "Get order items of an authenticated customer order",
                responses = {@ApiResponse(responseCode = "200", description = "successful operation"),
                             @ApiResponse(responseCode = "403", description = "unauthorized access")})
    @GetMapping(value = "/customers/me/orders/{orderId}/items")
    public List<OrderItemResponse> getAuthenticatedCustomerOrderItems(@PathVariable long orderId, Authentication auth) {
        long customerId = (long) auth.getPrincipal();
        return orderService.findAllOrderItems(customerId, orderId);
    }
}
