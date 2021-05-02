package com.example.booked.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booked.Booked;
import com.example.booked.R;
import com.example.booked.models.User;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class MessageFriendsAdapter extends RecyclerView.Adapter<MessageFriendsAdapter.MessageFriendsHolder> {

    ArrayList<User> myMessageFriends;

    public MessageFriendsAdapter(ArrayList<User> myMessageFriends) {
        this.myMessageFriends = myMessageFriends;
    }

    public class MessageFriendsHolder extends RecyclerView.ViewHolder
    {
        TextView friendName;
        ImageButton goMessages;

        public MessageFriendsHolder(@NonNull View itemView) {
            super(itemView);
            goMessages = itemView.findViewById(R.id.goMessageRoom);
            friendName = itemView.findViewById(R.id.messageFriendName);
        }
    }

    @NonNull
    @Override
    public MessageFriendsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messagefriend_single,parent,false);
        return new MessageFriendsHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MessageFriendsHolder holder, int position) {
        holder.friendName.setText(myMessageFriends.get(position).getName());

        holder.goMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Booked.setCurrentSeller(myMessageFriends.get(position));

                Intent intent = new Intent(holder.goMessages.getContext(), com.example.booked.MessageRoom.class);
                startActivity(holder.goMessages.getContext(),intent,null);

            }
        });

    }



    @Override
    public int getItemCount() {
        return myMessageFriends.size();
    }




}
