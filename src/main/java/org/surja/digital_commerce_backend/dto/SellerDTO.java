package org.surja.digital_commerce_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.surja.digital_commerce_backend.entity.User;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerDTO {
    private Long id;
    // in order to add some extra validation while taking input parameters
    // we use thise annotation
    // and we need a extra dependency for this :
    //      <dependency>
    //			<groupId>org.springframework.boot</groupId>
    //			<artifactId>spring-boot-starter-validation</artifactId>
    //		</dependency>
    @NotNull
    @Size(min =2)
    private String name;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
    @NotNull
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
