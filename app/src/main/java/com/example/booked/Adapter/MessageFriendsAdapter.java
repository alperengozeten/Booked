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

/**
 * This is the adapter used in MyMessageFriends page
 */
public class MessageFriendsAdapter extends RecyclerView.Adapter<MessageFriendsAdapter.MessageFriendsHolder> {

    ArrayList<String> myMessageFriends;
    User u;
    ArrayList<User> users;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**Constructor to initialize myMessagefriends
     * */
    public MessageFriendsAdapter(ArrayList<String> myMessageFriends) {
        this.myMessageFriends = myMessageFriends;

        users = new ArrayList<User>();
    }

    /**
     * This is an inner class whose objects holds reference to the gui elements
     */
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

    /**
     * This method creates a MessageFriendsHolder object which holds references to the gui (view) elements
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public MessageFriendsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messagefriend_single,parent,false);
        return new MessageFriendsHolder(view);
    }

    /**
     * This method is called when binding rows (elements)
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MessageFriendsHolder holder, int position) {

        db.collection("usersObj").document(myMessageFriends.get(position)).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        u = task.getResult().toObject(User.class);
                        holder.friendName.setText(u.getName());     //users name
                        users.add(u);

                        holder.goMessages.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                // go to message room of that user
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

    /**
     * This method returns the number of elements(rows)
     * @return
     */
    @Override
    public int getItemCount() {
        return myMessageFriends.size();
    }





}
