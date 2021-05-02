package com.example.booked;

import androidx.annotation.NonNull;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageRoomActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_room);

        db = FirebaseFirestore.getInstance();

        currentUser = Booked.getCurrentUser();
        currentSeller = Booked.getCurrentSeller();

        messageRecyclerView = (RecyclerView) findViewById(R.id.messageRecyclerView);
        messageRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        messageRecyclerView.setLayoutManager(linearLayoutManager);

        getMessages();

        setImageViewTextView();

        messageSendBtn = (ImageButton) findViewById(R.id.messageSendBtn);
        textSendEditText = (EditText) findViewById(R.id.textSendEditText);

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

    private void getMessages() {
        db.collection("messageRooms").document(Booked.findTheirMessageRoom(currentSeller.getDocumentId(), currentUser.getDocumentId())).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if ( task.isSuccessful() ) {
                    messageRoom = task.getResult().toObject(MessageRoom.class);
                    Toast.makeText(MessageRoomActivity.this, "Message room pulled", Toast.LENGTH_SHORT).show();

                    messageAdapter = new MessageAdapter(MessageRoomActivity.this,messageRoom.getMessages());
                    messageRecyclerView.setAdapter(messageAdapter);
                }
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