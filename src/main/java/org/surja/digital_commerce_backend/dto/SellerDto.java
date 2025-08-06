package org.surja.digital_commerce_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellerDTO {
    private Long id;
    private String name;
    private String email;
    private Long companyId;
}
