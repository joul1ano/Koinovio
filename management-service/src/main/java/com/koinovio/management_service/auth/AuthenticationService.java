package com.koinovio.management_service.auth;

import com.koinovio.management_service.config.JwtService;
import com.koinovio.management_service.dto.auth.AuthenticationRequestDTO;
import com.koinovio.management_service.dto.auth.AuthenticationResponseDTO;
import com.koinovio.management_service.dto.auth.RegisterRequestDTO;
import com.koinovio.management_service.exceptions.UserAlreadyExistsException;
import com.koinovio.management_service.model.Role;
import com.koinovio.management_service.model.User;
import com.koinovio.management_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponseDTO register(RegisterRequestDTO requestDTO) {
        if (userRepository.findByEmail(requestDTO.getEmail()).isPresent()){
            throw new UserAlreadyExistsException("A user with this email already exists");
        }
        var user = User.builder()
                .name(requestDTO.getName())
                .email(requestDTO.getEmail())
                .dateCreated(LocalDate.now())
                .passwordHash(passwordEncoder.encode(requestDTO.getPassword()))
                .role(Role.ADMIN)
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponseDTO.builder().token(jwtToken).build();
    }

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO requestDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                requestDTO.getEmail(),
                requestDTO.getPassword()
        ));

        var user = userRepository.findByEmail(requestDTO.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponseDTO.builder().token(jwtToken).build();
    }
}
