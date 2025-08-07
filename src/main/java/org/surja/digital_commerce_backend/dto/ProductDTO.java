package org.surja.digital_commerce_backend.dto;

import lombok.*;
import org.springframework.stereotype.Service;
import org.surja.digital_commerce_backend.entity.Product;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;
    private String name;
    private String description;

    private Double price;
    private Integer stocks;

    private String imageUrl;

    private Long companyId;
    private Long categoryId;



    public static  ProductDTO buildProductDTOFromProduct(Product product){
        ProductDTO productDTO = ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stocks(product.getStocks())
                .imageUrl(product.getImageUrl())
                .companyId(product.getCompany().getId())
                .categoryId(product.getCategory().getId())
                .build();
        return productDTO;

    }


}
