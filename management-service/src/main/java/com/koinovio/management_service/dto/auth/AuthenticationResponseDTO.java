package com.koinovio.management_service.dto.auth;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthenticationResponseDTO {
    private String token;
}
