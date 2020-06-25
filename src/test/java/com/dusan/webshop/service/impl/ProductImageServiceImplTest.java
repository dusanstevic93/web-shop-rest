package com.dusan.webshop.service.impl;

import com.dusan.webshop.CloudinaryConfig;
import com.dusan.webshop.dto.request.UploadedImage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {CloudinaryConfig.class, ProductImageServiceImpl.class})
class ProductImageServiceImplTest {

    @Autowired
    private ProductImageServiceImpl imageService;

    @Test
    void testUploadImage() throws IOException {
        long productId = 1L;
        File file = new ClassPathResource("images/testimage.jpg").getFile();
        UploadedImage image = new UploadedImage();
        image.setName(file.getName());
        image.setBytes(Files.readAllBytes(file.toPath()));

        imageService.saveImage(productId, image);
    }

    @Test
    void testDeleteImage() throws IOException {
        long productId = 1L;
        imageService.deleteImage(productId, "testimage.jpg");
    }

    @Test
    void testGetImageLink() throws IOException {
        long productId = 1L;
        String link = imageService.getImageLink(productId, "testimage.jpg");
        assertNotNull(link);
    }

    @Test
    void testGetLinksToAllProductImages() throws IOException {
        long productId = 1L;
        List<String> links = imageService.getLinksToAllProductImages(productId);
        System.out.println(links);
    }
}