package com.dusan.webshop.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort.Direction;

@Getter
@Setter
public class ProductReviewPageParams extends PageParams {

    private ProductReviewSort sort = ProductReviewSort.UNSORTED;
    private Direction direction = Direction.ASC;

    public enum ProductReviewSort {
        DATE, RATING, UNSORTED
    }
}
