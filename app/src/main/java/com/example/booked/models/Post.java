package com.example.booked.models;

import java.util.ArrayList;
import java.util.Comparator;

public class Post implements Reportable, Comparable<Post> {

    String title;
    String description;
    String university;
    String course;
    int price;
    ArrayList<String> pictures;
    ArrayList<Report> reports;
    User seller;
    Boolean isSold;
    Book theBook;

    Comparator<Post> TitleComparator = new Comparator<Post>() {

        @Override
        public int compare(Post p1, Post p2) {

            return p1.getTitle().compareTo(p2.getTitle());
        }
    };


    public Post (String title, String description, String course, int price, String aPicture, Book aBook, User seller) {
        this.seller = seller;
        this.title = title;
        this.description = description;
        this.university = this.seller.getUniversity();
        this.course = course;
        this.price = price;
        pictures = new ArrayList<>();
        pictures.add( aPicture);
        reports = new ArrayList<>();
        this.isSold = false;
        this.theBook = aBook;

    }

    public void editPost (String title, String description, int price, Book aBook) {
        this.title = title;
        this.description = description;
        this.price = price;
        theBook = aBook;
    }

    public boolean addPicture( String aPicture) {
        return this.pictures.add( aPicture);
    }

    public boolean deletePicture( String aPicture) {
        return this.pictures.remove( aPicture);
    }

    public void setPicture( String aPicture, int index) {
        this.pictures.set(index, aPicture);
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCourse() {
        return this.course;
    }

    public int getPrice() {
        return this.price;
    }

    public User getSeller() {
        return this.seller;
    }

    public Book getBook() {
        return this.theBook;
    }

    public Boolean getIsSold() {
        return isSold;
    }

    public ArrayList<String> getPictures() {
        return pictures;
    }

    public ArrayList<Report> getReports() {
        return reports;
    }

    public String getUniversity() {
        return university;
    }

    @Override
    public String toString() {
        return this.description + " for " +  this.price + " TL " + " by " + this.seller.getName() +
                "\n name of the book: " + this.theBook +"\n";
    }

    @Override
    public void report(String description, String catagory, User owner) {

        this.reports.add( new Report(description, catagory, owner) );

    }

    @Override
    public int compareTo(Post post) {

        return this.getPrice() - post.getPrice();

    }

}

