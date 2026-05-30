package com.koinovio.management_service.service;

import com.koinovio.management_service.dto.apartment.ApartmentResponse;
import com.koinovio.management_service.dto.tenant.AssignTenantRequest;
import com.koinovio.management_service.dto.tenant.TenantResponse;
import com.koinovio.management_service.exceptions.ApartmentAlreadyOccupiedException;
import com.koinovio.management_service.exceptions.ResourceNotFoundException;
import com.koinovio.management_service.exceptions.TenantAlreadyInactiveException;
import com.koinovio.management_service.model.Tenant;
import com.koinovio.management_service.repository.ApartmentRepository;
import com.koinovio.management_service.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class TenantService {
    private final TenantRepository tenantRepository;
    private final ApartmentRepository apartmentRepository;

    @Transactional
    public ApartmentResponse assignNewTenant(AssignTenantRequest request) {
        //checking if apartment is already rented out
        if(tenantRepository.existsByApartment_IdAndIsActiveTrue(request.getApartmentId())){
            throw new ApartmentAlreadyOccupiedException("Apartment is already occupied");
        }
        Tenant tenant = Tenant.builder()
                .name(request.getName())
                .email(request.getEmail())
                .isActive(true)
                .apartment(apartmentRepository.findById(request.getApartmentId())
                        .orElseThrow(() -> new ResourceNotFoundException("Apartment not found")))
                .build();
        Tenant savedTenant = tenantRepository.save(tenant);

        return ApartmentResponse.builder()
                .apartmentId(request.getApartmentId())
                .buildingId(savedTenant.getApartment().getBuilding().getId())
                .floor(savedTenant.getApartment().getFloor())
                .size(savedTenant.getApartment().getSize())
                .tenant(TenantResponse.builder()
                        .id(savedTenant.getId())
                        .name(savedTenant.getName())
                        .email(savedTenant.getEmail())
                        .build())
                .build();
    }

    @Transactional
    public void removeTenant(Long id){
        Tenant tenant = tenantRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Tenant not found")
        );

        if(tenant.getIsActive()){
            tenant.setApartment(null);
            tenant.setIsActive(false);
            // tenantRepository.save(tenant); -- hibernate dirty checking
            return;
        }
        throw new TenantAlreadyInactiveException("Tenant with id: " + id + " is already inactive");

    }


}
