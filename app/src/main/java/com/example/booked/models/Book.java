package com.example.booked.models;

public class Book {

    String bookName;
    String picture;


    public Book(String name, String pic)
    {
        bookName = name;
        picture = pic;
    }

    public String getBookName() {
        return bookName;
    }

    public String getPicture() {
        return picture;
    }

    public boolean equals(Book otherBook) {
        //pic ekle
        return this.getBookName() == otherBook.getBookName();

    }

    @Override
    public String toString() {

        return  getBookName();
    }
}

