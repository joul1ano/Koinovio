package com.koinovio.management_service.repository;

import com.koinovio.management_service.model.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
    Integer countByBuilding_Id(Long buildingId);
    List<Apartment> findAllByBuilding_Id(Long buildingId);
}
