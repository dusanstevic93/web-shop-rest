package com.dusan.webshop.service.impl;

import com.dusan.webshop.dao.ProductCategoryRepository;
import com.dusan.webshop.dto.request.CreateProductCategoryRequest;
import com.dusan.webshop.dto.request.UploadedImage;
import com.dusan.webshop.dto.response.ProductCategoryResponse;
import com.dusan.webshop.entity.Image;
import com.dusan.webshop.entity.ProductCategory;
import com.dusan.webshop.service.ProductCategoryService;
import com.dusan.webshop.service.exception.ResourceNotFoundException;
import com.dusan.webshop.storage.ImageStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private ProductCategoryRepository categoryRepository;
    private ImageStorage imageStorage;


    @Override
    public List<ProductCategoryResponse> getCategoryTree() {
        List<ProductCategory> categories = categoryRepository.findAllCategoriesFetchSubcategories();
        return categories.stream()
                .filter(category -> category.getParent() == null)
                .map(this::convertEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void createProductCategory(CreateProductCategoryRequest request) {
        ProductCategory category = new ProductCategory();
        category.setName(request.getName());
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

    @Override
    public void addCategoryImage(long categoryId, UploadedImage uploadedImage) {
        ProductCategory category = findProductCategory(categoryId);
        if (category.getImage() != null)
            imageStorage.deleteImage(category.getImage().getImageId());
        final String folder = "categories/" + categoryId;
        Map<String, String> response = imageStorage.saveImage(folder, uploadedImage);
        Image image = new Image(response.get("public_id"), response.get("url"));
        category.setImage(image);
        categoryRepository.save(category);
    }

    private ProductCategory findProductCategory(long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id = " + categoryId + " does not exits"));
    }

    public ProductCategoryResponse convertEntityToResponse(ProductCategory entity) {
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
