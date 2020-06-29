package com.dusan.webshop.service.impl;

import com.dusan.webshop.dao.ProductBrandRepository;
import com.dusan.webshop.dto.request.CreateProductBrandRequest;
import com.dusan.webshop.dto.request.ProductBrandPageParams;
import com.dusan.webshop.dto.request.UploadedImage;
import com.dusan.webshop.dto.response.ProductBrandResponse;
import com.dusan.webshop.entity.Image;
import com.dusan.webshop.entity.ProductBrand;
import com.dusan.webshop.service.ProductBrandService;
import com.dusan.webshop.service.exception.ResourceNotFoundException;
import com.dusan.webshop.storage.ImageStorage;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@AllArgsConstructor
@Service
public class ProductBrandServiceImpl implements ProductBrandService {

    private ProductBrandRepository productBrandRepository;
    private ImageStorage imageStorage;

    @Override
    public void createProductBrand(CreateProductBrandRequest request) {
        ProductBrand brand = new ProductBrand();
        brand.setName(request.getBrandName());
        productBrandRepository.save(brand);
    }

    @Override
    public void updateProductBrand(long brandId, CreateProductBrandRequest request) {
        ProductBrand brand = getProductBrand(brandId);
        brand.setName(request.getBrandName());
        productBrandRepository.save(brand);
    }

    @Override
    public ProductBrandResponse findProductBrandById(long brandId) {
        ProductBrand brand = getProductBrand(brandId);
        return convertEntityToResponse(brand);
    }

    private ProductBrand getProductBrand(long brandId) {
        return productBrandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Product brand with id = " + brandId + " is not found"));
    }

    @Override
    public Page<ProductBrandResponse> findAllProductBrands(ProductBrandPageParams pageParams) {
        Pageable pageable = getPageable(pageParams);
        Page<ProductBrand> page = productBrandRepository.findAll(pageable);
        return page.map(this::convertEntityToResponse);
    }

    private Pageable getPageable(ProductBrandPageParams pageParams) {
        Sort sort;
        if (pageParams.getSort() != null){
            sort = Sort.by(pageParams.getDirection(), "name");
        } else {
            sort = Sort.unsorted();
        }
        return PageRequest.of(pageParams.getPage(), pageParams.getSize(), sort);
    }

    private ProductBrandResponse convertEntityToResponse(ProductBrand entity) {
        ProductBrandResponse response = new ProductBrandResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        if (entity.getImage() != null)
            response.setLogoUrl(entity.getImage().getImageUrl());
        return  response;
    }

    @Override
    @Transactional
    public void addBrandLogo(long brandId, UploadedImage logo) {
        ProductBrand brand = getProductBrand(brandId);
        // delete old logo if exists
        if (brand.getImage() != null)
            imageStorage.deleteImage(brand.getImage().getImageId());
        String folder = "brands/" + brandId;
        Map<String, String> response = imageStorage.saveImage(folder, logo);
        Image image = new Image(response.get("public_id"), response.get("url"));
        brand.setImage(image);
        productBrandRepository.save(brand);
    }
}
