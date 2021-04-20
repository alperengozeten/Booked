package com.example.booked.models;

public class Report {

    private String description;
    private String category;
    private User owner;

    public Report( String description, String category, User owner) {
        this.description = description;
        this.category = category;
        this.owner = owner;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public User getOwner() {
        return owner;
    }
}

