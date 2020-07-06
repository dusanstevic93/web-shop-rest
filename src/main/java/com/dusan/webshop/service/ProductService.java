package com.dusan.webshop.service;

import com.dusan.webshop.dto.request.CreateProductRequest;
import com.dusan.webshop.dto.request.params.ProductFilterParams;
import com.dusan.webshop.dto.request.params.ProductPageParams;
import com.dusan.webshop.dto.request.UploadedImage;
import com.dusan.webshop.dto.response.ProductDetailsResponse;
import com.dusan.webshop.dto.response.ProductResponse;
import org.springframework.data.domain.Page;

public interface ProductService {

    void createProduct(CreateProductRequest request);
    void updateProduct(long productId, CreateProductRequest request);
    ProductDetailsResponse findProductDetailsById(long productId);
    Page<ProductResponse> findAllProducts(ProductFilterParams filterParams, ProductPageParams pageParams);
    void addMainImage(long productId, UploadedImage image);
    void addImage(long productId, UploadedImage image);
    void deleteImage(long productId, String imageId);
}