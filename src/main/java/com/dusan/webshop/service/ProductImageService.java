package com.dusan.webshop.service;

import com.dusan.webshop.dto.request.UploadedImage;

public interface ProductImageService {

    void saveImage(long productId, UploadedImage image);
    void deleteImage(long productId, String imageName);
}
