package com.example.booked.models;

public class Evaluation {

    String comment;
    int rate;
    User evaluater;

    Evaluation(String comment, int rate, User evaluater)
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

    public int getRate() {
        return rate;
    }

    @Override
    public String toString() {

        return rate + " " +  comment + " by " + evaluater.getName() ;
    }
}

