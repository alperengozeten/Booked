package com.example.booked.models;


/**
 * This is the model class of Evaluation
 * Evaluation are made by users, to make comments and give a rating to a book
 *
 */
public class Evaluation {

    //Properties
    String comment;
    double rate;
    User evaluater;

    //Constructors
    public Evaluation(String comment, double rate, User evaluater)
    {
        this.comment = comment;
        this.rate = rate;
        this.evaluater = evaluater;

    }

    public Evaluation() {
    }

    //Methods

    /**
     * Set method for comment
     * @param comment is comment made by the user
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Set method for rating
     * @param rate is a rating (out of 5) given by the user
     */
    public void setRate(double rate) {
        this.rate = rate;
    }

    /**
     * Set method for evaluater
     * @param evaluater is the user evaluating the book
     */
    public void setEvaluater(User evaluater) {
        this.evaluater = evaluater;
    }

    /**
     * Get method for comments
     * @return returns the comment made by user
     */
    public String getComment() {
        return comment;
    }

    /**
     * Get method for evaluater
     * @return returns the user evaluating the book
     */
    public User getEvaluater() {
        return evaluater;
    }

    /**
     * Get method for rate
     * @return returns the rate given by the user
     */
    public double getRate() {
        return rate;
    }

    /**
     * toString method for Evaluation
     * @return returns the rating and the comment made by user as a string
     */
    @Override
    public String toString() {

        return rate + " " +  comment + " by " + evaluater.getName() ;
    }
}

