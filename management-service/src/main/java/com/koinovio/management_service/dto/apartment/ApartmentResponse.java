package com.koinovio.management_service.dto.apartment;

import com.koinovio.management_service.dto.tenant.TenantResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentResponse {
    private Long apartmentId;
    private Long buildingId;
    private Integer floor;
    private Double size;
    private TenantResponse tenant;
}
