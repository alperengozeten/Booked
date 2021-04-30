package com.example.booked.models;

public class Book {

    String bookName;
    String picture;

    String id;

    public Book() {// dont delete this
    }

    public Book(String name) {
        this.bookName = name;
        randomKeyGenerator(10);
        picture = "";
    }

    public Book(String name, String pic)
    {
        bookName = name;
        picture = pic;
        //randomKeyGenerator(10);
    }

    public Book(String bookName, String picture, String id) {
        this.bookName = bookName;
        this.picture = picture;
        this.id = id;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getBookName() {
        return bookName;
    }

    public String getPicture() {
        return picture;
    }

    @Override
    public boolean equals(Object o) {

        if( o == null ){
            return false;
        }
        else if( o instanceof Book){
            Book otherBook = (Book) o;
            return this.getId().equals( otherBook.getId());
        }
        else {
            return false;
        }

    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {

        return  getBookName();
    }

    public String getId() {
        return id;
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

