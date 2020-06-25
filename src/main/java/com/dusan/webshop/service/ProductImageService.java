package com.dusan.webshop.service;

import com.dusan.webshop.dto.request.UploadedImage;

import java.util.List;

public interface ProductImageService {

    void saveImage(long productId, UploadedImage image);
    void deleteImage(long productId, String imageName);
    String getImageLink(long productId, String imageName);
    List<String> getLinksToAllProductImages(long productId);
}
