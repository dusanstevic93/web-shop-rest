package com.dusan.webshop.api.controller;

import com.dusan.webshop.api.controller.exception.EmptyFileException;
import com.dusan.webshop.api.controller.exception.UnsupportedFileFormatException;
import com.dusan.webshop.dto.response.PageResponseWrapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class ControllerUtils {

    static <T> PageResponseWrapper<T> createPageResponseWrapper(Page<T> page) {
        PageResponseWrapper.PageMetadata metadata = PageResponseWrapper.PageMetadata.of(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
        return new PageResponseWrapper<>(page.getContent(), metadata);
    }

    static void validateUploadedImage(MultipartFile image) {
        if (image.isEmpty())
            throw new EmptyFileException("File must not be empty");

        if (!image.getContentType().equals("image/jpeg"))
            throw new UnsupportedFileFormatException(image.getContentType() + " is not supported");
    }
}
