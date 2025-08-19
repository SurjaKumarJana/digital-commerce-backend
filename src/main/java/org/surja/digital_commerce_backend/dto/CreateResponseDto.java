package org.surja.digital_commerce_backend.dto;

import lombok.*;


@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateResponseDTO {
    private Long id;
    private String errorCode;
    private String message;
}
