package com.dusan.webshop.service;

import com.dusan.webshop.dto.request.CreateProductCategoryRequest;

public interface ProductCategoryService {

    void createProductCategory(CreateProductCategoryRequest reqeust);
    void createProductSubcategory(long parentCategoryId, CreateProductCategoryRequest request);
    void deleteProductCategory(long categoryId);
}
