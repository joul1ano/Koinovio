package com.koinovio.management_service.dto.building;

import com.koinovio.management_service.dto.apartment.CreateApartmentRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBuildingRequest {
    @NotBlank(message = "Address cannot be blank")
    private String address;
    @NotBlank(message = "Zip Code cannot be blank")
    private String zipCode;
    @Positive(message = "Floors must be a positive number")
    private Integer floors;
    @Valid
    private List<CreateApartmentRequest> apartmentsList;
}
