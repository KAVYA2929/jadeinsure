package com.example.jadeinsure.model;

import java.util.Date;

public class Claim {
    private String id;
    private String policyId;
    private String description;
    private double amount;
    private Date incidentDate;
    private String status;
    private Date submissionDate;

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPolicyId() { return policyId; }
    public void setPolicyId(String policyId) { this.policyId = policyId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public Date getIncidentDate() { return incidentDate; }
    public void setIncidentDate(Date incidentDate) { this.incidentDate = incidentDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getSubmissionDate() { return submissionDate; }
    public void setSubmissionDate(Date submissionDate) { this.submissionDate = submissionDate; }
}