package com.dusan.webshop.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort.Direction;

@Getter
@Setter
public class ProductPageParams extends PageParams {

    private ProductSort sort;
    private Direction direction;

    public enum ProductSort {
        NAME, PRICE, RATING
    }
}
