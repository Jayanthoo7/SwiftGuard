package com.jayanth.swiftguard_engine.service;

import com.jayanth.swiftguard_engine.model.Transaction;
import com.jayanth.swiftguard_engine.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FraudAuditService {

    @Autowired
    private TransactionRepository repository;

    @Scheduled(fixedDelay = 5000) // Runs every 5 seconds
    public void processPendingTransactions() {
        List<Transaction> pendingTransactions = repository.findByStatus("PENDING");

        if (!pendingTransactions.isEmpty()) {
            System.out.println("-------------------------------------------------");
            System.out.println("Found " + pendingTransactions.size() + " pending transaction(s). Running audit...");
        }

        for (Transaction t : pendingTransactions) {
            int riskScore = calculateRiskScore(t);
            t.setRiskScore(riskScore);

            if (riskScore >= 70) {
                t.setStatus("FLAGGED_FOR_REVIEW");
                System.out.println("🚨 Transaction " + t.getId() + " FLAGGED! Score: " + riskScore);
            } else {
                t.setStatus("APPROVED");
                System.out.println("✅ Transaction " + t.getId() + " APPROVED. Score: " + riskScore);
            }
            repository.save(t);
        }
    }

    private int calculateRiskScore(Transaction t) {
        int score = 0;
        if (t.getAmount() != null && t.getAmount() > 5000) score += 40;
        if (t.getLocation() == null || t.getLocation().equalsIgnoreCase("Unknown")) score += 50;
        if (t.getLocation() != null && t.getLocation().equalsIgnoreCase("HighRiskZone")) score += 60;
        return score;
    }
}
