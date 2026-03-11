package com.jayanth.swiftguard_engine.service;

import com.jayanth.swiftguard_engine.model.Transaction;
import com.jayanth.swiftguard_engine.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FraudAuditServiceTest {

    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private FraudAuditService fraudAuditService;

    private Transaction safeTransaction;
    private Transaction riskyTransaction;

    @BeforeEach
    void setUp() {
        // Setup a normal transaction
        safeTransaction = new Transaction();
        safeTransaction.setId("tx_safe_1");
        safeTransaction.setAmount(1000.0);
        safeTransaction.setLocation("Bengaluru");
        safeTransaction.setStatus("PENDING");

        // Setup a transaction that triggers our fraud rules
        riskyTransaction = new Transaction();
        riskyTransaction.setId("tx_risk_9");
        riskyTransaction.setAmount(8000.0); // > 5000 adds 40 points
        riskyTransaction.setLocation("HighRiskZone"); // adds 60 points
        riskyTransaction.setStatus("PENDING");
    }

    @Test
    void testProcessPendingTransactions_SafeTransaction_IsApproved() {
        // 1. Arrange: Tell the mock database to return our safe transaction
        when(repository.findByStatus("PENDING")).thenReturn(List.of(safeTransaction));

        // 2. Act: Run the engine
        fraudAuditService.processPendingTransactions();

        // 3. Assert: Verify the logic worked correctly
        assertEquals("APPROVED", safeTransaction.getStatus(), "Status should be APPROVED");
        assertEquals(0, safeTransaction.getRiskScore(), "Risk score should be 0");
        verify(repository, times(1)).save(safeTransaction); // Verify it saved to DB
    }

    @Test
    void testProcessPendingTransactions_RiskyTransaction_IsFlagged() {
        // 1. Arrange: Tell the mock database to return our risky transaction
        when(repository.findByStatus("PENDING")).thenReturn(List.of(riskyTransaction));

        // 2. Act: Run the engine
        fraudAuditService.processPendingTransactions();

        // 3. Assert: Verify the logic worked correctly
        assertEquals("FLAGGED_FOR_REVIEW", riskyTransaction.getStatus(), "Status should be FLAGGED");
        assertEquals(100, riskyTransaction.getRiskScore(), "Risk score should be 100");
        verify(repository, times(1)).save(riskyTransaction); // Verify it saved to DB
    }
}
