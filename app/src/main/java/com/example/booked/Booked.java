package com.example.booked;

import android.app.Application;
import android.net.Uri;

import com.example.booked.models.Book;
import com.example.booked.models.BookProfile;
import com.example.booked.models.Evaluation;
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

    private static String examplePath;

    public static String getExamplePath() {
        return examplePath;
    }

    public static void setExamplePath(String examplePath) {
        Booked.examplePath = examplePath;
    }

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
}
