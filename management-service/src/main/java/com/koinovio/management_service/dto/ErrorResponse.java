package com.koinovio.management_service.dto;

import lombok.*;
import org.springframework.stereotype.Service;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResponse {
    private String message;
}
