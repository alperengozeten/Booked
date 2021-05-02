package com.example.booked.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booked.Booked;
import com.example.booked.MessageRoomActivity;
import com.example.booked.R;
import com.example.booked.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class MessageFriendsAdapter extends RecyclerView.Adapter<MessageFriendsAdapter.MessageFriendsHolder> {

    ArrayList<String> myMessageFriends;
    User u;
    ArrayList<User> users;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MessageFriendsAdapter(ArrayList<String> myMessageFriends) {
        this.myMessageFriends = myMessageFriends;

        users = new ArrayList<User>();

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

        /**holder.friendName.setText(users.get(position).getName());

        holder.goMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Booked.setCurrentSeller(users.get(position));

                Intent intent = new Intent(holder.goMessages.getContext(), MessageRoomActivity.class);
                startActivity(holder.goMessages.getContext(),intent,null);

            }
        });*/


        db.collection("usersObj").document(myMessageFriends.get(position)).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        u = task.getResult().toObject(User.class);
                        holder.friendName.setText(u.getName());
                        users.add(u);

                        holder.goMessages.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Log.d("messageAdapter",users.get(position).getName() + position);

                                for(User u : users) {
                                    if (u.getDocumentId().equals(myMessageFriends.get(position))) {
                                        Booked.setCurrentSeller(u);
                                    }
                                }


                                Intent intent = new Intent(holder.goMessages.getContext(), MessageRoomActivity.class);
                                startActivity(holder.goMessages.getContext(),intent,null);

                            }
                        });



                    }
                });

    }



    @Override
    public int getItemCount() {
        return myMessageFriends.size();
    }





}
