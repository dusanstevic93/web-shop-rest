package com.dusan.webshop.dto.request.params;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort.Direction;

@Getter
@Setter
public class ProductPageParams extends PageParams {

    private ProductSort sort = ProductSort.UNSORTED;
    private Direction direction = Direction.ASC;

    public enum ProductSort {
        NAME, PRICE, RATING, UNSORTED
    }
}
