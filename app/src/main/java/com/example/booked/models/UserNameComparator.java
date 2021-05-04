package com.example.booked.models;

import java.util.Comparator;

/**
 * This class for posts to be sorted accordingly their users' name.
 * */
public class UserNameComparator implements Comparator<Post> {

    //Methods
    /** This method compares seller names of two post alphabetically.
     * @param p1 first post to compare
     * @param p2 second post to compare
     * */
    @Override
    public int compare(Post p1, Post p2) {

        return p1.getSeller().getName().toLowerCase().compareTo(p2.getSeller().getName().toLowerCase());
    }

}
