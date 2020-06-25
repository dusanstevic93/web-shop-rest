package com.dusan.webshop.service;

import com.dusan.webshop.dto.request.CreateProductRequest;
import com.dusan.webshop.dto.request.ProductPageParams;
import com.dusan.webshop.dto.request.UploadedImage;
import com.dusan.webshop.dto.response.ProductDetailsResponse;

public interface ProductService {

    void createProduct(CreateProductRequest request, UploadedImage mainImage);
    void updateProduct(long productId, CreateProductRequest request, UploadedImage newMainImage);
    void addImage(long productId, UploadedImage image);
    ProductDetailsResponse getProductDetails(long productId);
}