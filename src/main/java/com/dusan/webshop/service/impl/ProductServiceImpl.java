package com.dusan.webshop.service.impl;

import com.dusan.webshop.dao.ProductBrandRepository;
import com.dusan.webshop.dao.ProductCategoryRepository;
import com.dusan.webshop.dao.ProductDetailsRepository;
import com.dusan.webshop.dao.ProductRepository;
import com.dusan.webshop.dto.request.CreateProductRequest;
import com.dusan.webshop.dto.request.ProductFilterParams;
import com.dusan.webshop.dto.request.ProductPageParams;
import com.dusan.webshop.dto.request.ProductPageParams.ProductSort;
import com.dusan.webshop.dto.request.UploadedImage;
import com.dusan.webshop.dto.response.ProductDetailsResponse;
import com.dusan.webshop.dto.response.ProductResponse;
import com.dusan.webshop.entity.Product;
import com.dusan.webshop.entity.ProductBrand;
import com.dusan.webshop.entity.ProductCategory;
import com.dusan.webshop.entity.ProductDetails;
import com.dusan.webshop.entity.specification.ProductSpecification;
import com.dusan.webshop.service.ProductImageService;
import com.dusan.webshop.service.ProductService;
import com.dusan.webshop.service.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ProductDetailsRepository detailsRepository;
    private ProductCategoryRepository categoryRepository;
    private ProductBrandRepository brandRepository;
    private ProductImageService imageService;

    @Override
    @Transactional
    public void createProduct(CreateProductRequest request, UploadedImage mainImage) {
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
        product.setMainImage(mainImage.getName());

        ProductDetails productDetails = new ProductDetails();
        productDetails.setDescription(request.getLongDescription());
        productDetails.setProduct(product);

        ProductDetails savedProductDetails = detailsRepository.save(productDetails);

        imageService.saveImage(savedProductDetails.getId(), mainImage);
    }

    @Override
    @Transactional
    public void updateProduct(long productId, CreateProductRequest request, UploadedImage newMainImage) {
        ProductDetails productDetails = findProductDetailsById(productId);
        Product product = productDetails.getProduct();
        product.setProductCategory(categoryRepository.getOne(request.getCategoryId()));
        product.setProductBrand(brandRepository.getOne(request.getBrandId()));
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setShortDescription(request.getShortDescription());
        product.setWeight(request.getWeight());

        productDetails.setDescription(request.getLongDescription());

        // when user is changing current main image replace old image with new
        if (newMainImage != null){
            product.setMainImage(newMainImage.getName());
            imageService.deleteImage(productId, newMainImage.getName());
            imageService.saveImage(productId, newMainImage);
        }

        detailsRepository.save(productDetails);
    }

    @Override
    public ProductDetailsResponse getProductDetails(long productId) {
        ProductDetails details = findProductDetailsById(productId);
        Product product = details.getProduct();
        ProductDetailsResponse response = new ProductDetailsResponse();
        response.setId(product.getId());
        response.setBrandName(product.getProductBrand().getName());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setWeight(product.getWeight());
        response.setShortDescription(product.getShortDescription());
        response.setQuantity(product.getQuantity());
        response.setRating(product.getAverageRating());
        response.setDescription(details.getDescription());
        String mainImageLink = imageService.getImageLink(productId, product.getMainImage());
        List<String> links = imageService.getLinksToAllProductImages(productId);
        response.setMainImageLink(mainImageLink);
        response.setLinksToAllImages(links);
        return response;
    }

    @Override
    public Page<ProductResponse> getProducts(ProductFilterParams filterParams, ProductPageParams pageParams) {
        Specification<Product> specification = new ProductSpecification(filterParams);
        Pageable pageable = getPageable(pageParams);
        Page<Product> page = productRepository.findAll(specification, pageable);


        return null;
    }

    private Pageable getPageable(ProductPageParams pageParams) {
        Sort sort = getSort(pageParams.getSort(), pageParams.getDirection());
        return PageRequest.of(pageParams.getPage(), pageParams.getSize(), sort);
    }

    private Sort getSort(ProductSort productSort, Direction direction) {
        switch (productSort) {
            case NAME: return Sort.by(direction, "name");
            case PRICE: return Sort.by(direction, "price");
            case RATING: return Sort.by(direction, "averageRating");
            default: return Sort.unsorted();
        }
    }

    private ProductResponse convertProductToProductResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setBrandName(product.getProductBrand().getName());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setQuantity(product.getQuantity());
        response.setRating(product.getAverageRating());
        response.setShortDescription(product.getShortDescription());
        response.setWeight(product.getWeight());
        return response;
    }

    @Override
    public void addImage(long productId, UploadedImage image) {
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

    private ProductDetails findProductDetailsById(long productId) {
        return detailsRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id = " + productId + " does not exist"));
    }
}
