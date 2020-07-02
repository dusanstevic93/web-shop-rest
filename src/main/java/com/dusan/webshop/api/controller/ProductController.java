package com.dusan.webshop.api.controller;

import com.dusan.webshop.api.controller.exception.EmptyFileException;
import com.dusan.webshop.api.controller.exception.FileFormatNotSupportedException;
import com.dusan.webshop.dto.request.CreateProductRequest;
import com.dusan.webshop.dto.request.ProductFilterParams;
import com.dusan.webshop.dto.request.ProductPageParams;
import com.dusan.webshop.dto.request.UploadedImage;
import com.dusan.webshop.dto.response.PageResponseWrapper;
import com.dusan.webshop.dto.response.PageResponseWrapper.PageMetadata;
import com.dusan.webshop.dto.response.ProductResponse;
import com.dusan.webshop.service.ProductService;
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


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@Valid @RequestBody CreateProductRequest request) {
        productService.createProduct(request);
    }

    @PutMapping(value = "/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateProduct(@PathVariable long productId, @Valid @RequestBody CreateProductRequest request) {
        productService.updateProduct(productId, request);
    }

    @GetMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductResponse getProductDetails(@PathVariable long productId) {
        return productService.findProductDetailsById(productId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponseWrapper<ProductResponse> getAllProducts(
            @ParameterObject  ProductFilterParams filterParams,
            @Valid @ParameterObject ProductPageParams pageParams) {
        Page<ProductResponse> page = productService.findAllProducts(filterParams, pageParams);
        PageMetadata metadata = PageMetadata.of(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
        return new PageResponseWrapper<>(page.getContent(), metadata);
    }

    @PutMapping(value = "/{productId}/main-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadMainImage(@PathVariable long productId, @RequestBody MultipartFile image) throws Exception {
        if (image == null)
            throw new EmptyFileException("File must not be empty");

        if (!image.getContentType().equals("image/jpeg"))
            throw new FileFormatNotSupportedException(image.getContentType() + " is not supported");

        UploadedImage uploadedImage = new UploadedImage();
        uploadedImage.setName(image.getOriginalFilename());
        uploadedImage.setBytes(image.getBytes());
        productService.addMainImage(productId, uploadedImage);
    }

    @PostMapping(value = "/{productId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadImage(@PathVariable long productId, @RequestBody MultipartFile image) throws Exception {
        if (image == null)
            throw new EmptyFileException("File must not be empty");

        if (!image.getContentType().equals("image/jpeg"))
            throw new FileFormatNotSupportedException(image.getContentType() + " is not supported");

        UploadedImage uploadedImage = new UploadedImage();
        uploadedImage.setName(image.getOriginalFilename());
        uploadedImage.setBytes(image.getBytes());
        productService.addImage(productId, uploadedImage);
    }

    @DeleteMapping(value = "/{productId}/images/{imageId}")
    public void deleteImage(@PathVariable long productId, @PathVariable String imageId) {
        productService.deleteImage(productId, imageId);
    }
}
