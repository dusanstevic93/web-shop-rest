package com.dusan.webshop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Customer extends User {

    private String phone;
    private Address address;
}
