package com.example.booked;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import androidx.annotation.NonNull;

import com.example.booked.models.Book;
import com.example.booked.models.BookProfile;
import com.example.booked.models.Message;
import com.example.booked.models.MessageRoom;
import com.example.booked.models.Post;
import com.example.booked.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**This class stores necessary and temporary information while app is running.
 * Global class.
 *
 * @author NoExpection
 * @version 2021 Spring
 */
public class Booked extends Application {

    private static User currentUser;
    private static Post currentPost;
    static BookProfile currentBookProfile; // = new BookProfile(currentBook);
    static Book currentBook;
    private static User currentSeller;
    public static final String CHANNEL_ID = "post_notifications";

    public Booked() {
    }

    //accessor methods

    /**Get method for currentBook in the global class.
     * */
    public static  Book getCurrentBook() {
        return currentBook;
    }

    /**Get method for currentSeller in the global class.
     * */
    public static User getCurrentSeller() {
        return currentSeller;
    }

    /**Get method for currentBookProfile in the global class.
     * */
    public static com.example.booked.models.BookProfile getCurrentBookProfile() {
        return currentBookProfile;
    }

    /**Get method for currentUser in the global class.
     * */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**Get method for currentPost in the global class.
     * */
    public static Post getCurrentPost() {
        return currentPost;
    }


    //mutator methods

    /**Set method for currentPost in the global class.
     * @param currentPost
     * */
    public static void setCurrentPost(Post currentPost) {
        Booked.currentPost = currentPost;
    }

    /**Set method for currentSeller in the global class.
     * @param currentSeller
     * */
    public static void setCurrentSeller(User currentSeller) {
        Booked.currentSeller = currentSeller;
    }

    /**Set method for currentUser in the global class.
     * @param currentUser
     * */
    public static void setCurrentUser(User currentUser) {
        Booked.currentUser = currentUser;
    }

    /**Set method for currentBookProfile in the global class.
     * @param currentBookProfile
     * */
    public static void setCurrentBookProfile(BookProfile currentBookProfile) {
        Booked.currentBookProfile = currentBookProfile;
    }

    //database methods

    /**This method updates post's information in database
     * @param documentId
     * @param newPost
     *
     * */
    public static boolean updatePostInDatabase(String documentId, Post newPost)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return db.collection("postsObj").document(documentId).set(newPost).isComplete();
    }

    /**This method updates bookProfile's information in database
     * @param documentId
     * @param newBookProfile
     *
     * */
    public static boolean updateBookProfileInDatabase(String documentId, BookProfile newBookProfile)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return db.collection("bookProfileObj").document(documentId).set(newBookProfile).isComplete();
    }

    /**This method updates user's information in database
     * @param documentId
     * @param newUser
     *
     * */
    public static boolean updateUserInDatabase(String documentId, User newUser)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return db.collection("usersObj").document(documentId).set(newUser).isComplete();
    }

    /**This method delete a post from everywhere,when user or admin wanted to so.
     * @param p post will be deleted
     * */
    public static void deletePost(Post p) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("postsObj").document(p.getId()).delete();

        db.collection("bookProfileObj").document(p.getBook().getId()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        ArrayList<Post> offers = documentSnapshot.toObject(BookProfile.class).getOffers();
                        offers.remove(p);
                        // update in database
                        updateBookProfileOffers(offers, p.getBook().getId());
                    }
                });
    }
    /** Update book profile when a post deleted
     * */
    private static void updateBookProfileOffers(ArrayList<Post> offers, String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("bookProfileObj").document(id).update("offers",offers);

    }


    /**
     * This method creates a unique messageRoom id for two user, when they start to send message to each other
     * */
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


    /**
     * This method call when users send messages to each other,
     * it saves messages to database also
     * @param m message
     * */
    public static void sendMessage(Message m)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("messageRooms").document(findTheirMessageRoom(m.getReceiverId(),m.getSenderId())).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        //get messages
                        ArrayList<Message> messages = task.getResult().toObject(MessageRoom.class).getMessages();
                        //add new message
                        messages.add(m);
                        //update database
                        updateMessages(messages, findTheirMessageRoom(m.getReceiverId(),m.getSenderId()) );
                    }
                });

    }

    /**
     * Updates messages in database
     * */
    private static void updateMessages(ArrayList<Message> messages, String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("messageRooms").document(id).update("messages",messages);


    }



}
