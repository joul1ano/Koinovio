package com.koinovio.management_service.dto.tenant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantResponse {
    private Long id;
    private String name;
    private String email;
}
