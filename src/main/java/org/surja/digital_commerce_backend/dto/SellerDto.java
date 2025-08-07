package org.surja.digital_commerce_backend.dto;

import lombok.*;
import org.surja.digital_commerce_backend.entity.User;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerDTO {
    private Long id;
    private String name;
    private String email;
    private Long companyId;


    public static SellerDTO buildDTOFromUser(User user) {
        SellerDTO sellerDTO = SellerDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .companyId(user.getCompany().getId())
                .build();

        return sellerDTO;
    }
}
