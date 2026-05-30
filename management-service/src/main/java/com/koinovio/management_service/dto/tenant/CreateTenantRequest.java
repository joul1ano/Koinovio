package com.koinovio.management_service.dto.tenant;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTenantRequest {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @Email(message = "Wrong email format")
    private String email;
}
