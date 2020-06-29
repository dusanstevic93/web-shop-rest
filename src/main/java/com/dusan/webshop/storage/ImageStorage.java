package com.dusan.webshop.storage;

import com.dusan.webshop.dto.request.UploadedImage;

import java.util.Map;

public interface ImageStorage {

    Map<String, String> saveImage(String folder, UploadedImage image);
    void deleteImage(String publicId);
}
