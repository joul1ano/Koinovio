package com.koinovio.management_service.dto.apartment;

import com.koinovio.management_service.dto.tenant.CreateTenantRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateApartmentRequest {
    @PositiveOrZero(message = "Floor must be positive or zero")
    private Integer floor;
    @Positive(message = "Size must be positive")
    private Double size;
    @Valid
    private CreateTenantRequest tenant; //might be null if apartment does not have tenant yet
}
