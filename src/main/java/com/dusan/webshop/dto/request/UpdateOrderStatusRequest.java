package com.dusan.webshop.dto.request;

import com.dusan.webshop.entity.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateOrderStatusRequest {

    @NotNull
    private OrderStatus orderStatus;
}
