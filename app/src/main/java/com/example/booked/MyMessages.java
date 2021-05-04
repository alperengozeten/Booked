package com.example.booked;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

/**
 * This page shows users, their list of message friends.
 * */
public class MyMessages extends AppCompatActivity {

    RecyclerView myMessageFriendsRcycler;
    MessageFriendsAdapter messageFriendsAdapter;

    ArrayList<String> myFriendsIds;

    FirebaseFirestore db;

    /**
     * This method sets the activity on create by overriding AppCompatActivity's onCreate method.
     *
     * @param savedInstanceState - Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_messages);

        // Set the top icons
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = FirebaseFirestore.getInstance();

        myFriendsIds = new ArrayList<String>();

        setMyFriendsIds();

    }


    /**
    * This method pull the list of Id which belong to message friends of current user, from the database.
    * */
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


    /**
    *   This method sets recyclerview and its adapter.
    *
    * */
    private void setMessageFriendsView() {

        myMessageFriendsRcycler = (RecyclerView) findViewById(R.id.myMessageFriends);
        myMessageFriendsRcycler.setLayoutManager(new LinearLayoutManager(this));

        Log.d("messageSon",myFriendsIds.toString());
        messageFriendsAdapter =  new MessageFriendsAdapter(myFriendsIds);

        myMessageFriendsRcycler.setAdapter(messageFriendsAdapter);

        messageFriendsAdapter.notifyDataSetChanged();

    }

    /**
     * This method is in all pages which creates the top menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
        return true;
    }

    /**
     * This method is in all pages which creates the functionality of the top menu
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_icon:
                Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity( settingsIntent);
                return true;
            case android.R.id.home:
                Intent bookIntent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(bookIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}