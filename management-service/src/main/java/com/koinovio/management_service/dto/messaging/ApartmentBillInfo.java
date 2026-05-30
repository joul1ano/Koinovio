package com.koinovio.management_service.dto.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentBillInfo {
    private Long apartmentId;
    private Integer floor;
    private Double size;
    private String tenantName;
    private String tenantEmail;
}