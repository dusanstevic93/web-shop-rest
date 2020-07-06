package com.dusan.webshop.service.impl;

import com.dusan.webshop.dao.CustomerRepository;
import com.dusan.webshop.dao.OrderItemRepository;
import com.dusan.webshop.dao.OrderRepository;
import com.dusan.webshop.dao.ProductRepository;
import com.dusan.webshop.dto.request.*;
import com.dusan.webshop.dto.request.params.OrderFilterParams;
import com.dusan.webshop.dto.request.params.OrderPageParams;
import com.dusan.webshop.dto.response.OrderItemResponse;
import com.dusan.webshop.dto.response.OrderResponse;
import com.dusan.webshop.entity.*;
import com.dusan.webshop.entity.enums.OrderStatus;
import com.dusan.webshop.entity.specification.CustomerOrderSpecification;
import com.dusan.webshop.entity.specification.OrderSpecification;
import com.dusan.webshop.service.OrderService;
import com.dusan.webshop.service.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private ProductRepository productRepository;
    private CustomerRepository customerRepository;

    @Override
    @Transactional
    @Lock(LockModeType.OPTIMISTIC)
    public void createOrder(long customerId, CreateOrderRequest request) {
        checkIfCustomerExists(customerId);

        Order order = new Order();
        order.setCustomer(customerRepository.getOne(customerId));
        order.setCreationDate(LocalDate.now());
        order.setStatus(OrderStatus.NEW);

        // get shipping address form the request
        Address shippingAddress = new Address();
        shippingAddress.setStreet(request.getShippingStreet());
        shippingAddress.setZipCode(request.getShippingZipCode());
        shippingAddress.setCity(request.getShippingCity());
        order.setShippingAddress(shippingAddress);

        // get billing address from the request
        Address billingAddress = new Address();
        billingAddress.setStreet(request.getBillingStreet());
        billingAddress.setZipCode(request.getBillingZipCode());
        billingAddress.setCity(request.getBillingCity());
        order.setBillingAddress(billingAddress);

        // set order items
        BigDecimal totalValue = new BigDecimal(0);
        BigDecimal totalWeight = new BigDecimal(0);
        List<OrderProduct> orderedProducts = request.getOrderedProducts();

        for (OrderProduct orderProduct : orderedProducts) {
            Product product = getProductFromDatabase(orderProduct.getProductId());

            // throw exception if product is updated
            if (!orderProduct.getVersion().equals(product.getVersion()))
                throw new RuntimeException("Version conflict");

            totalValue = totalValue.add(product.getPrice());
            totalWeight = totalWeight.add(product.getWeight());

            product.decreaseQuantity(orderProduct.getQuantity());

            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(orderProduct.getQuantity());
            orderItem.setWeight(product.getWeight());
            orderItem.setPrice(product.getPrice());
            orderItem.setProduct(product);
            orderItem.setOrder(order);
            order.addOrderItem(orderItem);
        }

        totalValue = totalValue.setScale(2, RoundingMode.HALF_UP);
        totalWeight = totalWeight.setScale(2, RoundingMode.HALF_UP);

        order.setValue(totalValue);
        order.setTotalWeight(totalWeight);

        orderRepository.save(order);
    }

    private void checkIfCustomerExists(long customerId) {
        boolean exists = customerRepository.existsById(customerId);
        if (!exists)
            throw new ResourceNotFoundException("Customer with id = " + customerId + " do not exist");
    }

    private Product getProductFromDatabase(long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id = " + productId + " does not exist"));
    }

    @Override
    @Transactional
    public void updateOrderStatus(long orderId, UpdateOrderStatusRequest request) {
        checkIfOrderExists(orderId);
        orderRepository.updateOrderStatus(orderId, request.getOrderStatus());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponse> findAllOrders(OrderFilterParams filterParams, OrderPageParams pageParams) {
        Specification<Order> specification = new OrderSpecification(filterParams);
        Pageable pageable = getPageable(pageParams);
        Page<Order> page = orderRepository.findAll(specification, pageable);
        return page.map(this::convertOrderEntityToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse findOrderById(long orderId) {
        Order order = getOrderFromDatabase(orderId);
        return convertOrderEntityToResponse(order);
    }

    private Order getOrderFromDatabase(long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id = " + orderId + " does not exists"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderItemResponse> findAllOrderItems(long orderId) {
        checkIfOrderExists(orderId);
        List<OrderItem> orderItems = orderItemRepository.findAllOrderItemsOfSpecificOrder(orderId);
        return orderItems.stream().map(this::convertOrderItemEntityToResponse).collect(Collectors.toList());
    }

    private void checkIfOrderExists(long orderId) {
        boolean exists = orderRepository.existsById(orderId);
        if (!exists)
            throw new ResourceNotFoundException("Order with id = " + orderId + " does not exist");
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponse> findAllCustomerOrders(long customerId, OrderFilterParams filterParams, OrderPageParams pageParams) {
        Specification<Order> orderSpecification = new OrderSpecification(filterParams)
                .and(new CustomerOrderSpecification(customerId));
        Pageable pageable = getPageable(pageParams);
        Page<Order> orders = orderRepository.findAll(orderSpecification, pageable);
        return orders.map(this::convertOrderEntityToResponse);
    }

    private Pageable getPageable(OrderPageParams pageParams) {
        Sort sort = null;
        switch (pageParams.getSort()) {
            case DATE: sort = Sort.by(pageParams.getDirection(), "creationDate");
            break;
            case VALUE: sort = Sort.by(pageParams.getDirection(), "value");
            break;
            case WEIGHT: sort = Sort.by(pageParams.getDirection(), "totalWeight");
            break;
            case UNSORTED: sort = Sort.unsorted();
            break;
        }
        return PageRequest.of(pageParams.getPage(), pageParams.getSize(), sort);
    }

    private OrderResponse convertOrderEntityToResponse(Order order) {
        OrderResponse response = new OrderResponse();

        response.setCustomerId(order.getCustomer().getId());

        BeanUtils.copyProperties(order, response);

        Address shippingAddress = order.getShippingAddress();
        response.setShippingStreet(shippingAddress.getStreet());
        response.setShippingZipCode(shippingAddress.getZipCode());
        response.setShippingCity(shippingAddress.getCity());

        Address billingAddress = order.getBillingAddress();
        response.setBillingStreet(billingAddress.getStreet());
        response.setBillingZipCode(billingAddress.getZipCode());
        response.setBillingCity(billingAddress.getCity());

        return response;
    }

    private OrderItemResponse convertOrderItemEntityToResponse(OrderItem orderItem) {
        OrderItemResponse response = new OrderItemResponse();
        response.setProductId(orderItem.getProduct().getId());
        response.setPrice(orderItem.getPrice());
        response.setWeight(orderItem.getWeight());
        response.setQuantity(orderItem.getQuantity());
        return response;
    }
}
