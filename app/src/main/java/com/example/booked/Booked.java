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

public class Booked extends Application {

    private static User currentUser;
    private static Post currentPost;
    static BookProfile currentBookProfile; // = new BookProfile(currentBook);
    static Book currentBook;// = new Book("Example Book","picbook");
    private static User currentSeller;

    public static final String CHANNEL_ID = "post_notifications";

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

    public static void sendMessage(Message m)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("messageRooms").document(findTheirMessageRoom(m.getReceiverId(),m.getSenderId())).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        ArrayList<Message> messages = task.getResult().toObject(MessageRoom.class).getMessages();
                        messages.add(m);
                        updateMessages(messages, findTheirMessageRoom(m.getReceiverId(),m.getSenderId()) );
                    }
                });

    }

    private static void updateMessages(ArrayList<Message> messages, String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("messageRooms").document(id).update("messages",messages);


    }

   /** public static ArrayList<Message> getMessagesBetweenThem(String userId1, String userId2)
    {
        ArrayList<Message> allMessages = new ArrayList<Message>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("messageRooms").document(findTheirMessageRoom(userId1,userId2)).collection("messages").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot document: queryDocumentSnapshots)
                {
                    allMessages.add(document.toObject(Message.class));
                }
            }
        });

        return allMessages;
    }*/

}
