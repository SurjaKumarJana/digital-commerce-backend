package org.surja.digital_commerce_backend.dto;


import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String name;
    private String email;

    private String password;
}
