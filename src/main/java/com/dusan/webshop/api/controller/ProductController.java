package com.dusan.webshop.api.controller;

import com.dusan.webshop.api.docs.Descriptions;
import com.dusan.webshop.dto.request.CreateProductRequest;
import com.dusan.webshop.dto.request.params.ProductFilterParams;
import com.dusan.webshop.dto.request.params.ProductPageParams;
import com.dusan.webshop.dto.response.PageResponseWrapper;
import com.dusan.webshop.dto.response.ProductResponse;
import com.dusan.webshop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@AllArgsConstructor
@Tag(name = "Product")
@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;


    @Operation(summary = "Create a new product", description = Descriptions.CREATE_PRODUCT,
                responses = {@ApiResponse(responseCode = "201", description = "successful operation"),
                             @ApiResponse(responseCode = "403", description = "unauthorized access"),
                             @ApiResponse(responseCode = "404", description = "product category or product brand is not found")})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@Valid @RequestBody CreateProductRequest request) {
        productService.createProduct(request);
    }

    @Operation(summary = "Update an existing product", description = Descriptions.UPDATE_PRODUCT,
                responses = {@ApiResponse(responseCode = "200", description = "successful operation"),
                             @ApiResponse(responseCode = "403", description = "unauthorized access"),
                             @ApiResponse(responseCode = "404", description = "product category or product brand is not found")})
    @PutMapping(value = "/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateProduct(@PathVariable long productId, @Valid @RequestBody CreateProductRequest request) {
        productService.updateProduct(productId, request);
    }

    @Operation(summary = "Get a product by id", description = Descriptions.GET_PRODUCT_DETAILS,
                responses = {@ApiResponse(responseCode = "200", description = "successful operation"),
                             @ApiResponse(responseCode = "404", description = "product is not found")})
    @GetMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductResponse getProductDetails(@PathVariable long productId) {
        return productService.findProductDetailsById(productId);
    }

    @Operation(summary = "Get all products", description = Descriptions.GET_ALL_PRODUCTS,
                responses = @ApiResponse(responseCode = "200", description = "successful operation"))
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponseWrapper<ProductResponse> getAllProducts(
            @ParameterObject  ProductFilterParams filterParams,
            @Valid @ParameterObject ProductPageParams pageParams) {
        Page<ProductResponse> page = productService.findAllProducts(filterParams, pageParams);
        return ControllerUtils.createPageResponseWrapper(page);
    }

    @Operation(summary = "Upload a product main image", description = Descriptions.UPLOAD_MAIN_IMAGE,
                responses = {@ApiResponse(responseCode = "201", description = "successful operation"),
                             @ApiResponse(responseCode = "403", description = "unauthorized access"),
                             @ApiResponse(responseCode = "404", description = "product is not found"),
                             @ApiResponse(responseCode = "422", description = "image upload error")})
    @PostMapping(value = "/{productId}/main-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadMainImage(@PathVariable long productId, @RequestParam("main-image") MultipartFile image) throws Exception {
        ControllerUtils.validateUploadedImage(image);
        productService.addMainImage(productId, image.getBytes());
    }

    @Operation(summary = "Upload a product image", description = Descriptions.UPLOAD_IMAGE,
            responses = {@ApiResponse(responseCode = "201", description = "successful operation"),
                         @ApiResponse(responseCode = "403", description = "unauthorized access"),
                         @ApiResponse(responseCode = "404", description = "product is not found"),
                         @ApiResponse(responseCode = "422", description = "image upload error")})
    @PostMapping(value = "/{productId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadImage(@PathVariable long productId, @RequestParam("image") MultipartFile image) throws Exception {
        ControllerUtils.validateUploadedImage(image);
        productService.addImage(productId, image.getBytes());
    }

    @Operation(summary = "Delete a product image", description = Descriptions.DELETE_IMAGE,
            responses = {@ApiResponse(responseCode = "200", description = "successful operation"),
                         @ApiResponse(responseCode = "403", description = "unauthorized access"),
                         @ApiResponse(responseCode = "404", description = "product is not found"),
                         @ApiResponse(responseCode = "422", description = "image upload error")})
    @DeleteMapping(value = "/{productId}/images/{imageId}")
    public void deleteImage(@PathVariable long productId, @PathVariable String imageId) {
        productService.deleteImage(productId, imageId);
    }
}
