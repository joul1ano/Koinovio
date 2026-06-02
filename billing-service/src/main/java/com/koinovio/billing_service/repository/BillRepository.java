package com.koinovio.billing_service.repository;

import com.koinovio.billing_service.model.Bill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends MongoRepository<Bill, String> {
    boolean existsByBuildingIdAndMonthAndYear(Long buildingId, Integer month, Integer year);
}
