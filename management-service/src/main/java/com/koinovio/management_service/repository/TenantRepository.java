package com.koinovio.management_service.repository;

import com.koinovio.management_service.model.Tenant;
import jakarta.validation.constraints.Positive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TenantRepository extends JpaRepository<Tenant, Long> {
    boolean existsByApartment_IdAndIsActiveTrue(Long apartmentId);


}
