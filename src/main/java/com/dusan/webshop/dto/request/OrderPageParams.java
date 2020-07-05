package com.dusan.webshop.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort.Direction;

@Getter
@Setter
public class OrderPageParams extends PageParams {

    private OrderSort sort = OrderSort.UNSORTED;
    private Direction direction = Direction.ASC;

    public enum OrderSort {
        DATE, VALUE, WEIGHT, UNSORTED
    }
}
