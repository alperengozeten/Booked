package com.example.booked.models;

import java.util.Comparator;

public class UserNameComparator implements Comparator<Post> {


    @Override
    public int compare(Post p1, Post p2) {

        return p1.getSeller().getName().compareTo(p2.getSeller().getName());
    }

}
