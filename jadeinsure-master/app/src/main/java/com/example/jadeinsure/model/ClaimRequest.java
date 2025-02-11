package com.example.jadeinsure.model;

public class ClaimRequest {
    private String policyId;
    private String description;
    private double amount;
    private String incidentDate;
    private String claimType;

    // Constructor
    public ClaimRequest(String policyId, String description, double amount, 
                       String incidentDate, String claimType) {
        this.policyId = policyId;
        this.description = description;
        this.amount = amount;
        this.incidentDate = incidentDate;
        this.claimType = claimType;
    }

    // Getters and setters
    public String getPolicyId() { return policyId; }
    public void setPolicyId(String policyId) { this.policyId = policyId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getIncidentDate() { return incidentDate; }
    public void setIncidentDate(String incidentDate) { this.incidentDate = incidentDate; }

    public String getClaimType() { return claimType; }
    public void setClaimType(String claimType) { this.claimType = claimType; }
} 