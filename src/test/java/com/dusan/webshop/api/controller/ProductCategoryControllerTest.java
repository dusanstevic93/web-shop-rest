package com.dusan.webshop.api.controller;

import com.dusan.webshop.dto.request.CreateProductCategoryRequest;
import com.dusan.webshop.dto.response.ProductCategoryResponse;
import com.dusan.webshop.service.ProductCategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductCategoryController.class)
class ProductCategoryControllerTest extends ControllerTestSetup {

    @MockBean
    private ProductCategoryService categoryService;

    @Autowired
    private MockMvc mvc;

    @Test
    void getCategoryTree() throws Exception {
        List<ProductCategoryResponse> categories = new ArrayList<>();
        given(categoryService.getCategoryTree()).willReturn(categories);

        mvc.perform(get("/categories").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createCategoryAdmin() throws Exception {
        CreateProductCategoryRequest request = new CreateProductCategoryRequest();
        request.setName("test category");
        String json = new ObjectMapper().writeValueAsString(request);

        mvc.perform(post("/categories").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void createCategoryCustomer() throws Exception {
        mvc.perform(post("/categories").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void createCategoryCustomerAnonymousUser() throws Exception {
        mvc.perform(post("/categories").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createSubcategoryAdmin() throws Exception {
        CreateProductCategoryRequest request = new CreateProductCategoryRequest();
        request.setName("test subcategory");
        String json = new ObjectMapper().writeValueAsString(request);

        mvc.perform(post("/categories/1/subcategories").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void createSubcategoryCustomer() throws Exception {
        mvc.perform(post("/categories/1/subcategories").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void createSubcategoryAnonymousUser() throws Exception {
        mvc.perform(post("/categories/1/subcategories").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateCategoryAdmin() throws Exception {
        CreateProductCategoryRequest request = new CreateProductCategoryRequest();
        request.setName("Updated category");
        String json = new ObjectMapper().writeValueAsString(request);

        mvc.perform(put("/categories/1").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void updateCategoryCustomer() throws Exception {
        mvc.perform(put("/categories/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void updateCategoryAnonymousUser() throws Exception {
        mvc.perform(put("/categories/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void uploadCategoryImageAdmin() throws Exception {
        MockMultipartFile file = new MockMultipartFile("image", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "test".getBytes());
        mvc.perform(multipart("/categories/1/image").file(file))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void uploadCategoryImageAdminEmptyFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("image", new byte[0]);
        mvc.perform(multipart("/categories/1/image").file(file))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void uploadCategoryImageAdminUnsupportedFileFormat() throws Exception {
        MockMultipartFile file = new MockMultipartFile("image", "test.xml", MediaType.APPLICATION_XML_VALUE, "test".getBytes());
        mvc.perform(multipart("/categories/1/image").file(file))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void uploadCategoryImageCustomer() throws Exception {
        mvc.perform(multipart("/categories/1/image"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void uploadCategoryImageAnonymousUser() throws Exception {
        mvc.perform(multipart("/categories/1/image"))
                .andExpect(status().isForbidden());
    }
}