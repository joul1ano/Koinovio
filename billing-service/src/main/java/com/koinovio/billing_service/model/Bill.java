package com.koinovio.billing_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "bills")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    @Id
    private String id;
    private Long buildingId;
    private String buildingAddress;
    private Integer month;
    private Integer year;
    private Long apartmentId;
    private Integer floor;
    private String tenantName;
    private String tenantEmail;
    private List<BillItem> billItems;
    private Double totalAmount;
    private LocalDateTime documentCreatedAt;
}
