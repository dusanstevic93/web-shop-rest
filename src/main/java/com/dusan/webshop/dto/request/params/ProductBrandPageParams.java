package com.dusan.webshop.dto.request.params;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort.Direction;


@Getter
@Setter
public class ProductBrandPageParams extends PageParams {

    private ProductBrandSort sort;
    private Direction direction = Direction.ASC;

    public enum ProductBrandSort {
        NAME
    }
}
