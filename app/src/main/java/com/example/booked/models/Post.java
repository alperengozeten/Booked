package com.example.booked.models;

import java.util.ArrayList;
import java.util.Comparator;

public class Post implements Reportable, Comparable<Post> {

    String title;
    String description;
    String university;
    String course;
    String id;
    int price;
    String picture;
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
        this.id = id;
        picture = aPicture;
        reports = new ArrayList<>();
        this.isSold = false;
        this.theBook = aBook;
        randomKeyGenerator(11);

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void editPost (String title, String description, int price, Book aBook) {
        this.title = title;
        this.description = description;
        this.price = price;
        theBook = aBook;
    }

    public void deletePicture( String aPicture) {
        this.picture = "";
    }

    public void setPicture( String aPicture) {
        this.picture = aPicture;
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

    public String getPicture() {
        return picture;
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

    // function to generate a random string of length n
    private void randomKeyGenerator(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        this.id = sb.toString();
    }

}

