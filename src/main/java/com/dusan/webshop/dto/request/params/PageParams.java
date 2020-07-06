package com.dusan.webshop.dto.request.params;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
public class PageParams {

    @Min(0)
    private int page = 0;

    @Min(1)
    @Max(25)
    private int size = 10;
}
