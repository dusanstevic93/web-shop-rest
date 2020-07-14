package com.dusan.webshop.storage;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@AllArgsConstructor
@Component
class ImageStorageImpl implements ImageStorage {

    private Cloudinary cloudinary;

    @Override
    public Map<String, String> saveImage(byte[] bytes) {
        Map<String, String> response;
        try {
            response = cloudinary.uploader().upload(bytes, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new ImageHandlingException("Image upload error", e);
        }
        return response;
    }

    @Override
    public void deleteImage(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new ImageHandlingException("Image delete error", e);
        }
    }
}
