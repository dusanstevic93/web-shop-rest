package com.dusan.webshop.storage;

import java.util.Map;

public interface ImageStorage {

    Map<String, String> saveImage(byte[] bytes);
    void deleteImage(String publicId);
}
