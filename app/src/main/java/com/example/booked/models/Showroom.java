package com.example.booked.models;

import java.util.ArrayList;

import java.util.Collections;

public class Showroom implements Filterable, Sortable{

    ArrayList<Post> posts;

    public Showroom()
    {
        posts = new ArrayList<Post>();
    }

    public Showroom(ArrayList<Post> posts)
    {
        this.posts = posts;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    /**void createPost(String title, String description, String course, int price, Book aBook, User seller)
    {
        Post aPost = new Post(title, description, course, price, course, aBook, seller);
        //prog.bookProfiles.get(prog.books.indexOf(aBook)).addPost(aPost);

        posts.add(aPost);
    }*/

    boolean deletePost(Post aPost)
    {
        //prog.bookProfiles.get(prog.books.indexOf(aPost.getBook())).deletePost(aPost);
        return posts.remove(aPost);
    }

    @Override
    public Showroom filterByUniversity(String aUniversity) {

        ArrayList<Post> postsFiltered = new ArrayList<Post>();

        for(Post aPost : this.getPosts())
        {
            if(aPost.getUniversity().equals(aUniversity))
            {
                postsFiltered.add(aPost);
            }
        }
        return new Showroom(postsFiltered);
    }

    @Override
    public Showroom filterByCourse(String course) {

        ArrayList<Post> postsFiltered = new ArrayList<Post>();

        for(Post aPost : this.getPosts())
        {
            if(aPost.getCourse().equals(course))
            {
                postsFiltered.add(aPost);
            }
        }
        return new Showroom(postsFiltered);
    }

    @Override
    public Showroom filterByPrice(int minPrice, int maxPrice) {
        ArrayList<Post> postsFiltered = new ArrayList<Post>();

        for(Post aPost : this.getPosts())
        {
            if(aPost.getPrice() <= maxPrice && aPost.getPrice() >= minPrice)
            {
                postsFiltered.add(aPost);
            }
        }
        return new Showroom(postsFiltered);
    }

    @Override
    public Showroom filterByWord(String word) {
        ArrayList<Post> postsFiltered = new ArrayList<Post>();

        if ( word == null || word.length() == 0 ) {
            return this;
        }
        else {
            String filterPattern = word.toLowerCase().trim();
            for ( Post apost : this.getPosts() ) {
                if ( apost.getTitle().contains(filterPattern)) {
                    postsFiltered.add(apost);
                }
            }
            return new Showroom( postsFiltered);
        }
    }

    @Override
    public void sortByPrice(boolean isLowToHigh) {

        Collections.sort(posts);

        if(!isLowToHigh)
        {
            Collections.reverse(posts);
        }
    }

    @Override
    public void sortByLetter(boolean isAToZ) {

        Collections.sort(posts, new TitleComparator());

        if(!isAToZ)
        {
            Collections.reverse(posts);
        }
    }

    @Override
    public String toString() {
        return posts.toString();
    }
}

