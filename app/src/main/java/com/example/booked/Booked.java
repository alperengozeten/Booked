package com.example.booked;

import android.app.Application;

import com.example.booked.models.User;

public class Booked extends Application {

    private static User currentUser;

    public Booked() {
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Booked.currentUser = currentUser;
    }
}
