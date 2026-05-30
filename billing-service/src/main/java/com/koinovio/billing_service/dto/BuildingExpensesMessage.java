package com.koinovio.billing_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildingExpensesMessage {
    private Long buildingId;
    private String buildingAddress;
    private Integer month;
    private Integer year;
    private List<ExpenseItem> expenses;
    private List<ApartmentBillInfo> apartments;
}
