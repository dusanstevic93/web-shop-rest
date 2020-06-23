package com.dusan.webshop.service.impl;

import com.dusan.webshop.dao.ProductCategoryRepository;
import com.dusan.webshop.dto.request.CreateProductCategoryRequest;
import com.dusan.webshop.entity.ProductCategory;
import com.dusan.webshop.service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ProductCategoryServiceImplTest {

    @Mock
    private ProductCategoryRepository categoryRepository;

    @InjectMocks
    private ProductCategoryServiceImpl categoryService;

    @Test
    void testCreateProductCategoryShouldBeSuccessful() {
        // given
        CreateProductCategoryRequest request = new CreateProductCategoryRequest();
        request.setName("Test category");

        // when
        categoryService.createProductCategory(request);

        // then
        ArgumentCaptor<ProductCategory> argumentCaptor = ArgumentCaptor.forClass(ProductCategory.class);
        then(categoryRepository).should().save(argumentCaptor.capture());
        ProductCategory savedCategory = argumentCaptor.getValue();
        assertEquals(request.getName(), savedCategory.getName());
    }

    @Test
    void testCreateProductSubcategoryShouldBeSuccessful() {
        // given
        long parentId = 1L;
        ProductCategory parentCategory = new ProductCategory();
        ReflectionTestUtils.setField(parentCategory, "id", parentId);
        parentCategory.setName("Parent");
        given(categoryRepository.findById(parentId)).willReturn(Optional.of(parentCategory));

        CreateProductCategoryRequest request = new CreateProductCategoryRequest();
        request.setName("Subcategory");

        // when
        categoryService.createProductSubcategory(parentId, request);

        // then
        ArgumentCaptor<ProductCategory> argumentCaptor = ArgumentCaptor.forClass(ProductCategory.class);
        then(categoryRepository).should().save(argumentCaptor.capture());
        ProductCategory savedParent = argumentCaptor.getValue();
        assertAll(
                () -> assertEquals("Subcategory", savedParent.getSubCategories().get(0).getName())
        );
    }

    @Test
    void testCreateProductSubcategoryParentNotFound() {
        // given
        long parentId = 1L;
        given(categoryRepository.findById(parentId)).willReturn(Optional.empty());

        CreateProductCategoryRequest request = new CreateProductCategoryRequest();
        request.setName("Subcategory");

        // when
        Executable createSubcategory = () -> categoryService.createProductSubcategory(parentId, request);

        // then
        assertThrows(ResourceNotFoundException.class, createSubcategory);
    }

    @Test
    void testDeleteProductCategory() {
        // given
        long categoryId = 1L;
        ProductCategory category = new ProductCategory();
        category.setName("Test category");
        given(categoryRepository.findById(categoryId)).willReturn(Optional.of(category));

        // when
        categoryService.deleteProductCategory(categoryId);

        // then
        ArgumentCaptor<ProductCategory> argumentCaptor = ArgumentCaptor.forClass(ProductCategory.class);
        then(categoryRepository).should().delete(argumentCaptor.capture());
        ProductCategory deletedCategory = argumentCaptor.getValue();
        assertEquals("Test category", deletedCategory.getName());
    }

    @Test
    void testDeleteProductCategoryCategoryNotFound() {
        // given
        long categoryId = 1L;
        given(categoryRepository.findById(categoryId)).willReturn(Optional.empty());

        // when
        Executable deleteCategory = () -> categoryService.deleteProductCategory(categoryId);

        // then
        assertThrows(ResourceNotFoundException.class, deleteCategory);
    }
}