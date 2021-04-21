package com.example.booked;

import android.app.Application;

import com.example.booked.models.Post;
import com.example.booked.models.User;

public class Booked extends Application {

    private static User currentUser;
    private static Post currentPost;

    public Booked() {
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Booked.currentUser = currentUser;
    }

    public static Post getCurrentPost() {
        return currentPost;
    }

    public static void setCurrentPost(Post currentPost) {
        Booked.currentPost = currentPost;
    }
}
