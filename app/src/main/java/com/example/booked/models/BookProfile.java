package com.example.booked.models;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This is the model class of BookProfile
 */

public class BookProfile implements Sortable{

    //Properties
    double rating;
    ArrayList<Evaluation> evalutions;
    ArrayList<Post> offers;
    Book book;

    //Constructors

    // for firebase
    public BookProfile() {
    }

    /**This constructor initializes book.
     * @param book
     * */
    public BookProfile(Book book)
    {
        this.book = book;
        offers = new ArrayList<Post>();
        evalutions = new ArrayList<Evaluation>();
        rating = 0;
    }
    //Methods

    /**
     * Set method for book
     * @param book
     */
    public void setBook(Book book) {
        this.book = book;
    }


    /**
     * Method to add evaluations about a book profile
     * @param e is the evaluation made by the user
     */
    public void addEvalution(Evaluation e)
    {
        evalutions.add(e);
        this.getMeanRate();
    }

    /**
     * Set method for rating of a book profile
     * @param rating is the rating given to the book profile
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * Set method for evaluations on a book profile
     * @param evalutions is the ArrayList of evaluations given to the book profile by users
     */
    public void setEvalutions(ArrayList<Evaluation> evalutions) {
        this.evalutions = evalutions;
    }

    /**
     * Set method for offers on a book profile
     * @param offers is the rating given to the book profile
     */
    public void setOffers(ArrayList<Post> offers) {
        this.offers = offers;
    }


    /**
     * This method gets the mean rate of ratings on a book profile
     */
    public void getMeanRate()
    {
        rating = 0;
        for (Evaluation e : evalutions){
            rating = rating + e.getRate();
        }

        rating = rating / evalutions.size();
    }

    /**
     * This method adds the posts(offers) generated by users, to the book profile
     * @param aPost is a post object created by users
     */
    public void addPost( Post aPost)
    {
        offers.add(aPost);
    }

    /**
     * This method deletes the posts(offers) generated by users, from the book profile
     * @param aPost is a post object created by users
     */
    boolean deletePost(Post aPost)
    {
        return offers.remove(aPost);
    }

    /**
     * Get method for book object
     * @return returns the book object
     */
    public Book getBook() {
        return book;
    }

    /**
     * Get method for evaluations made on a book profile
     * @return returns the ArrayList evaluations
     */
    public ArrayList<Evaluation> getEvalutions() {
        return evalutions;
    }

    /**
     * Get method for rating given to a book profile
     * @return returns rating
     */
    public double getRating() {
        return rating;
    }

    /**
     * Get method for offers(posts) created on a book profile
     * @return returns offers
     */
    public ArrayList<Post> getOffers() {
        return offers;
    }

    /**
     * toString method for BookProfile
     * @return returns name, rating, evaluations and offers made on a book profile as a string
     */
    @Override
    public String toString() {
        return "Name: " + getBook().getBookName() +" Rate: " + getRating() + "  Evalu:" + evalutions + " offers; " + offers;


    }

    /**
     * This method sorts the offer prices on a book in low to high order
     * @param isLowToHigh is used to chek the order
     */
    @Override
    public void sortByPrice(boolean isLowToHigh) {

        Collections.sort(offers);

        if(!isLowToHigh)
        {
            Collections.reverse(offers);
        }
    }

    /**
     * This method sorts the user names of offers on a book in alphabetical order(from A to Z)
     * @param isAToZ is used to check the order
     */
    @Override
    public void sortByLetter(boolean isAToZ) {
        Collections.sort(offers, new UserNameComparator());

        if(!isAToZ)
        {
            Collections.reverse(offers);
        }

    }
}

