package com.dusan.webshop.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CreateOrderRequest {

    @NotBlank
    private String shippingStreet;

    @NotBlank
    private String shippingZipCode;

    @NotBlank
    private String shippingCity;

    @NotBlank
    private String billingStreet;

    @NotBlank
    private String billingZipCode;

    @NotBlank
    private String billingCity;

    private List<OrderProduct> orderedProducts = new ArrayList<>();
}
