package com.example.booked.models;

public class Report {

    private String description;
    private int category;
    private User owner;

    public Report() {
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Report(String description, int category, User owner) {
        this.description = description;
        this.category = category;
        this.owner = owner;
    }

    public int getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public User getOwner() {
        return owner;
    }
}

