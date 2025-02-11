package com.example.jadeinsure.model;

import java.util.Date;

public class Policy {
    private String id;
    private String type;
    private String status;
    private Date startDate;
    private Date endDate;
    private double premium;

    // Constructor
    public Policy(String id, String type, String status, Date startDate, Date endDate, double premium) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.premium = premium;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public double getPremium() { return premium; }
    public void setPremium(double premium) { this.premium = premium; }
}