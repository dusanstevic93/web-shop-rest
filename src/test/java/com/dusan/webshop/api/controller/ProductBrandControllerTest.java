package com.dusan.webshop.api.controller;

import com.dusan.webshop.dto.request.CreateProductBrandRequest;
import com.dusan.webshop.dto.response.ProductBrandResponse;
import com.dusan.webshop.service.ProductBrandService;
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

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductBrandController.class)
class ProductBrandControllerTest extends ControllerTestSetup {

    @MockBean
    private ProductBrandService brandService;

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(roles = "ADMIN")
    void createProductBrandAdmin() throws Exception {
        CreateProductBrandRequest request = new CreateProductBrandRequest();
        request.setBrandName("test");

        String json = new ObjectMapper().writeValueAsString(request);

        mvc.perform(
                post("/brands").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void createProductBrandCustomer() throws Exception {
        mvc.perform(post("/brands").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void createProductBrandAnonymousUser() throws Exception {
        mvc.perform(post("/brands").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void getProductBrandById() throws Exception {
        ProductBrandResponse response = new ProductBrandResponse();
        response.setId(1L);
        response.setLogoUrl("logourl");
        response.setName("test brand");
        given(brandService.findProductBrandById(1L)).willReturn(response);

        mvc.perform(get("/brands/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateProductBrandAdmin() throws Exception {
        CreateProductBrandRequest request = new CreateProductBrandRequest();
        request.setBrandName("test");

        String json = new ObjectMapper().writeValueAsString(request);

        mvc.perform(put("/brands/1").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void updateProductBrandCustomer() throws Exception {
        mvc.perform(put("/brands/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void updateProductBrandAnonymousUser() throws Exception {
        mvc.perform(put("/brands/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void getAllProductBrands() throws Exception {
        Page<ProductBrandResponse> page = new PageImpl<>(Collections.emptyList());

        given(brandService.findAllProductBrands(any())).willReturn(page);

        mvc.perform(get("/brands").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void uploadProductBrandLogoAdmin() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile("image", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "test".getBytes());
        mvc.perform(multipart("/brands/1/logo").file(mockFile))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void uploadProductBrandLogoCustomer() throws Exception {
        mvc.perform(multipart("/brands/1/logo")).andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void uploadProductBrandLogoAnonymousUser() throws Exception {
        mvc.perform(multipart("/brands/1/logo")).andExpect(status().isForbidden());
    }
}