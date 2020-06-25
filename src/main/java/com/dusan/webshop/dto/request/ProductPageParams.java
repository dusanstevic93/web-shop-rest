package com.dusan.webshop.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPageParams extends PageParams {

    private ProductSort sort;

    public enum ProductSort {
        NAME, PRICE, RATING
    }
}
