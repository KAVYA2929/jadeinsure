package com.example.jadeinsure.model;

public class Service {
    private String id;
    private String name;
    private String description;
    private String iconUrl;
    private String category;

    public Service(String id, String name, String description, String iconUrl, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.iconUrl = iconUrl;
        this.category = category;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getIconUrl() { return iconUrl; }
    public String getCategory() { return category; }
}