package com.dusan.webshop.service.impl;

import com.dusan.webshop.dao.ProductBrandRepository;
import com.dusan.webshop.dto.request.CreateProductBrandRequest;
import com.dusan.webshop.dto.request.ProductBrandPageParams;
import com.dusan.webshop.dto.response.ProductBrandResponse;
import com.dusan.webshop.entity.ProductBrand;
import com.dusan.webshop.service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ProductBrandServiceImplTest {

    @Mock
    private ProductBrandRepository brandRepository;

    @InjectMocks
    private ProductBrandServiceImpl brandService;


    @Test
    void testCreateProductBrandShouldBeSuccessful() {
        // given
        CreateProductBrandRequest request = new CreateProductBrandRequest();
        request.setBrandName("Test brand");

        // when
        brandService.createProductBrand(request);

        // then
        ArgumentCaptor<ProductBrand> argumentCaptor = ArgumentCaptor.forClass(ProductBrand.class);
        then(brandRepository).should().save(argumentCaptor.capture());
        ProductBrand savedBrand = argumentCaptor.getValue();
        assertEquals(request.getBrandName(), savedBrand.getName());
    }

    @Test
    void testFindProductBrandByIdShouldBeSuccessful() {
        // given
        long brandId = 1L;
        ProductBrand foundBrand = new ProductBrand();
        ReflectionTestUtils.setField(foundBrand, "id", 1L);
        foundBrand.setName("Test brand");
        given(brandRepository.findById(brandId)).willReturn(Optional.of(foundBrand));

        // when
        ProductBrandResponse response = brandService.findProductBrandById(brandId);

        // then
        assertAll(
                () -> assertEquals(brandId, response.getId()),
                () -> assertEquals(foundBrand.getName(), response.getName())
        );
    }

    @Test
    void testFindProductBrandByIdBrandNotFound() {
        // given
        long brandId = 1L;
        given(brandRepository.findById(brandId)).willReturn(Optional.empty());

        // when
        Executable findBrand = () -> brandService.findProductBrandById(brandId);

        // then
        assertThrows(ResourceNotFoundException.class, findBrand);
    }

    @Test
    void testFindAllProductBrandsShouldBeSuccessful() {
        // given
        ProductBrand brand1 = new ProductBrand();
        brand1.setName("Brand 1");

        ProductBrand brand2 = new ProductBrand();
        brand2.setName("Brand 2");

        ProductBrand brand3 = new ProductBrand();
        brand3.setName("Brand 3");

        Page<ProductBrand> page = new PageImpl<>(Arrays.asList(brand1, brand2, brand3));
        given(brandRepository.findAll(any(Pageable.class))).willReturn(page);

        // when
        Page<ProductBrandResponse> responsePage = brandService.findAllProductBrands(new ProductBrandPageParams());

        // then
        assertEquals(page.getContent().size(), responsePage.getContent().size());
        for (int i = 0; i < page.getContent().size(); i++){
            assertEquals(page.getContent().get(i).getName(), responsePage.getContent().get(i).getName());
        }
    }
}