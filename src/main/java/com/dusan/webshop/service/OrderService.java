package com.dusan.webshop.service;

import com.dusan.webshop.dto.request.CreateOrderRequest;
import com.dusan.webshop.dto.request.params.OrderFilterParams;
import com.dusan.webshop.dto.request.params.OrderPageParams;
import com.dusan.webshop.dto.request.UpdateOrderStatusRequest;
import com.dusan.webshop.dto.response.OrderItemResponse;
import com.dusan.webshop.dto.response.OrderResponse;
import org.springframework.data.domain.Page;

import java.util.List;


public interface OrderService {

    void createOrder(long customerId, CreateOrderRequest request);
    void updateOrderStatus(long orderId, UpdateOrderStatusRequest request);
    Page<OrderResponse> findAllOrders(OrderFilterParams filterParams, OrderPageParams pageParams);
    OrderResponse findOrderById(long orderId);
    List<OrderItemResponse> findAllOrderItems(long orderId);
    List<OrderItemResponse> findAllOrderItems(long customerId, long orderId);
    Page<OrderResponse> findAllCustomerOrders(long customerId, OrderFilterParams filterParams, OrderPageParams pageParams);
}
