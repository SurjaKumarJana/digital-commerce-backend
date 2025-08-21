package org.surja.digital_commerce_backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class OrderDetailDTO {

    private Long orderId;

    private List<OrderItemDTO> orderItems;

    private Double orderTotalPrice;
}
