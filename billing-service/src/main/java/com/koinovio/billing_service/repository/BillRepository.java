package com.koinovio.billing_service.repository;

import com.koinovio.billing_service.model.Bill;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BillRepository extends MongoRepository<Bill, String> {
}
