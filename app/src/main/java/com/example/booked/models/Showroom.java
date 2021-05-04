package com.example.booked.models;

import java.util.ArrayList;

import java.util.Collections;

/**
 * Showroom lists posts. Users can buy and sell books from here
 */
public class Showroom implements Filterable, Sortable{

    //Properties
    ArrayList<Post> posts;

    //Constructors
    /**
     * Constructor that initializes posts as empty list
     * */
    public Showroom()
    {
        posts = new ArrayList<Post>();
    }

    /**
     * Constructor that initializes posts with given Arraylist
     * @param posts List of posts
     * */
    public Showroom(ArrayList<Post> posts)
    {
        this.posts = posts;
    }

    //Methods
    /**
     * Accessor method of posts.
     * */
    public ArrayList<Post> getPosts() {
        return posts;
    }


    /**This filter posts according to their university property.
     * @param aUniversity
     * @return
     */
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

    /**This filter posts according to their course property.
     * @param course
     * @return
     */
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

    /**This filter posts according to its price and boundaries given.
     * @param minPrice
     * @param maxPrice
     * @return
     */
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

    /**This filter posts according to given word
     * @param word
     * @return
     */
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

    /**
     * This method sorts showroom's post list according to prices.
     * @param isLowToHigh if it is true showroom will be sorted from the lowest price to the highest
     *               if it is false showroom will be sorted from the highest  price to the lowest
     * */
    @Override
    public void sortByPrice(boolean isLowToHigh) {

        Collections.sort(posts);

        if(!isLowToHigh)
        {
            Collections.reverse(posts);
        }
    }

    /**
     * This method sorts showroom's post list alphabetically.
     * @param isAToZ if it is true showroom will be sorted alphabetically from a to z
     *               if it is false showroom will be sorted alphabetically from z to a
     * */
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

