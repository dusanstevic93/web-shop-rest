package com.dusan.webshop.service.impl;

import com.dusan.webshop.dao.repository.ProductCategoryRepository;
import com.dusan.webshop.dto.request.CreateProductCategoryRequest;
import com.dusan.webshop.dto.response.ProductCategoryResponse;
import com.dusan.webshop.entity.Image;
import com.dusan.webshop.entity.ProductCategory;
import com.dusan.webshop.service.ProductCategoryService;
import com.dusan.webshop.service.exception.ResourceNotFoundException;
import com.dusan.webshop.storage.ImageStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
class ProductCategoryServiceImpl implements ProductCategoryService {

    private ProductCategoryRepository categoryRepository;
    private ImageStorage imageStorage;


    @Override
    @Transactional(readOnly = true)
    public List<ProductCategoryResponse> getCategoryTree() {
        List<ProductCategory> categories = categoryRepository.findAllCategoriesFetchSubcategories();
        return categories.stream()
                .filter(category -> category.getParent() == null)
                .map(this::convertEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createProductCategory(CreateProductCategoryRequest request) {
        ProductCategory category = new ProductCategory();
        category.setName(request.getName());
        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void createProductSubcategory(long parentCategoryId, CreateProductCategoryRequest request) {
        ProductCategory parentCategory = findProductCategory(parentCategoryId);
        ProductCategory subcategory = new ProductCategory();
        subcategory.setName(request.getName());
        parentCategory.addSubCategory(subcategory);
    }

    @Override
    @Transactional
    public void updateProductCategory(long categoryId, CreateProductCategoryRequest request) {
        ProductCategory category = findProductCategory(categoryId);
        category.setName(request.getName());
    }

    @Override
    @Transactional
    public void deleteProductCategory(long categoryId) {
        ProductCategory category = findProductCategory(categoryId);
        categoryRepository.delete(category);
    }

    @Override
    @Transactional
    public void addCategoryImage(long categoryId, byte[] image) {
        ProductCategory category = findProductCategory(categoryId);
        if (category.getImage() != null)
            imageStorage.deleteImage(category.getImage().getImageId());
        Map<String, String> response = imageStorage.saveImage(image);
        Image categoryImage = new Image(response.get("public_id"), response.get("url"));
        category.setImage(categoryImage);
    }

    private ProductCategory findProductCategory(long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id = " + categoryId + " does not exits"));
    }

    private ProductCategoryResponse convertEntityToResponse(ProductCategory entity) {
        ProductCategoryResponse response = new ProductCategoryResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        List<ProductCategoryResponse> subcategories = entity.getSubCategories()
                .stream()
                .map(this::convertEntityToResponse)
                .collect(Collectors.toList());
        response.setSubcategories(subcategories);
        if (entity.getImage() != null)
            response.setImageUrl(entity.getImage().getImageUrl());
        return response;
    }
}
