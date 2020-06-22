package com.dusan.webshop.dao;

import com.dusan.webshop.entity.Address;
import com.dusan.webshop.entity.Customer;
import com.dusan.webshop.entity.Order;
import com.dusan.webshop.entity.OrderItem;
import com.dusan.webshop.entity.enums.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @Sql("classpath:scripts/create-order-setup.sql")
    void testSaveOrder() {
        // given
        Order order = new Order();
        order.setCreationDate(LocalDate.now());
        order.setValue(new BigDecimal(5000));
        order.setStatus(OrderStatus.NEW);

        Address shippingAddress = new Address();
        shippingAddress.setStreet("Shipping street 41");
        shippingAddress.setZipCode("123");
        shippingAddress.setCity("Shipping city");
        order.setShippingAddress(shippingAddress);

        Address billingAddress = new Address();
        billingAddress.setStreet("Billing street 25");
        billingAddress.setZipCode("321");
        billingAddress.setCity("Billing city");
        order.setBillingAddress(billingAddress);

        Customer customer = customerRepository.getOne(1L);
        order.setCustomer(customer);

        OrderItem item1 = new OrderItem();
        item1.setProduct(productRepository.getOne(1L));
        item1.setOrder(order);
        item1.setPrice(new BigDecimal(500));
        item1.setWeight(new BigDecimal(0.5));
        item1.setQuantity(10);
        order.addOrderItem(item1);

        OrderItem item2 = new OrderItem();
        item2.setProduct(productRepository.getOne(2L));
        item2.setOrder(order);
        item2.setPrice(new BigDecimal(500));
        item2.setWeight(new BigDecimal(0.5));
        item2.setQuantity(10);
        order.addOrderItem(item2);

        // when
        Order savedOrder = orderRepository.saveAndFlush(order);

        // then
        assertNotNull(savedOrder.getId());
    }
}