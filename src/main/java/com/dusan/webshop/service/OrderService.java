package com.dusan.webshop.service;

import com.dusan.webshop.dto.request.CreateOrderRequest;
import com.dusan.webshop.dto.request.UpdateOrderStatusRequest;
import com.dusan.webshop.dto.response.OrderResponse;


public interface OrderService {

    void createOrder(long customerId, CreateOrderRequest request);
    void updateOrderStatus(long orderId, UpdateOrderStatusRequest request);
    OrderResponse findOrderById(long orderId);
}
