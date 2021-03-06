package com.dusan.webshop.api.controller;

import com.dusan.webshop.api.docs.Descriptions;
import com.dusan.webshop.api.docs.OpenApiConfig;
import com.dusan.webshop.dto.request.CreateProductBrandRequest;
import com.dusan.webshop.dto.request.params.ProductBrandPageParams;
import com.dusan.webshop.dto.response.PageResponseWrapper;
import com.dusan.webshop.dto.response.ProductBrandResponse;
import com.dusan.webshop.service.ProductBrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@Tag(name = "Brand")
@RestController
@RequestMapping("/brands")
public class ProductBrandController {

    private ProductBrandService brandService;

    @Operation(summary = "Create a new product brand", description = Descriptions.CREATE_PRODUCT_BRAND,
                security = @SecurityRequirement(name = OpenApiConfig.BEARER_TOKEN_SCHEME),
                responses = {@ApiResponse(responseCode = "201", description = "successful operation"),
                             @ApiResponse(responseCode = "403", description = "unauthorized access")})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createProductBrand(@Valid @RequestBody CreateProductBrandRequest request) {
        brandService.createProductBrand(request);
    }

    @Operation(summary = "Get a product brand", description = Descriptions.GET_PRODUCT_BRAND,
                responses = {@ApiResponse(responseCode = "200", description = "Successful operation"),
                             @ApiResponse(responseCode = "404", description = "Product brand is not found", content = @Content)})
    @GetMapping(value = "/{brandId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductBrandResponse getProductBrandById(@PathVariable long brandId) {
        return brandService.findProductBrandById(brandId);
    }

    @Operation(summary = "Update a product brand", description = Descriptions.UPDATE_PRODUCT_BRAND,
                security = @SecurityRequirement(name = OpenApiConfig.BEARER_TOKEN_SCHEME),
                responses = {@ApiResponse(responseCode = "200", description = "successful operation"),
                             @ApiResponse(responseCode = "404", description = "product brand is not found")})
    @PutMapping(value = "/{brandId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateProductBrand(@PathVariable long brandId, @Valid @RequestBody CreateProductBrandRequest request) {
        brandService.updateProductBrand(brandId, request);
    }

    @Operation(summary = "Get all product brands", description = Descriptions.GET_ALL_BRANDS,
                responses = @ApiResponse(responseCode = "200", description = "successful operation"))
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponseWrapper<ProductBrandResponse> getAllProductBrands(@Valid @ParameterObject ProductBrandPageParams pageParams) {
        Page<ProductBrandResponse> page = brandService.findAllProductBrands(pageParams);
        return ControllerUtils.createPageResponseWrapper(page);
    }

    @Operation(summary = "Upload a product brand logo", description = Descriptions.UPLOAD_PRODUCT_BRAND_LOGO,
                security = @SecurityRequirement(name = OpenApiConfig.BEARER_TOKEN_SCHEME),
                responses = {@ApiResponse(responseCode = "201", description = "successful operation"),
                             @ApiResponse(responseCode = "404", description = "product brand is not found"),
                             @ApiResponse(responseCode = "422", description = "image upload error")})
    @PostMapping(value = "/{brandId}/logo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadProductBrandLogo(@PathVariable long brandId, @RequestParam("image") MultipartFile image) throws Exception {
        ControllerUtils.validateUploadedImage(image);
        brandService.addBrandLogo(brandId, image.getBytes());
    }
}
