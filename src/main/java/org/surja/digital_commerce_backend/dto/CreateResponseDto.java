package org.surja.digital_commerce_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CreateResponseDTO {
    private Long id;
    private String errorCode;
    private String message;
}
