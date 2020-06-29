package com.dusan.webshop.api.docs;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Descriptions {

    // product brand controller
    public static final String CREATE_PRODUCT_BRAND = "Create new product brand. User needs to have ADMIN role to perform this operation";
    public static final String GET_PRODUCT_BRAND = "Find a single product brand by id";
    public static final String GET_ALL_BRANDS = "Get all available product brands";
    public static final String UPDATE_PRODUCT_BRAND = "Update product brand specified by id";
    public static final String UPLOAD_PRODUCT_BRAND_LOGO = "Set product brand logo. " +
            "If product brand already have a logo, an old logo will be replaced with an uploaded logo. " +
            "User needs to have ADMIN role to perform this operation. Supported file extension is .jpeg";

    // product category controller
    public static final String CREATE_PRODUCT_CATEGORY = "Create new product category. User needs to have ADMIN role to perform this operation";
    public static final String CREATE_PRODUCT_SUBCATEGORY = "Create subcategory of parent category specified by id. User needs to have ADMIN role to perform this operation";
    public static final String GET_CATEGORY_TREE = "Returns all product categories organized in tree structure";
    public static final String UPLOAD_CATEGORY_IMAGE = "Upload product category image. " +
            "If product category already have a associated image, an old image will be replaced with a new image. " +
            "User needs to have ADMIN role to perform this operation. Supported file extension is .jpeg";
}
