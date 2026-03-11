package com.jayanth.swiftguard_engine.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "transactions")
public class Transaction {
    @Id
    private String id;
    private String userId;
    private Double amount;
    private String location;
    private String status;
    private Integer riskScore;
}
