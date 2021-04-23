package com.example.booked.models;

public class Evaluation {

    String comment;
    double rate;
    User evaluater;

    public Evaluation(String comment, double rate, User evaluater)
    {
        this.comment = comment;
        this.rate = rate;
        this.evaluater = evaluater;

    }

    public String getComment() {
        return comment;
    }

    public User getEvaluater() {
        return evaluater;
    }

    public double getRate() {
        return rate;
    }

    @Override
    public String toString() {

        return rate + " " +  comment + " by " + evaluater.getName() ;
    }
}

