package com.example.booked.models;

import java.util.Comparator;

/**
 * This class is for posts to be sorted accordingly their titles.
 */
public class TitleComparator implements Comparator<Post> {

    //Methods
    /** This method compares titles of two post alphabetically.
     * @param p1 first post to compare
     * @param p2 second post to compare
     * */
    @Override
    public int compare(Post p1, Post p2) {

        return p1.getTitle().toLowerCase().compareTo(p2.getTitle().toLowerCase());
    }

}
