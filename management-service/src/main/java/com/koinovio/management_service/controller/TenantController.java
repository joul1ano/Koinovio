package com.koinovio.management_service.controller;

import com.koinovio.management_service.dto.apartment.ApartmentResponse;
import com.koinovio.management_service.dto.tenant.AssignTenantRequest;
import com.koinovio.management_service.service.TenantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tenants")
@RequiredArgsConstructor
public class TenantController {
    private final TenantService tenantService;

    @PostMapping
    public ResponseEntity<ApartmentResponse> assignNewTenant(@RequestBody @Valid AssignTenantRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(tenantService.assignNewTenant(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeTenant(@PathVariable Long id){
        tenantService.removeTenant(id);
        return ResponseEntity.noContent().build();
    }
}
