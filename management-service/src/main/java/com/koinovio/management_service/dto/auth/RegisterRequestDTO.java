package com.koinovio.management_service.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterRequestDTO {
    private String name;
    @Email(message = "Wrong email format")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
