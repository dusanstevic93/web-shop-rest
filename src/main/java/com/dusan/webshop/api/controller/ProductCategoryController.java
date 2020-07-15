package com.dusan.webshop.api.controller;

import com.dusan.webshop.api.docs.Descriptions;
import com.dusan.webshop.api.docs.OpenApiConfig;
import com.dusan.webshop.dto.request.CreateProductCategoryRequest;
import com.dusan.webshop.dto.response.ProductCategoryResponse;
import com.dusan.webshop.service.ProductCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@Tag(name = "Category")
@RestController
@RequestMapping("/categories")
public class ProductCategoryController {

    private ProductCategoryService categoryService;

    @Operation(summary = "Get all product categories", description = Descriptions.GET_CATEGORY_TREE,
                responses = @ApiResponse(responseCode = "200", description = "successful operation"))
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductCategoryResponse> getCategoryTree() {
        return categoryService.getCategoryTree();
    }

    @Operation(summary = "Create a new product category", description = Descriptions.CREATE_PRODUCT_CATEGORY,
                security = @SecurityRequirement(name = OpenApiConfig.BEARER_TOKEN_SCHEME),
                responses = {@ApiResponse(responseCode = "201", description = "successful operation"),
                             @ApiResponse(responseCode = "403", description = "unauthorized")})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createCategory(@Valid @RequestBody CreateProductCategoryRequest request) {
        categoryService.createProductCategory(request);
    }

    @Operation(summary = "Create a new product subcategory", description = Descriptions.CREATE_PRODUCT_SUBCATEGORY,
            security = @SecurityRequirement(name = OpenApiConfig.BEARER_TOKEN_SCHEME),
            responses = {@ApiResponse(responseCode = "201", description = "successful operation"),
                         @ApiResponse(responseCode = "403", description = "unauthorized"),
                         @ApiResponse(responseCode = "404", description = "parent category is not found")})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{parentCategoryId}/subcategories", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createSubcategory(@PathVariable long parentCategoryId, @Valid @RequestBody CreateProductCategoryRequest request) {
        categoryService.createProductSubcategory(parentCategoryId, request);
    }

    @Operation(summary = "Update a product category", description = Descriptions.UPDATE_PRODUCT_CATEGORY,
                security = @SecurityRequirement(name = OpenApiConfig.BEARER_TOKEN_SCHEME),
                responses = {@ApiResponse(responseCode = "200", description = "successful operation"),
                             @ApiResponse(responseCode = "403", description = "unauthorized"),
                             @ApiResponse(responseCode = "404", description = "category is not found")})
    @PutMapping(value = "/{categoryId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateCategory(@PathVariable long categoryId, @Valid @RequestBody CreateProductCategoryRequest request) {
        categoryService.updateProductCategory(categoryId, request);
    }

    @Operation(summary = "Upload a category image", description = Descriptions.UPLOAD_CATEGORY_IMAGE,
            security = @SecurityRequirement(name = OpenApiConfig.BEARER_TOKEN_SCHEME),
            responses = {@ApiResponse(responseCode = "200", description = "successful operation"),
                         @ApiResponse(responseCode = "404", description = "product brand is not found"),
                         @ApiResponse(responseCode = "422", description = "image upload error")})
    @PostMapping(value = "/{categoryId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadCategoryImage(@PathVariable long categoryId, @RequestParam("image") MultipartFile image) throws Exception {
        ControllerUtils.validateUploadedImage(image);
        categoryService.addCategoryImage(categoryId, image.getBytes());
    }
}
