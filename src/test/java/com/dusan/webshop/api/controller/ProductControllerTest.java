package com.dusan.webshop.api.controller;

import com.dusan.webshop.dto.request.CreateProductRequest;
import com.dusan.webshop.dto.response.ProductDetailsResponse;
import com.dusan.webshop.dto.response.ProductResponse;
import com.dusan.webshop.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest extends ControllerTestSetup {

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(roles = "ADMIN")
    void createProductAdmin() throws Exception {
        String json = getCreateProductRequestModelJson();
        mvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void createProductCustomer() throws Exception {
        mvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void createProductAnonymousUser() throws Exception {
        mvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateProductAdmin() throws Exception {
        String json = getCreateProductRequestModelJson();
        mvc.perform(put("/products/1").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void updateProductCustomer() throws Exception {
        mvc.perform(put("/products/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void updateProductAnonymousUser() throws Exception {
        mvc.perform(put("/products/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void getProductDetails() throws Exception {
        given(productService.findProductDetailsById(1L)).willReturn(new ProductDetailsResponse());

        mvc.perform(get("/products/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAllProducts() throws Exception {
        Page<ProductResponse> products = new PageImpl<>(Collections.emptyList());
        given((productService.findAllProducts(any(), any()))).willReturn(products);

        mvc.perform(get("/products").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void uploadMainImageAdmin() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "main-image",
                "image.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                "test".getBytes());

        mvc.perform(multipart("/products/1/main-image").file(file).contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void uploadMainImageCustomer() throws Exception {
        mvc.perform(multipart("/products/1/main-image").contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void uploadMainImageAnonymousUser() throws Exception {
        mvc.perform(multipart("/products/1/main-image").contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void uploadImageAdmin() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "image",
                "image.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                "test".getBytes());

        mvc.perform(multipart("/products/1/images").file(file).contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void uploadImageCustomer() throws Exception {
        mvc.perform(multipart("/products/1/images").contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void uploadImageAnonymousUser() throws Exception {
        mvc.perform(multipart("/products/1/images").contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteImageAdmin() throws Exception {
        mvc.perform(delete("/products/1/images/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void deleteImageCustomer() throws Exception {
        mvc.perform(delete("/products/1/images/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void deleteImageAnonymousUser() throws Exception {
        mvc.perform(delete("/products/1/images/1"))
                .andExpect(status().isForbidden());
    }

    private String getCreateProductRequestModelJson() throws Exception {
        CreateProductRequest request = new CreateProductRequest();
        request.setBrandId(1L);
        request.setCategoryId(1L);
        request.setName("test product");
        request.setPrice(new BigDecimal("99.99"));
        request.setWeight(new BigDecimal("2.5"));
        request.setQuantity(5);
        request.setShortDescription("short desc");
        request.setLongDescription("long desc");
        return new ObjectMapper().writeValueAsString(request);
    }
}