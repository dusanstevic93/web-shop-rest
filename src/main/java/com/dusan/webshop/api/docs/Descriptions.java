package com.dusan.webshop.api.docs;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Descriptions {

    // authentication controller
    public static final String AUTHENTICATE = "Authenticate a user by providing username and password. " +
            "If the authentication is successful, a bearer token is returned in response body.";

    // registration controller
    public static final String REGISTER_CUSTOMER = "Insert a new customer in a database. " +
            "User needs to be ANONYMOUS to perform this operation";

    // customer controller
    public static final String GET_AUTHENTICATED_CUSTOMER = "Retrieve an authenticated customer data.";
    public static final String UPDATE_AUTHENTICATED_CUSTOMER = "Update an authenticated customer data.";
    public static final String GET_ANY_CUSTOMER = "Retrieve a data of a customer found by id. " +
            "User needs to have ADMIN role to perform this operation";
    public static final String GET_ALL_CUSTOMERS = "Retrieve a data of all registered customers. " +
            "User needs to have ADMIN role to perform this operation";

    // product brand controller
    public static final String CREATE_PRODUCT_BRAND = "Create a new product brand. An user needs to have ADMIN role to perform this operation";
    public static final String GET_PRODUCT_BRAND = "Retrieve a product brand by id";
    public static final String GET_ALL_BRANDS = "Retrieve all product brands";
    public static final String UPDATE_PRODUCT_BRAND = "Update a product brand by id";
    public static final String UPLOAD_PRODUCT_BRAND_LOGO = "Upload a product brand logo. " +
            "If a product brand already has a logo, the old logo will be replaced with an uploaded logo. " +
            "User needs to have ADMIN role to perform this operation. Supported file extension is .jpeg";

    // product category controller
    public static final String CREATE_PRODUCT_CATEGORY = "Create a new product category. User needs to have ADMIN role to perform this operation";
    public static final String CREATE_PRODUCT_SUBCATEGORY = "Create a new product subcategory. User needs to have ADMIN role to perform this operation";
    public static final String UPDATE_PRODUCT_CATEGORY = "Update a product category by id. User needs to have ADMIN role to perform this operation";
    public static final String GET_CATEGORY_TREE = "Return all product categories organized in tree structure";
    public static final String UPLOAD_CATEGORY_IMAGE = "Upload a product category image. " +
            "If a product category already have an associated image, the old image will be replaced with a new image. " +
            "User needs to have ADMIN role to perform this operation. Supported file extension is .jpeg";

    // product controller
    public static final String CREATE_PRODUCT = "Create a new product. User needs to have ADMIN role to perform this operation.";
    public static final String UPDATE_PRODUCT = "Update an existing product found by id. User needs to have ADMIN role to perform this operation.";
    public static final String GET_PRODUCT_DETAILS = "Retrieve details of a product found by id.";
    public static final String GET_ALL_PRODUCTS = "Retrieve all products";
    public static final String UPLOAD_MAIN_IMAGE = "Upload a product main image. " +
            "If a product already have a main image, an old image will be replaced with a new image. " +
            "User needs to have ADMIN role to perform this operation.";
    public static final String UPLOAD_IMAGE = "Add a new image to collection of existing product images. User needs to have ADMIN role to perform this operation.";
    public static final String DELETE_IMAGE = "Delete a product image. User needs to have ADMIN role to perform this operation.";

    // order controller
    public static final String CREATE_NEW_ORDER = "Create a new order. User needs to have CUSTOMER role to perform this operation.";
    public static final String UPDATE_ORDER_STATUS = "Update a status of an order found by id. User needs to have ADMIN role to perform this operation.";
    public static final String GET_ALL_ORDERS = "Retrieve all created orders. User needs to have ADMIN role to perform this operation.";
    public static final String GET_ORDER = "Find an order by id. User needs to have ADMIN role to perform this operation.";
    public static final String GET_CUSTOMER_ORDERS = "Find all orders of specific customer. User needs to have ADMIN role to perform this operation.";
    public static final String GET_ORDER_ITEMS = "Find all order items of specific order. User needs to have ADMIN role to perform this operation.";

}
