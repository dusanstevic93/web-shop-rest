package com.dusan.webshop.dao;

import com.dusan.webshop.entity.Order;
import com.dusan.webshop.entity.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderItemRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    @Sql({"classpath:scripts/create-order-test-data.sql", "classpath:scripts/insert-order.sql"})
    void testFindAllByOrder() {
        // given
        int expectedNumberOfItems = 2;
        Order order = orderRepository.getOne(1L);
        Pageable pageable = PageRequest.of(0, 5);

        // when
        Page<OrderItem> items = orderItemRepository.findAllByOrder(order, pageable);

        // then
        assertEquals(expectedNumberOfItems, items.getContent().size());
    }
}