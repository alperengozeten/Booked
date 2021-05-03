package com.example.booked.models;

/**
 * This is the model class of Book
 */

public class Book {

    //Properties
    String bookName;
    String picture;

    String id;

    //Constructors
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

    }

    public Book(String bookName, String picture, String id) {
        this.bookName = bookName;
        this.picture = picture;
        this.id = id;
    }

    //Methods

    /**
     * Set method for book name
     * @param bookName is the desired book name
     */
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    /**
     * Set method for book's picture
     * @param picture is the url of the picture
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     *Get method for book's name
     * @return book's name as a string
     */
    public String getBookName() {
        return bookName;
    }

    /**
     * Get method for book's picture
     * @return book's picture's url
     */
    public String getPicture() {
        return picture;
    }

    /**
     * Equals method for book object in order to check if two objects are the same book
     * @param o is an object
     * @return true if object o and this are the same book
     */
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

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * ToString method for book object
     * @return book's name as a string
     */
    @Override
    public String toString() {

        return  getBookName();
    }

    /**
     * getId method
     * @return book's id
     */
    public String getId() {
        return id;
    }

    /**
     * This method generates a random string of length n
     * @param n is the length of string
     */
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

