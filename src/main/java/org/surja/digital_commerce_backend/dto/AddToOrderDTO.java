package org.surja.digital_commerce_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToOrderDTO {
    private Long productId;
    private Integer quantity;
    private Long userId;
}
