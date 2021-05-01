package com.example.booked;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.booked.models.User;
import com.google.firebase.auth.FirebaseAuth;

public class Settings extends AppCompatActivity {

     ImageButton logOutImageBtn;
     ImageButton feedbackImageBtn;
     ImageButton changePasswordImageBtn;
     ImageButton notificationImageBtn;


     Button logOutBtn;
     Button feedbackBtn;
     Button changePasswordBtn;
     Button notificationBtn;

     FirebaseAuth mAuth;

    private User currentUser;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_icon:
                Intent settingsIntent = new Intent(getApplicationContext(), Settings.class);
                startActivity( settingsIntent);
                return true;
            case android.R.id.home:
                Intent bookIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(bookIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentUser = Booked.getCurrentUser();

        logOutBtn = (Button) findViewById(R.id.logOutBtn);
        feedbackBtn = (Button) findViewById(R.id.feedbackBtn);
        changePasswordBtn = (Button) findViewById(R.id.changePasswordBtn);
        notificationBtn = (Button) findViewById(R.id.notificationBtn);

        mAuth = FirebaseAuth.getInstance();



        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity( new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        feedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFeedbackDialog();

            }
        });

        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangePassword.class);
                startActivity(intent);
            }
        });

        notificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //user setle databasee

            }
        });
    }
    public void openFeedbackDialog(){
        FeedbackDialog feedbackDialog = new FeedbackDialog(Settings.this, currentUser.getName());
        feedbackDialog.show(getSupportFragmentManager(),"feedback dialog");

    }
}