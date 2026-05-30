package com.koinovio.management_service.service;

import com.koinovio.management_service.dto.apartment.ApartmentResponse;
import com.koinovio.management_service.dto.tenant.TenantResponse;
import com.koinovio.management_service.mapper.ApartmentMapper;
import com.koinovio.management_service.mapper.TenantMapper;
import com.koinovio.management_service.model.Apartment;
import com.koinovio.management_service.repository.ApartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final ApartmentMapper apartmentMapper;

    public List<ApartmentResponse> getAllApartmentsByBuildingId(Long id) {
        List<Apartment> apartments = apartmentRepository.findAllByBuilding_Id(id);

        return apartments.stream().map(apartmentMapper::toApartmentResponse).toList();
    }
}
