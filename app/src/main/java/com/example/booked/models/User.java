package com.example.booked.models;

import java.util.ArrayList;

public class User{

    String documentId;
    String userName;
    String email;
    String avatar;
    ArrayList<String> socialMedia;
    String phoneNumber;
    String university;
    boolean notifications;
    boolean isBanned;
    ArrayList<Book> wishlist;



    public boolean isNotifications() {
        return notifications;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public User() { //do not delete this
    }

    public User(String userName, String email,String documentId) {
        this.email = email;
        this.userName = userName;
        this.documentId = documentId;
        this.avatar = "";
        this.socialMedia = new ArrayList<>();
        socialMedia.add(null);
        socialMedia.add(null);
        socialMedia.add(null);
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
        socialMedia.add(null);
        socialMedia.add(null);
        socialMedia.add(null);
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
        socialMedia.add(null);
        socialMedia.add(null);
        socialMedia.add(null);
        this.notifications = true;
        this.isBanned = false;
        this.wishlist = new ArrayList<>();
    }

    public User(String userName, String email, String avatar, ArrayList<String> socialMedia, String phoneNumber, String university, boolean notifications, boolean isBanned, ArrayList<Book> wishlist) {
        this.userName = userName;
        this.email = email;
        this.avatar = avatar;
        this.socialMedia = socialMedia;
        socialMedia.add(null);
        socialMedia.add(null);
        socialMedia.add(null);
        this.phoneNumber = phoneNumber;
        this.university = university;
        this.notifications = notifications;
        this.isBanned = isBanned;
        this.wishlist = wishlist;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    //bunu silelim
    public void clearSocialMedia() {
        this.socialMedia.clear();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //bunu silelim
    public void addSocialMedia(String socialMediaProfileLink ) {
        //add degil set yapalım, int position girsin
        //socialMedia.set(position, socialMediaProfileLink);

        socialMedia.add( socialMediaProfileLink);
    }


    public void setSocialMedia(int position, String socialMediaProfileLink ) {
        socialMedia.set(position, socialMediaProfileLink);
    }



    // Gereksiz gibi duruyo zaten setPhoneNumber var
    public void addPhoneNumber() {

    }

    // Accessor Methods

    public ArrayList<String> getSocialMedia() {
        return this.socialMedia;
    }

    public String getName() {
        return this.userName;
    }

    public String getUserName() {
        return userName;
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
    public void setSocialMedia(ArrayList<String> socialMedia) {
        this.socialMedia = socialMedia;
    }

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public void setWishlist(ArrayList<Book> wishlist) {
        this.wishlist = wishlist;
    }

    public void setAvatar(String anAvatar) {
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

    public void removeBookFromWishlist(int index) {
         this.wishlist.remove(index);
    }

    public ArrayList<Book> getWishlist() {
        return wishlist;
    }


    @Override
    public String toString() {
        return this.userName + " from " + this.university + " University";
    }

    @Override
    public boolean equals(Object o) {

        if( o == null ){
            return false;
        }
        else if( o instanceof User){
            User otherUser = (User) o;
            return this.getDocumentId().equals( otherUser.getDocumentId());
        }
        else {
            return false;
        }

    }

}

