package org.surja.digital_commerce_backend.dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {
    private Long id;
    private String code;
    private String message;

}
