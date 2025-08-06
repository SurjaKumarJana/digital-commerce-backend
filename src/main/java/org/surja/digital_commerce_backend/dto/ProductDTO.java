package org.surja.digital_commerce_backend.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Setter
@Getter
public class ProductDTO {

    private String name;
    private String description;

    private Double price;
    private Integer stocks;

    private String imageUrl;

    private Long companyId;
    private Long categoryId;
}
