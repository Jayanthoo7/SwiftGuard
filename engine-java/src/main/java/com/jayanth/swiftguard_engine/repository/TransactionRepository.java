package com.jayanth.swiftguard_engine.repository;

import com.jayanth.swiftguard_engine.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
    List<Transaction> findByStatus(String status);
}
