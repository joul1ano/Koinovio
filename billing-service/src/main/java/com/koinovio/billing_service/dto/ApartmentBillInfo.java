package com.koinovio.billing_service.dto;

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