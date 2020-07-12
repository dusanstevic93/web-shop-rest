package com.dusan.webshop.dao;

import com.dusan.webshop.entity.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderItemRepositoryTest {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    @Sql("/scripts/insert-test-data.sql")
    void testFindAllOrderItemsBySpecificOrder() {
        // given
        int expectedNumberOfItems = 1;

        // when
        List<OrderItem> orderItems = orderItemRepository.findAllOrderItemsOfSpecificOrder(0L);

        // then
        assertEquals(expectedNumberOfItems, orderItems.size());
    }
}