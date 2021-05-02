package com.example.booked;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.booked.Adapter.MessageFriendsAdapter;
import com.example.booked.Adapter.MyPostAdapter;
import com.example.booked.Adapter.OfferRecyclerApapter;
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

public class MyMessages extends AppCompatActivity {

    RecyclerView myMessageFriendsRcycler;
    MessageFriendsAdapter messageFriendsAdapter;
    ArrayList<User> myFriends;
    ArrayList<String> myFriendsIds;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_messages);

        db = FirebaseFirestore.getInstance();
        myFriends = new ArrayList<User>();
        myFriendsIds = new ArrayList<String>();


        setMyFriendsIds();
        //setMyFriends();

        //setMessageFriendsView();



    }

    private void setMyFriends() {

        for( String userId : myFriendsIds )
        {
            db.collection("usersObj").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    myFriends.add(documentSnapshot.toObject(User.class));
                }
            });
        }

    }

    private void setMyFriendsIds() {

       /** db.collection("messageRooms").whereEqualTo("senderId",Booked.getCurrentUser().getDocumentId()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                        {
                            myFriendsIds.add(documentSnapshot.toObject(MessageRoom.class).getReceiverId());
                        }
                    }
                });

        db.collection("messageRooms").whereEqualTo("receiverId",Booked.getCurrentUser().getDocumentId()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                        {
                            myFriendsIds.add(documentSnapshot.toObject(MessageRoom.class).getSenderId());

                        }
                    }
                });*/

        db.collection("messageRooms").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if ( task.isSuccessful() ) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {

                        MessageRoom mR = documentSnapshot.toObject(MessageRoom.class);

                        assert mR != null;
                        if( mR.getReceiverId().equals(Booked.getCurrentUser().getDocumentId()))
                        {
                            myFriendsIds.add(mR.getSenderId());
                        }
                        else if ( mR.getSenderId().equals(Booked.getCurrentUser().getDocumentId()))
                        {
                            myFriendsIds.add(mR.getReceiverId());
                        }


                    }

                    setMessageFriendsView();

                }

            }
        });

    }



    private void setMessageFriendsView() {
        Log.d("message",myFriendsIds.toString());
        myMessageFriendsRcycler = (RecyclerView) findViewById(R.id.myMessageFriends);

        messageFriendsAdapter =  new MessageFriendsAdapter(myFriendsIds);

        myMessageFriendsRcycler.setAdapter(messageFriendsAdapter);
        myMessageFriendsRcycler.setLayoutManager(new LinearLayoutManager(this));

        messageFriendsAdapter.notifyDataSetChanged();

    }


}