package com.dusan.webshop.service.impl;

import com.dusan.webshop.dao.ProductBrandRepository;
import com.dusan.webshop.dao.ProductCategoryRepository;
import com.dusan.webshop.dao.ProductDetailsRepository;
import com.dusan.webshop.dao.ProductRepository;
import com.dusan.webshop.dto.request.CreateProductRequest;
import com.dusan.webshop.dto.request.UploadedImage;
import com.dusan.webshop.entity.Product;
import com.dusan.webshop.entity.ProductBrand;
import com.dusan.webshop.entity.ProductCategory;
import com.dusan.webshop.entity.ProductDetails;
import com.dusan.webshop.service.ProductImageService;
import com.dusan.webshop.service.ProductService;
import com.dusan.webshop.service.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ProductDetailsRepository detailsRepository;
    private ProductCategoryRepository categoryRepository;
    private ProductBrandRepository brandRepository;
    private ProductImageService imageService;

    @Override
    public void createProduct(CreateProductRequest request, UploadedImage image) {
        ProductCategory category = findCategoryById(request.getCategoryId());
        ProductBrand brand = findBrandById(request.getBrandId());
        Product product = new Product();
        product.setProductCategory(category);
        product.setProductBrand(brand);
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setShortDescription(request.getShortDescription());
        product.setWeight(request.getWeight());
        product.setMainImage(image.getName());

        ProductDetails productDetails = new ProductDetails();
        productDetails.setDescription(request.getLongDescription());
        productDetails.setProduct(product);

        ProductDetails savedProductDetails = detailsRepository.save(productDetails);

        imageService.saveImage(savedProductDetails.getId(), image);
    }

    @Override
    public void addImage(long productId, UploadedImage image) {
        ProductDetails productDetails = detailsRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id = " + productId + " does not exist."));
        productDetails.getImages().add(image.getName());
        imageService.saveImage(productId, image);
    }

    private ProductCategory findCategoryById(long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Product category with id = " + categoryId + " does not exist."));
    }

    private ProductBrand findBrandById(long brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Product brand with id = " + brandId + " does not exist"));
    }
}
