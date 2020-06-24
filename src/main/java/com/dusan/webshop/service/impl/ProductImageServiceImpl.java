package com.dusan.webshop.service.impl;

import com.dusan.webshop.dto.request.UploadedImage;
import com.dusan.webshop.service.ProductImageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@AllArgsConstructor
@Service
public class ProductImageServiceImpl implements ProductImageService {

    private String folder = "/images";

    @Override
    public void saveImage(long productId, UploadedImage image) {
        Path path = Paths.get(folder, String.valueOf(productId));
        try {

            if (Files.notExists(path))
                Files.createDirectories(path);

            Files.write(path, image.getBytes());

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
