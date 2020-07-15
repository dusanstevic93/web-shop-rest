package com.dusan.webshop.api.controller;

import com.dusan.webshop.dto.request.CreateProductReviewRequest;
import com.dusan.webshop.service.ProductReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductReviewController.class)
class ProductReviewControllerTest extends ControllerTestSetup {

    @MockBean
    private ProductReviewService reviewService;

    @Autowired
    private MockMvc mvc;

    @Test
    @WithCustomMockUser(roles = "CUSTOMER")
    void createProductReviewCustomerRole() throws Exception {
        CreateProductReviewRequest request = new CreateProductReviewRequest();
        request.setProductId(1L);
        request.setRating(5);
        request.setReview("test");

        String json = new ObjectMapper().writeValueAsString(request);

        mvc.perform(post("/reviews").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createProductReviewAdminRole() throws Exception {
        mvc.perform(post("/reviews").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void createProductReviewAnonymousUser() throws Exception {
        mvc.perform(post("/reviews").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void getProductReviews() throws Exception {
        given(reviewService.findAllProductReviews(any(), any())).willReturn(new PageImpl<>(Collections.emptyList()));

        mvc.perform(get("/reviews").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteProductReviewRoleAdmin() throws Exception {
        mvc.perform(delete("/reviews/1_1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void deleteProductReviewRoleCustomer() throws Exception {
        mvc.perform(delete("/reviews/1_1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void deleteProductReviewAnonymousUser() throws Exception {
        mvc.perform(delete("/reviews/1_1"))
                .andExpect(status().isForbidden());
    }
}