package com.dusan.webshop.service.impl;

import com.dusan.webshop.dao.ProductCategoryRepository;
import com.dusan.webshop.dto.request.CreateProductCategoryRequest;
import com.dusan.webshop.entity.ProductCategory;
import com.dusan.webshop.service.ProductCategoryService;
import com.dusan.webshop.service.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private ProductCategoryRepository categoryRepository;

    @Override
    public void createProductCategory(CreateProductCategoryRequest reqeust) {
        ProductCategory category = new ProductCategory();
        category.setName(reqeust.getName());
        categoryRepository.save(category);
    }

    @Override
    public void createProductSubcategory(long parentCategoryId, CreateProductCategoryRequest request) {
        ProductCategory parentCategory = findProductCategory(parentCategoryId);
        ProductCategory subcategory = new ProductCategory();
        subcategory.setName(request.getName());
        parentCategory.addSubCategory(subcategory);
        categoryRepository.save(parentCategory);
    }

    @Override
    public void deleteProductCategory(long categoryId) {
        ProductCategory category = findProductCategory(categoryId);
        categoryRepository.delete(category);
    }

    private ProductCategory findProductCategory(long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id = " + categoryId + " does not exits"));
    }
}
