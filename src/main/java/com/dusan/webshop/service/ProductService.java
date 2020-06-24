package com.dusan.webshop.service;

import com.dusan.webshop.dto.request.CreateProductRequest;
import com.dusan.webshop.dto.request.UploadedImage;

public interface ProductService {

    void createProduct(CreateProductRequest request, UploadedImage image);
    void addImage(long productId, UploadedImage image);
}
