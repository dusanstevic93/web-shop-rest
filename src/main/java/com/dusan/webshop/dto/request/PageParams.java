package com.dusan.webshop.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageParams {

    private int page = 0;
    private int size = 10;
}
