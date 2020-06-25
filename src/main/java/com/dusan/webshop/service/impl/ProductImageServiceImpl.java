package com.dusan.webshop.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import com.dusan.webshop.dto.request.UploadedImage;
import com.dusan.webshop.service.ProductImageService;
import com.dusan.webshop.service.exception.ImageHandlingException;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class ProductImageServiceImpl implements ProductImageService {

    private Cloudinary cloudinary;

    @Override
    public void saveImage(long productId, UploadedImage image) {
        // image name should be without file extension
        String publicId = "products/" + productId + "/" + FilenameUtils.removeExtension(image.getName());
        Map<String, Object> params = new HashMap<>();
        params.put("public_id", publicId);

        try {
            cloudinary.uploader().upload(image.getBytes(), params);
        } catch (IOException e) {
            throw new ImageHandlingException("Image upload error", e);
        }
    }

    @Override
    public void deleteImage(long productId, String imageName) {
        String publicId = "products/" + productId + "/" + FilenameUtils.removeExtension(imageName);

        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new ImageHandlingException("Image delete error", e);
        }
    }

    @Override
    public String getImageLink(long productId, String imageName) {
        String publicId = "products/" + productId + "/" + FilenameUtils.removeExtension(imageName);
        ApiResponse response;
        try {
            response = cloudinary.api().resource(publicId, ObjectUtils.emptyMap());
        } catch (Exception e) {
            throw new ImageHandlingException("Image retrieving error", e);
        }

        return (String) response.get("url");
    }

    @Override
    public List<String> getLinksToAllProductImages(long productId) {
        String prefix = "products/" + productId + "/";
        Map<String, Object> params = new HashMap<>();
        params.put("prefix", prefix);
        params.put("type", "upload");
        ApiResponse response;
        try {
            response = cloudinary.api().resources(params);
        } catch (Exception e) {
            throw new ImageHandlingException("Image retrieving error", e);
        }

        List<String> links = new ArrayList<>();
        List<Map<String, Object>> mapList = ((List<Map<String, Object>>) response.get("resources"));
        for (Map<String, Object> map : mapList){
            links.add((String)map.get("url"));
        }
        return links;
    }
}
