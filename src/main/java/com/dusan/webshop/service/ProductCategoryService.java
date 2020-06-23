package com.dusan.webshop.service;

import com.dusan.webshop.dto.request.CreateProductCategoryRequest;
import com.dusan.webshop.dto.response.ProductCategoryResponse;

import java.util.List;

public interface ProductCategoryService {

    void createProductCategory(CreateProductCategoryRequest reqeust);
    void createProductSubcategory(long parentCategoryId, CreateProductCategoryRequest request);
    void deleteProductCategory(long categoryId);
    List<ProductCategoryResponse> findProductCategoryByIdFetchSubcategories(long categoryId);
}
