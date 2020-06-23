package com.dusan.webshop.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCustomerRequest {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String street;
    private String zipCode;
    private String city;
}
