package com.example.booked;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.booked.Adapter.MessageFriendsAdapter;
import com.example.booked.Adapter.OfferRecyclerApapter;
import com.example.booked.models.User;

import java.util.ArrayList;

public class MyMessages extends AppCompatActivity {

    RecyclerView myMessageFriends;
    MessageFriendsAdapter messageFriendsAdapter;
    ArrayList<User> myFriends;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_messages);

        //setMessageFriendsView();









    }

        private void setMessageFriendsView() {

        myMessageFriends = (RecyclerView) findViewById(R.id.myMessageFriends);

        //myMessageFriends = new MessageFriendsAdapter(myFriends);



    }


}