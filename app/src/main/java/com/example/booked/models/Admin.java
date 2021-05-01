package com.example.booked.models;

public class Admin extends User {

    public Admin(String name, String email) {
        super( name,email);

    }


    public boolean banUser( User aUser) {
        return true;
    }


    public boolean addBook( Book book){

        return true;



    }

    public void deletePost( Post aPost) {



    }

    // thebook is added
    public void setBook( Book theBook, String newTitle, String newPic) {



    }
}
