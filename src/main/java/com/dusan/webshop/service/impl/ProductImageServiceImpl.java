package com.dusan.webshop.service.impl;

import com.dusan.webshop.dto.request.UploadedImage;
import com.dusan.webshop.service.ProductImageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Override
    public void saveImage(long productId, UploadedImage image) {

    }

    @Override
    public void deleteImage(long productId, String imageName) {

    }

    @Override
    public String getImageLink(long productId, String imageName) {
        return null;
    }

    @Override
    public List<String> getLinksToAllProductImages(long productId) {
        return null;
    }
}
