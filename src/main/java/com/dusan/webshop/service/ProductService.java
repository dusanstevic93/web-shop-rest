package com.dusan.webshop.service;

import com.dusan.webshop.dto.request.CreateProductRequest;
import com.dusan.webshop.dto.request.ProductFilterParams;
import com.dusan.webshop.dto.request.ProductPageParams;
import com.dusan.webshop.dto.request.UploadedImage;
import com.dusan.webshop.dto.response.ProductDetailsResponse;
import com.dusan.webshop.dto.response.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    void createProduct(CreateProductRequest request, UploadedImage mainImage);
    void updateProduct(long productId, CreateProductRequest request, UploadedImage newMainImage);
    void addImage(long productId, UploadedImage image);
    ProductDetailsResponse getProductDetails(long productId);
    Page<ProductResponse> getProducts(ProductFilterParams filterParams, ProductPageParams pageParams);
}