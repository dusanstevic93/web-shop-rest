package com.dusan.webshop.api.controller;

import com.dusan.webshop.dto.request.CreateOrderRequest;
import com.dusan.webshop.dto.request.UpdateOrderStatusRequest;
import com.dusan.webshop.dto.response.OrderItemResponse;
import com.dusan.webshop.dto.response.OrderResponse;
import com.dusan.webshop.entity.enums.OrderStatus;
import com.dusan.webshop.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest extends ControllerTestSetup {

    @MockBean
    private OrderService orderService;

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void createOrderCustomer() throws Exception {
        CreateOrderRequest request = new CreateOrderRequest();
        request.setShippingStreet("shipping street");
        request.setShippingZipCode("shipping zip");
        request.setShippingCity("shipping city");
        request.setBillingStreet("billing street");
        request.setBillingZipCode("billing zip");
        request.setBillingCity("billing city");
        request.setOrderedProducts(new ArrayList<>());

        String json = new ObjectMapper().writeValueAsString(request);

        mvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createOrderAdmin() throws Exception {
        mvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void createOrderAnonymousUser() throws Exception {
        mvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateOrderStatusAdmin() throws Exception {
        UpdateOrderStatusRequest request = new UpdateOrderStatusRequest();
        request.setOrderStatus(OrderStatus.SHIPPED);

        String json = new ObjectMapper().writeValueAsString(request);

        mvc.perform(put("/orders/1/status").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void updateOrderStatusCustomer() throws Exception {
        mvc.perform(put("/orders/1/status").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void updateOrderStatusAnonymousUser() throws Exception {
        mvc.perform(put("/orders/1/status").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllOrdersAdmin() throws Exception {
        Page<OrderResponse> orders = new PageImpl<>(Collections.emptyList());
        given(orderService.findAllOrders(any(), any())).willReturn(orders);

        mvc.perform(get("/orders").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void getAllOrdersCustomer() throws Exception {
        mvc.perform(get("/orders").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void getAllOrdersAnonymousUser() throws Exception {
        mvc.perform(get("/orders").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getOrderAdmin() throws Exception {
        OrderResponse response = new OrderResponse();
        given(orderService.findOrderById(anyLong())).willReturn(response);

        mvc.perform(get("/orders/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void getOrderCustomer() throws Exception {
        mvc.perform(get("/orders/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void getOrderAnonymousUser() throws Exception {
        mvc.perform(post("/orders/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getOrderItemsAdmin() throws Exception {
        List<OrderItemResponse> orderItems = new ArrayList<>();
        given(orderService.findAllOrderItems(anyLong())).willReturn(orderItems);

        mvc.perform(get("/orders/1/items").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void getOrderItemsCustomer() throws Exception {
        mvc.perform(get("/orders/1/items").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void getOrderItemsAnonymousUser() throws Exception {
        mvc.perform(get("/orders/1/items").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getCustomerOrdersAdmin() throws Exception {
        Page<OrderResponse> orders = new PageImpl<>(Collections.emptyList());
        given(orderService.findAllCustomerOrders(anyLong(), any(), any())).willReturn(orders);

        mvc.perform(get("/customers/1/orders").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void getCustomerOrdersCustomer() throws Exception {
        mvc.perform(get("/customers/1/orders").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void getCustomerOrdersAnonymousUser() throws Exception {
        mvc.perform(get("/customers/1/orders").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}