package com.dusan.webshop.api.controller;

import com.dusan.webshop.dto.response.PageResponseWrapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

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
}
