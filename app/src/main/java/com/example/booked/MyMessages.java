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

    ArrayList<String> myFriendsIds;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_messages);

        db = FirebaseFirestore.getInstance();

        myFriendsIds = new ArrayList<String>();


        setMyFriendsIds();




    }



    private void setMyFriendsIds() {


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

                }

                setMessageFriendsView();
            }
        });

    }



    private void setMessageFriendsView() {

        myMessageFriendsRcycler = (RecyclerView) findViewById(R.id.myMessageFriends);
        myMessageFriendsRcycler.setLayoutManager(new LinearLayoutManager(this));

        Log.d("messageSon",myFriendsIds.toString());
        messageFriendsAdapter =  new MessageFriendsAdapter(myFriendsIds);

        myMessageFriendsRcycler.setAdapter(messageFriendsAdapter);

        messageFriendsAdapter.notifyDataSetChanged();

    }



}