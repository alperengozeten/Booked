package com.example.booked;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booked.Adapter.MessageAdapter;
import com.example.booked.models.Message;
import com.example.booked.models.MessageRoom;
import com.example.booked.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * This class is the chatting part of the Messaging utility
 *
 * @author NoException
 * @version 2021 Spring
 */
public class MessageRoomActivity extends AppCompatActivity {

    // Properties
    private FirebaseFirestore db;

    private User currentUser;
    private User currentSeller;

    private TextView contactUserNameTextView;

    private CircleImageView contactImageView;

    private ImageButton messageSendBtn;

    private EditText textSendEditText;

    private MessageRoom messageRoom;

    private MessageAdapter messageAdapter;

    private RecyclerView messageRecyclerView;

    /**
     * This is the first method called when an instance of MessageRoomActivity is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_room);

        // Set the top icons
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize the database object
        db = FirebaseFirestore.getInstance();

        // Take the info of the users from the global class Booked
        currentUser = Booked.getCurrentUser();
        currentSeller = Booked.getCurrentSeller();

        // Initialize the recycler view
        messageRecyclerView = (RecyclerView) findViewById(R.id.messageRecyclerView);
        messageRecyclerView.setHasFixedSize(true);

        // Set the layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        messageRecyclerView.setLayoutManager(linearLayoutManager);

        // Pull the messages for the first time. Later, the messages will be updated by snapshot listener on the
        // database, not by this method
        getMessages();

        // Set the ImageViews and TextViews
        setImageViewTextView();

        // Initialize the EditText and ImageButton
        messageSendBtn = (ImageButton) findViewById(R.id.messageSendBtn);
        textSendEditText = (EditText) findViewById(R.id.textSendEditText);

        // Set a listener for the send button which saves the message to the database
        messageSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( !(textSendEditText.getText() == null || textSendEditText.getText().toString().equals("")) ) {
                    Message newMessage = new Message(currentSeller.getDocumentId(), currentUser.getDocumentId(), textSendEditText.getText().toString());
                    Booked.sendMessage(newMessage);
                    textSendEditText.setText("");
                    Toast.makeText(MessageRoomActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MessageRoomActivity.this, "Type a message", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Initialize the ImageViews and TextViews
     */
    private void setImageViewTextView() {
        contactUserNameTextView = (TextView) findViewById(R.id.contactUserNameTextView);
        contactUserNameTextView.setText(currentSeller.getName());

        contactImageView = (CircleImageView) findViewById(R.id.contactImageView);
        if ( !currentSeller.getAvatar().equals("") ) {
        Picasso.get().load(currentSeller.getAvatar()).fit().into(contactImageView);
        }
        else {
            Picasso.get().load(R.drawable.ic_user_male).fit().into(contactImageView);
        }
    }

    /**
     * Pulls the messages between the two users from the database
     */
    private void getMessages() {
        db.collection("messageRooms").document(Booked.findTheirMessageRoom(currentSeller.getDocumentId(), currentUser.getDocumentId())).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if ( task.isSuccessful() ) {
                    messageRoom = task.getResult().toObject(MessageRoom.class);

                    messageAdapter = new MessageAdapter(MessageRoomActivity.this,messageRoom.getMessages());
                    messageRecyclerView.setAdapter(messageAdapter);
                }
            }
        });

    }

    /**
     * Holds a snapshot listener on the database, so that it updates data of the activity with getMessages() method whenever the
     * node in the database is changed
     */
    @Override
    protected void onStart() {
        super.onStart();

        db.collection("messageRooms").document(Booked.findTheirMessageRoom(currentUser.getDocumentId(),currentSeller.getDocumentId())).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                getMessages();
            }
        });
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