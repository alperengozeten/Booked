package com.example.booked;

import android.app.Application;

import com.example.booked.models.Book;
import com.example.booked.models.BookProfile;
import com.example.booked.models.Post;
import com.example.booked.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Booked extends Application {

    private static User currentUser;
    private static Post currentPost;
    static BookProfile currentBookProfile; // = new BookProfile(currentBook);
    static Book currentBook;// = new Book("Example Book","picbook");
    private static User currentSeller;


    public Booked() {
    }

    public static  Book getCurrentBook() {
        return currentBook;
    }


    public static com.example.booked.models.BookProfile getCurrentBookProfile() {
        return currentBookProfile;
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

    public static void setCurrentSeller(User currentSeller) {
        Booked.currentSeller = currentSeller;
    }

    public static User getCurrentSeller() {
        return currentSeller;
    }

    public static void setCurrentBookProfile(BookProfile currentBookProfile) {
        Booked.currentBookProfile = currentBookProfile;
    }

    public static boolean updatePostInDatabase(String documentId, Post newPost)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return db.collection("postsObj").document(documentId).set(newPost).isComplete();
    }

    public static boolean updateBookProfileInDatabase(String documentId, BookProfile newBookProfile)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return db.collection("bookProfileObj").document(documentId).set(newBookProfile).isComplete();
    }

    public static boolean updateUserInDatabase(String documentId, User newUser)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return db.collection("usersObj").document(documentId).set(newUser).isComplete();
    }


    public static void deletePost(Post p) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("postsObj").document(p.getId()).delete();

        db.collection("bookProfileObj").document(p.getBook().getId()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        ArrayList<Post> offers = documentSnapshot.toObject(BookProfile.class).getOffers();
                        offers.remove(p);

                        updateBookProfileOffers(offers, p.getBook().getId());
                    }
                });
    }

    private static void updateBookProfileOffers(ArrayList<Post> offers, String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("bookProfileObj").document(id).update("offers",offers);

    }

    public static String findTheirMessageRoom(String id1, String id2)
    {
        if( id1.compareTo(id2) > 0)
        {
            return id1 + id2;
        }
        else
        {
            return id2 + id1;
        }

    }


}
