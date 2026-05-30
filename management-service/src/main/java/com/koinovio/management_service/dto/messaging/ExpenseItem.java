package com.koinovio.management_service.dto.messaging;

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
public class ExpenseItem {
    @NotBlank(message = "Expense description cannot be blank")
    private String description;
    @Positive(message = "Expense cost must be positive")
    private Double amount;
}
