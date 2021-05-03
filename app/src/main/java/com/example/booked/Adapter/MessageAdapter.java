package com.example.booked.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booked.R;
import com.example.booked.models.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * This is an adapter class for the RecyclerView in the MessageRoom activity
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyMessageViewHolder> {

    // Properties
    private Context context;
    private ArrayList<Message> messages;
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private FirebaseUser fUser;

    /**
     * Constructor that takes the list of messages and also a reference to the MessageRoomActivity as a Context object
     * @param context
     * @param messages
     */
    public MessageAdapter(Context context, ArrayList<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    /**
     * This method is called to create an instance of MyMessageViewHolder which holds references to the gui elements
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public MyMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Depending on the view type, choose the right or left message layout
        if ( viewType == MSG_TYPE_RIGHT ) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_right,parent,false);
            return new MyMessageViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_left,parent,false);
            return new MyMessageViewHolder(view);
        }
    }

    /**
     * Set the message text
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MyMessageViewHolder holder, int position) {
        Message message = messages.get(position);

        holder.showMessage.setText(message.getMessage());
    }

    /**
     * Returns number of elements (rows)
     * @return
     */
    @Override
    public int getItemCount() {
        return messages.size();
    }

    /**
     * This is an inner class whose objects holds reference to the gui elements
     */
    public class MyMessageViewHolder extends RecyclerView.ViewHolder{

        public TextView showMessage;

        public MyMessageViewHolder(@NonNull View itemView) {
            super(itemView);

            showMessage = (TextView) itemView.findViewById(R.id.showMessage);
        }
    }

    /**
     * This method helps us to decide the origin of the message
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        if ( messages.get(position).getSenderId().equals(fUser.getUid()) ) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}
