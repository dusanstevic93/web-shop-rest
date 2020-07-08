package com.dusan.webshop.api.controller;

import com.dusan.webshop.api.docs.Descriptions;
import com.dusan.webshop.dto.request.CreateProductBrandRequest;
import com.dusan.webshop.dto.request.params.ProductBrandPageParams;
import com.dusan.webshop.dto.response.PageResponseWrapper;
import com.dusan.webshop.dto.response.ProductBrandResponse;
import com.dusan.webshop.service.ProductBrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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
@Tag(name = "Brand")
@RestController
@RequestMapping("/brands")
public class ProductBrandController {

    private ProductBrandService brandService;

    @Operation(summary = "Create new product brand", description = Descriptions.CREATE_PRODUCT_BRAND,
                responses = {
                             @ApiResponse(responseCode = "201", description = "Successful operation"),
                             @ApiResponse(responseCode = "400", description = "Required field is invalid or missing"),
                             @ApiResponse(responseCode = "403", description = "Unauthorized access")
                             })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createProductBrand(@Valid @RequestBody CreateProductBrandRequest request) {
        brandService.createProductBrand(request);
    }

    @Operation(summary = "Get product brand", description = Descriptions.GET_PRODUCT_BRAND,
                responses = {
                             @ApiResponse(responseCode = "200", description = "Successful operation"),
                             @ApiResponse(responseCode = "404", description = "Product brand is not found", content = @Content)
                })
    @GetMapping(value = "/{brandId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductBrandResponse getProductBrandById(@PathVariable long brandId) {
        return brandService.findProductBrandById(brandId);
    }

    @Operation(summary = "Update product brand", description = Descriptions.UPDATE_PRODUCT_BRAND,
                responses = {
                             @ApiResponse(responseCode = "200", description = "successful operation"),
                             @ApiResponse(responseCode = "404", description = "Product brand is not found")
                })
    @PutMapping(value = "/{brandId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateProductBrand(@PathVariable long brandId, @Valid @RequestBody CreateProductBrandRequest request) {
        brandService.updateProductBrand(brandId, request);
    }

    @Operation(summary = "Get all brands", description = Descriptions.GET_ALL_BRANDS,
                responses = @ApiResponse(responseCode = "200", description = "successful operation"))
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponseWrapper<ProductBrandResponse> getAllProductBrands(@Valid @ParameterObject ProductBrandPageParams pageParams) {
        Page<ProductBrandResponse> page = brandService.findAllProductBrands(pageParams);
        return ControllerUtils.createPageResponseWrapper(page);
    }

    @Operation(summary = "Upload brand logo", description = Descriptions.UPLOAD_PRODUCT_BRAND_LOGO,
                responses = {@ApiResponse(responseCode = "201", description = "Successful operation"),
                             @ApiResponse(responseCode = "404", description = "Product brand is not found"),
                             @ApiResponse(responseCode = "422", description = "Image upload error")})
    @PostMapping(value = "/{brandId}/logo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadProductBrandLogo(@PathVariable long brandId, @RequestParam("image") MultipartFile image) throws Exception {
        ControllerUtils.validateUploadedImage(image);
        brandService.addBrandLogo(brandId, image.getBytes());
    }
}
