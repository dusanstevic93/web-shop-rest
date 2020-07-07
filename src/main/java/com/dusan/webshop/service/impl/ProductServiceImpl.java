package com.dusan.webshop.service.impl;

import com.dusan.webshop.dao.ProductBrandRepository;
import com.dusan.webshop.dao.ProductCategoryRepository;
import com.dusan.webshop.dao.ProductDetailsRepository;
import com.dusan.webshop.dao.ProductRepository;
import com.dusan.webshop.dto.request.CreateProductRequest;
import com.dusan.webshop.dto.request.params.ProductFilterParams;
import com.dusan.webshop.dto.request.params.ProductPageParams;
import com.dusan.webshop.dto.response.ImageResponse;
import com.dusan.webshop.dto.response.ProductDetailsResponse;
import com.dusan.webshop.dto.response.ProductResponse;
import com.dusan.webshop.entity.*;
import com.dusan.webshop.entity.specification.ProductSpecification;
import com.dusan.webshop.service.ProductService;
import com.dusan.webshop.service.exception.ResourceNotFoundException;
import com.dusan.webshop.storage.ImageStorage;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ProductDetailsRepository detailsRepository;
    private ProductCategoryRepository categoryRepository;
    private ProductBrandRepository brandRepository;
    private ImageStorage imageStorage;

    @Override
    @Transactional
    public void createProduct(CreateProductRequest request) {
        checkIfProductBrandExists(request.getBrandId());
        checkIfProductCategoryExists(request.getCategoryId());

        ProductBrand brand = brandRepository.getOne(request.getBrandId());
        ProductCategory category = categoryRepository.getOne(request.getCategoryId());

        Product product = new Product();
        product.setProductBrand(brand);
        product.setProductCategory(category);
        BeanUtils.copyProperties(request, product);

        ProductDetails details = new ProductDetails();
        details.setProduct(product);
        details.setDescription(request.getLongDescription());

        detailsRepository.save(details);
    }

    @Override
    @Transactional
    public void updateProduct(long productId, CreateProductRequest request) {
        ProductDetails details = getProductDetailsFromDatabase(productId);
        Product product = details.getProduct();

        // when user is updating product category
        long newCategoryId = request.getCategoryId();
        long oldCategoryId = product.getProductCategory().getId();
        if (newCategoryId != oldCategoryId) {
            checkIfProductCategoryExists(request.getCategoryId());
            product.setProductCategory(categoryRepository.getOne(request.getCategoryId()));
        }

        // when user is updating product brand
        long newBrandId = request.getBrandId();
        long oldBrandId = product.getProductBrand().getId();
        if (newBrandId != oldBrandId) {
            checkIfProductBrandExists(request.getBrandId());
            product.setProductBrand(brandRepository.getOne(request.getBrandId()));
        }

        BeanUtils.copyProperties(request, product);

        details.setDescription(request.getLongDescription());
    }

    private void checkIfProductBrandExists(long brandId) {
        boolean brandExists = brandRepository.existsById(brandId);
        if (!brandExists)
            throw new ResourceNotFoundException("Brand with id = " + brandId + " does not exist");
    }

    private void checkIfProductCategoryExists(long categoryId) {
        boolean categoryExists = categoryRepository.existsById(categoryId);
        if (!categoryExists)
            throw new ResourceNotFoundException("Category with id = " + categoryId + " does not exists");
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDetailsResponse findProductDetailsById(long productId) {
        ProductDetails details = getProductDetailsFromDatabase(productId);
        Product product = details.getProduct();
        ProductResponse response = convertEntityToResponse(product);
        ProductDetailsResponse detailsResponse = new ProductDetailsResponse();
        BeanUtils.copyProperties(response, detailsResponse);
        detailsResponse.setLongDescription(details.getDescription());
        detailsResponse.setImages(
                details.getImages()
                        .stream()
                        .map(image -> new ImageResponse(image.getImageId(), image.getImageUrl()))
                        .collect(Collectors.toList())
        );
        return detailsResponse;
    }

    private ProductDetails getProductDetailsFromDatabase(long productId) {
        return detailsRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id = " + productId + " does not exist"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> findAllProducts(ProductFilterParams filterParams, ProductPageParams pageParams) {
        Specification<Product> productSpecification = new ProductSpecification(filterParams);
        Pageable pageable = getPageable(pageParams);
        Page<Product> products = productRepository.findAll(productSpecification, pageable);
        return products.map(this::convertEntityToResponse);
    }

    private Pageable getPageable(ProductPageParams pageParams) {
        Sort sort = null;
        switch (pageParams.getSort()) {
            case NAME: sort = Sort.by(pageParams.getDirection(), "name");
            break;
            case PRICE: sort = Sort.by(pageParams.getDirection(), "price");
            break;
            case RATING: sort = Sort.by(pageParams.getDirection(), "averageRating");
            break;
            case UNSORTED: sort = Sort.unsorted();
            break;
        }
        return PageRequest.of(pageParams.getPage(), pageParams.getSize(), sort);
    }

    private ProductResponse convertEntityToResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setCategoryId(product.getProductCategory().getId());
        response.setBrandId(product.getProductBrand().getId());
        BeanUtils.copyProperties(product, response);
        if (product.getMainImage() != null) {
            ImageResponse imageResponse = new ImageResponse(
                    product.getMainImage().getImageId(),
                    product.getMainImage().getImageUrl()
            );
            response.setMainImage(imageResponse);
        }
        return response;
    }

    @Override
    @Transactional
    public void addMainImage(long productId, byte[] image) {
        Product product = getProductFromDatabase(productId);
        if (product.getMainImage() != null)
            imageStorage.deleteImage(product.getMainImage().getImageId());

        Map<String, String> response = imageStorage.saveImage(image);
        Image productImage = new Image(response.get("public_id"), response.get("url"));
        product.setMainImage(productImage);
    }

    private Product getProductFromDatabase(long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id = " + productId + " does not exit"));
    }

    @Override
    @Transactional
    public void addImage(long productId, byte[] image) {
        checkIfProductExists(productId);
        Map<String, String> response = imageStorage.saveImage(image);
        detailsRepository.insertImage(productId, response.get("public_id"), response.get("url"));
    }

    @Override
    @Transactional
    public void deleteImage(long productId, String imageId) {
        checkIfProductExists(productId);
        detailsRepository.deleteImage(imageId);
        imageStorage.deleteImage(imageId);
    }

    private void checkIfProductExists(long productId) {
        boolean exits = productRepository.existsById(productId);
        if (!exits)
            throw new ResourceNotFoundException("Product with id = " + productId + " does not exist");
    }
}
