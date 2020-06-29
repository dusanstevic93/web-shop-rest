package com.dusan.webshop.storage;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dusan.webshop.dto.request.UploadedImage;
import com.dusan.webshop.service.exception.ImageHandlingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Component
public class ImageStorageImpl implements ImageStorage {

    private Cloudinary cloudinary;

    @Override
    public Map<String, String> saveImage(String folder, UploadedImage image) {
        Map<String, Object> params = new HashMap<>();
        params.put("folder", folder);
        Map<String, String> response;
        try {
            response = cloudinary.uploader().upload(image.getBytes(), params);
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
