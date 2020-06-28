package com.dusan.webshop.service.impl;

import com.dusan.webshop.dao.ProductBrandRepository;
import com.dusan.webshop.dto.request.CreateProductBrandRequest;
import com.dusan.webshop.dto.request.ProductBrandPageParams;
import com.dusan.webshop.dto.response.ProductBrandResponse;
import com.dusan.webshop.entity.ProductBrand;
import com.dusan.webshop.service.ProductBrandService;
import com.dusan.webshop.service.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ProductBrandServiceImpl implements ProductBrandService {

    private ProductBrandRepository productBrandRepository;

    @Override
    public void createProductBrand(CreateProductBrandRequest request) {
        ProductBrand brand = new ProductBrand();
        brand.setName(request.getBrandName());
        productBrandRepository.save(brand);
    }

    @Override
    public ProductBrandResponse findProductBrandById(long brandId) {
        ProductBrand brand = productBrandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Product brand with id = " + brandId + " is not found"));
        return convertEntityToResponse(brand);
    }

    @Override
    public Page<ProductBrandResponse> findAllProductBrands(ProductBrandPageParams pageParams) {
        Pageable pageable = getPageable(pageParams);
        Page<ProductBrand> page = productBrandRepository.findAll(pageable);
        return page.map(this::convertEntityToResponse);
    }

    private Pageable getPageable(ProductBrandPageParams pageParams) {
        Sort sort = null;
        if (pageParams.getSort() != null){
            sort = Sort.by(pageParams.getDirection(), "name");
        } else {
            sort = Sort.unsorted();
        }
        return PageRequest.of(pageParams.getPage(), pageParams.getSize(), sort);
    }

    private ProductBrandResponse convertEntityToResponse(ProductBrand entity) {
        ProductBrandResponse response = new ProductBrandResponse();
        BeanUtils.copyProperties(entity, response);
        return  response;
    }
}
