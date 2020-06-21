package com.dusan.webshop.entity;

import com.dusan.webshop.entity.enums.OrderStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "customer_order")
public class Order {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_sequence")
    @SequenceGenerator(name = "order_id_sequence", sequenceName = "order_id_sequence", allocationSize = 1)
    private Long id;

    private LocalDate creationDate;

    private BigDecimal value;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @AttributeOverride(name = "street", column = @Column(name = "shipping_street"))
    @AttributeOverride(name = "zipCode", column = @Column(name = "shipping_zip_code"))
    @AttributeOverride(name = "city", column = @Column(name = "shipping_city"))
    private Address shippingAddress;

    @AttributeOverride(name = "street", column = @Column(name = "billing_street"))
    @AttributeOverride(name = "zipCode", column = @Column(name = "billing_zip_code"))
    @AttributeOverride(name = "city", column = @Column(name = "billing_city"))
    private Address billingAddress;

    @ManyToOne
    private Customer customer;
}
