package org.surja.digital_commerce_backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class OrderDetailDto {

    private Long orderId;

    private List<OrderItemDto> orderItems;

    private Double orderTotalPrice;
}
