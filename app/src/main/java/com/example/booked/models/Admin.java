package com.example.booked.models;

public class Admin extends User {

    public Admin(String name) {
        super( name);

    }


    public boolean banUser( User aUser, Program prog) {
        return prog.users.remove(aUser);
    }


    public boolean addBook( Book book, Program prog){

        return prog.books.add(book) && prog.bookProfiles.add(new BookProfile(book));



    }

    public void deletePost( Post aPost, Program prog) {

        //prog.showroom.deletePost(aPost, prog);

    }

    // thebook is added
    public void setBook( Book theBook, String newTitle, String newPic, Program prog) {

        prog.getBookProfile(theBook).setBook(new Book(newTitle, newPic));

        prog.books.set(prog.books.indexOf(theBook), new Book(newTitle, newPic));

    }
}
