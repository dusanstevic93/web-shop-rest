package com.dusan.webshop.api.controller;

import com.dusan.webshop.api.controller.exception.EmptyFileException;
import com.dusan.webshop.api.controller.exception.FileFormatNotSupportedException;
import com.dusan.webshop.api.docs.Descriptions;
import com.dusan.webshop.dto.request.CreateProductCategoryRequest;
import com.dusan.webshop.dto.request.UploadedImage;
import com.dusan.webshop.dto.response.ProductCategoryResponse;
import com.dusan.webshop.service.ProductCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Create new product category", description = Descriptions.CREATE_PRODUCT_CATEGORY,
                responses = {@ApiResponse(responseCode = "201", description = "successful operation"),
                             @ApiResponse(responseCode = "403", description = "unauthorized")})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createCategory(@Valid @RequestBody CreateProductCategoryRequest request) {
        categoryService.createProductCategory(request);
    }

    @Operation(summary = "Create new product subcategory", description = Descriptions.CREATE_PRODUCT_SUBCATEGORY,
            responses = {@ApiResponse(responseCode = "201", description = "successful operation"),
                         @ApiResponse(responseCode = "403", description = "unauthorized"),
                         @ApiResponse(responseCode = "404", description = "parent category is not found")})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{parentCategoryId}/subcategories", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createSubcategory(@PathVariable long parentCategoryId, @Valid @RequestBody CreateProductCategoryRequest request) {
        categoryService.createProductSubcategory(parentCategoryId, request);
    }

    @Operation(summary = "Update product category", description = Descriptions.UPDATE_PRODUCT_CATEGORY,
                responses = {@ApiResponse(responseCode = "200", description = "successful operation"),
                             @ApiResponse(responseCode = "403", description = "unauthorized"),
                             @ApiResponse(responseCode = "404", description = "category not found")})
    @PutMapping(value = "/{categoryId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateCategory(@PathVariable long categoryId, @Valid @RequestBody CreateProductCategoryRequest request) {
        categoryService.updateProductCategory(categoryId, request);
    }

    @Operation(summary = "Upload category image", description = Descriptions.UPLOAD_CATEGORY_IMAGE,
            responses = {@ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "400", description = "Empty file"),
                    @ApiResponse(responseCode = "404", description = "Product brand is not found"),
                    @ApiResponse(responseCode = "422", description = "File format is not valid")})
    @PutMapping(value = "/{categoryId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadCategoryImage(@PathVariable long categoryId, @RequestBody MultipartFile image) throws Exception {
        if (image == null)
            throw new EmptyFileException("File must not be empty");

        if (!image.getContentType().equals("image/jpeg"))
            throw new FileFormatNotSupportedException(image.getContentType() + " is not supported");

        UploadedImage uploadedImage = new UploadedImage();
        uploadedImage.setName(image.getOriginalFilename());
        uploadedImage.setBytes(image.getBytes());
        categoryService.addCategoryImage(categoryId, uploadedImage);
    }
}
