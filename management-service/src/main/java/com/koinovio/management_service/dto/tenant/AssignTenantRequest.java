package com.koinovio.management_service.dto.tenant;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignTenantRequest {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @Email(message = "Wrong email format")
    private String email;
    @Positive(message = "Apartment id must be positive")
    private Long apartmentId;
}
