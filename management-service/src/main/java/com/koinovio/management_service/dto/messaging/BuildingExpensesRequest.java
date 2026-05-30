package com.koinovio.management_service.dto.messaging;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildingExpensesRequest {
    @Min(1)
    @Max(12)
    private Integer month;
    @Min(2026)
    private Integer year;
    private List<ExpenseItem> expenses;
}
