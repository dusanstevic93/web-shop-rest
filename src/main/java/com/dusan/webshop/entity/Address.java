package com.dusan.webshop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class Address {

    private String street;
    private String zipCode;
    private String city;
}
