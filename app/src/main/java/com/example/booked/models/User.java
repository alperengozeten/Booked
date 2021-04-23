package com.example.booked.models;

import java.util.ArrayList;

public class User{

    String userName;
    String email;
    String avatar;
    ArrayList<String> socialMedia;
    String phoneNumber;
    String university;
    boolean notifications;
    boolean isBanned;
    ArrayList<Book> wishlist;

    public User(String userName) {
        this.email = "";
        this.userName = userName;
        this.avatar = "";
        this.socialMedia = new ArrayList<>();
        this.phoneNumber = "";
        this.university = "";
        this.wishlist = new ArrayList<>();
        this.notifications = true;
        this.isBanned = false;
    }

    public User(String email, String userName) {
        this.email = email;
        this.userName = userName;
        this.avatar = "";
        this.socialMedia = new ArrayList<>();
        this.phoneNumber = "";
        this.university = "";
        this.wishlist = new ArrayList<>();
        this.notifications = true;
        this.isBanned = false;
    }

    public User(String userName, String email, String avatar, String phoneNumber, String university )
    {
        this.userName = userName;
        this.email = email;
        this.avatar = avatar;
        this.phoneNumber = phoneNumber;
        this.university = university;
        this.socialMedia = new ArrayList<>();
        this.notifications = true;
        this.isBanned = false;
        this.wishlist = new ArrayList<>();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addSocialMedia(String socialMediaProfileLink ) {
        socialMedia.add( socialMediaProfileLink);
    }

    // Gereksiz gibi duruyo zaten setPhoneNumber var
    public void addPhoneNumber() {

    }

    // Accessor Methods

    public String getName() {
        return this.userName;
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
        this.notifications = !this.notifications;
    }

    public void addBookToWishlist(Book b) {
        this.wishlist.add(b);
    }

    public boolean removeBookFromWishlist(Book b) {
        return this.wishlist.remove(b);
    }

    public ArrayList<Book> getWishlist() {
        return wishlist;
    }

    /**  void addPostToMyPosts(Post post)
     {
     posts.add(post);
     }
     */

    @Override
    public String toString() {
        return this.userName + " from " + this.university + " University";
    }

}

