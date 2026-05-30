package com.koinovio.management_service.dto.building;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BuildingResponse {
    private Long buildingId;
    private String address;
    private String zipCode;
    private Integer floors;
    private Integer numberOfApartments;
}
