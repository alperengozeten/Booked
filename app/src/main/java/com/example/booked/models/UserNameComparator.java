package com.example.booked.models;

import java.util.Comparator;

/**
 * This class for posts to be sorted accordingly their users' name.
 * */
public class UserNameComparator implements Comparator<Post> {


    @Override
    public int compare(Post p1, Post p2) {

        return p1.getSeller().getName().toLowerCase().compareTo(p2.getSeller().getName().toLowerCase());
    }

}
