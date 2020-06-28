package com.dusan.webshop.api.docs;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Descriptions {

    public static final String CREATE_PRODUCT_BRAND = "Create new product brand. User needs to have ADMIN role to perform this operation";
    public static final String GET_PRODUCT_BRAND = "Find a single product brand by id";
    public static final String GET_ALL_BRANDS = "Get all available product brands";
    public static final String UPDATE_PRODUCT_BRAND = "Update product brand specified by id";
}
