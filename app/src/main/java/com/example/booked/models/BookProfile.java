package com.example.booked.models;

import java.util.ArrayList;
import java.util.Collections;

public class BookProfile implements Sortable{


    double rating;
    ArrayList<Evaluation> evalutions;
    ArrayList<Post> offers;
    Book book;


    public BookProfile(Book book)
    {
        this.book = book;
        offers = new ArrayList<Post>();
        evalutions = new ArrayList<Evaluation>();
        rating = 0;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void addEvalution(Evaluation e)
    {
        evalutions.add(e);
        this.getMeanRate();
    }

    /**void deleteEvalution(int index)
     {
     evalutions.remove(index);
     this.getMeanRate();
     }*/

    public void getMeanRate()
    {
        rating = 0;
        for (Evaluation e : evalutions){
            rating = rating + e.getRate();
        }

        rating = rating / evalutions.size();
    }


    public void addPost( Post aPost)
    {
        offers.add(aPost);
    }

    boolean deletePost(Post aPost)
    {
        return offers.remove(aPost);
    }

    public Book getBook() {
        return book;
    }

    public ArrayList<Evaluation> getEvalutions() {
        return evalutions;
    }

    public double getRating() {
        return rating;
    }

    public ArrayList<Post> getOffers() {
        return offers;
    }

    @Override
    public String toString() {
        return "Name: " + getBook().getBookName() +" Rate: " + getRating() + "  Evalu:" + evalutions + " offers; " + offers;


    }

    @Override
    public void sortByPrice(boolean isLowToHigh) {

        Collections.sort(offers);

        if(!isLowToHigh)
        {
            Collections.reverse(offers);
        }
    }

    @Override
    public void sortByLetter(boolean isAToZ) {
        Collections.sort(offers, new TitleComparator());

    }
}

