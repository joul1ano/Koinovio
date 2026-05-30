package com.koinovio.management_service.auth;

import com.koinovio.management_service.dto.auth.AuthenticationRequestDTO;
import com.koinovio.management_service.dto.auth.RegisterRequestDTO;
import com.koinovio.management_service.dto.auth.AuthenticationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> register(@RequestBody @Validated RegisterRequestDTO requestDTO){
        return ResponseEntity.ok(authenticationService.register(requestDTO));

    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@RequestBody @Validated AuthenticationRequestDTO requestDTO){
        return ResponseEntity.ok(authenticationService.authenticate(requestDTO));

    }
}
