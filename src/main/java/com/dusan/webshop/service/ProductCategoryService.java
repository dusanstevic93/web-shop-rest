package com.dusan.webshop.service;

import com.dusan.webshop.dto.request.CreateProductCategoryRequest;
import com.dusan.webshop.dto.request.UploadedImage;
import com.dusan.webshop.dto.response.ProductCategoryResponse;

import java.util.List;

public interface ProductCategoryService {

    void createProductCategory(CreateProductCategoryRequest reqeust);
    void createProductSubcategory(long parentCategoryId, CreateProductCategoryRequest request);
    void updateProductCategory(long categoryId, CreateProductCategoryRequest request);
    void deleteProductCategory(long categoryId);
    void addCategoryImage(long categoryId, UploadedImage image);
    List<ProductCategoryResponse> getCategoryTree();
}
