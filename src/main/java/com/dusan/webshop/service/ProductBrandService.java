package com.dusan.webshop.service;

import com.dusan.webshop.dto.request.CreateProductBrandRequest;
import com.dusan.webshop.dto.request.ProductBrandPageParams;
import com.dusan.webshop.dto.request.UploadedImage;
import com.dusan.webshop.dto.response.ProductBrandResponse;
import org.springframework.data.domain.Page;

public interface ProductBrandService {

    void createProductBrand(CreateProductBrandRequest request);
    void updateProductBrand(long brandId, CreateProductBrandRequest request);
    ProductBrandResponse findProductBrandById(long brandId);
    Page<ProductBrandResponse> findAllProductBrands(ProductBrandPageParams pageParams);
    void addBrandLogo(long brandId, UploadedImage logo);
}
