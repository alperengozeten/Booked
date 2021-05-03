package com.example.booked.models;

import java.util.Comparator;

/**
 * This class is for posts to be sorted accordingly their titles.
 * */
public class TitleComparator implements Comparator<Post> {


    @Override
    public int compare(Post p1, Post p2) {

        return p1.getTitle().compareTo(p2.getTitle());
    }

}
