package com.dusan.webshop.dao;

import com.dusan.webshop.entity.Address;
import com.dusan.webshop.entity.Customer;
import com.dusan.webshop.entity.Order;
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
    private UserRepository userRepository;

    @Test
    @Sql({"classpath:scripts/insert-roles.sql", "classpath:scripts/insert-customer.sql"})
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

        Customer customer = (Customer) userRepository.findById(1L).get();
        order.setCustomer(customer);

        // when
        Order savedOrder = orderRepository.saveAndFlush(order);

        // then
        assertNotNull(savedOrder.getId());
    }
}