# 🛡️ SwiftGuard: Operations-Focused Fraud Audit System

SwiftGuard is a high-performance, polyglot microservices architecture designed for **operational fulfillment** and **real-time transaction troubleshooting**. It showcases a robust approach to building **scalable applications** by decoupling high-speed ingestion from complex business logic.



## 🎯 Swift Internship Alignment
This project was engineered to demonstrate core competencies required for the **Java Developer Intern** role:
* **Scalable Architecture:** Utilizing a Node.js API Gateway to handle concurrent requests without impacting audit latency.
* **Operational Excellence:** Automated background processing of "Pending" transactions to simulate real-world e-commerce fulfillment workflows.
* **Thorough Testing:** 100% pass rate on core business logic using **JUnit 5** and **Mockito** for reliable debugging and quality assurance.

## 🏗️ Technical Architecture
The system utilizes a distributed pattern to ensure fault tolerance and separation of concerns:

* **Ingestion Layer (Node.js/Express):** A lightweight service optimized for high-throughput ingestion. It serves as the entry point for all transaction data.
* **Persistence Layer (MongoDB):** A flexible document store that acts as the source of truth and the integration bridge between microservices.
* **Audit & Logic Layer (Java/Spring Boot):** The "Engine" of the application. It implements a polling consumer pattern to scan the database, calculate risk scores, and transition transaction states.
* **Monitoring Dashboard:** A real-time web interface providing visibility into system operations and transaction statuses.

## 🧪 Quality Assurance & Testing
To meet the requirement for "thorough testing and debugging," the Java engine includes a comprehensive test suite:
* **Unit Tests:** Validates the `FraudAuditService` logic.
* **Mocking:** Uses Mockito to isolate business logic from database dependencies.
* **State Verification:** Ensures transactions are correctly flagged based on specific risk-score thresholds.

## 🚀 Deployment & Operations
### Prerequisites
* MongoDB (Port 27017)
* Java 17+ / Maven
* Node.js

### Execution Steps
1.  **Start Gateway:** `cd gateway-node && npm install && node server.js`
2.  **Start Audit Engine:** `cd engine-java && ./mvnw spring-boot:run`
3.  **Run Test Suite:** `cd engine-java && ./mvnw test`

---
**Developed by Jayanth** *Focused on building reliable, scalable, and maintainable software solutions.*
