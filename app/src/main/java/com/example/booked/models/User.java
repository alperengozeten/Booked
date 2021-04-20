package com.example.booked.models;

import java.util.ArrayList;

public class User{

    String name;
    String email;
    String avatar;
    ArrayList<String> socialMedia;
    String phoneNumber;
    String university;
    boolean notifications;
    boolean isBanned;

    public User(String name) {
        this.name = name;
    }

    public User(String name, String email, String avatar, String phoneNumber, String university )
    {
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.phoneNumber = phoneNumber;
        this.university = university;
        this.socialMedia = new ArrayList<>();
        this.notifications = true;
        this.isBanned = false;

    }

    public void addSocialMedia( String socialMediaProfileLink ) {
        socialMedia.add( socialMediaProfileLink);
    }

    // Gereksiz gibi duruyo zaten setPhoneNumber var
    public void addPhoneNumber() {

    }

    // Accessor Methods

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getUniversity() {
        return this.university;
    }

    // Mutator Methods

    public void setAvatar( String anAvatar) {
        this.avatar = anAvatar;
    }

    public void setPhoneNumber( String aPhoneNumber) {
        // belirli sayıda karaktere sınırla
        this.phoneNumber = aPhoneNumber;
    }

    public void setUniversity( String aUniversity) {
        this.university = aUniversity;
    }

    public void changeNotifications() {
        if ( this.notifications) {
            this.notifications = false;
        }
        else {
            this.notifications = true;
        }
    }

    /**  void addPostToMyPosts(Post post)
     {
     posts.add(post);
     }
     */

    @Override
    public String toString() {
        return this.name + " from " + this.university + " University";
    }

}

