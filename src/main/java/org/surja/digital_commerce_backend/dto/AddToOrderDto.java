package org.surja.digital_commerce_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToOrderDto {
    private Long productId;
    private Integer quantity;
    private Long userId;
}
