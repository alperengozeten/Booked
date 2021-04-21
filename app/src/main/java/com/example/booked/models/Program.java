package com.example.booked.models;

import java.util.ArrayList;

public class Program {


    ArrayList<User> users;
    ArrayList<Book> books;
    ArrayList<BookProfile> bookProfiles;
    Showroom showroom;

    public Program()
    {
        users = new ArrayList<User>();
        books = new ArrayList<Book>();
        bookProfiles = new ArrayList<BookProfile>();
        showroom = new Showroom();
    }

    public BookProfile getBookProfile(Book book)
    {
        for(BookProfile profile: bookProfiles)
        {
            if(profile.getBook().equals(book))
                return profile;
        }
        return null;
    }

    public static void main(String[] args)
    {

        Program pro = new Program();

        Admin a1 = new Admin("a1");
        User u1 = new User("user1");
        User u2 = new User("user2");


        /*
        a1.addBook(new Book("book1", "pic1"), pro);
        a1.addBook(new Book("book2", "pic2"), pro);

        pro.bookProfiles.get(0).addEvalution(new Evaluation("good", 4, u1));
        pro.bookProfiles.get(0).addEvalution(new Evaluation("bad", 2, u2));

        pro.showroom.createPost("post1", "good", "mat 132", 10, pro.books.get(0), u1, pro);
        pro.showroom.createPost("post2", "goodish", "mat 132", 20, pro.books.get(0), u2, pro);

        pro.bookProfiles.get(0).sortByPrice(false);

        //System.out.println("Users: " + pro.users);
        System.out.println("books:" + pro.books);
        System.out.println("bookProfiles:" + pro.bookProfiles);
        System.out.println("Showroom: " + pro.showroom.getPosts());
        System.out.println("Results:" + pro.showroom.filterByWord("post1"));
        */
    }
}

