package com.koinovio.management_service.controller;

import com.koinovio.management_service.dto.apartment.ApartmentResponse;
import com.koinovio.management_service.service.ApartmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/buildings/{id}/apartments")
@RequiredArgsConstructor
@Tag(name = "Apartments", description = "Operations related to apartments")
public class ApartmentController {
    private final ApartmentService apartmentService;

    @GetMapping
    public ResponseEntity<List<ApartmentResponse>> getAllApartmentsByBuildingId(@PathVariable Long id){
        return ResponseEntity.ok(apartmentService.getAllApartmentsByBuildingId(id));

    }


}
