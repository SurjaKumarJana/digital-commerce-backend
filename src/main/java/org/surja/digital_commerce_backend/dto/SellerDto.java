package org.surja.digital_commerce_backend.dto;

import lombok.Getter;
import lombok.Setter;
import org.surja.digital_commerce_backend.entity.Company;

@Getter
@Setter
public class SellerDto {
    private Long id;
    private String name;
    private String email;
    private Long companyId;
}
