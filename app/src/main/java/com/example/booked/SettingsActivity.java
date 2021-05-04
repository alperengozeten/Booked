package com.example.booked;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.booked.models.User;
import com.google.firebase.auth.FirebaseAuth;

/**
 * This is the class of the Settings page
 */
public class SettingsActivity extends AppCompatActivity {


     Button logOutBtn;
     Button feedbackBtn;
     Button changePasswordBtn;
     Button notificationBtn;

     FirebaseAuth mAuth;

    private User currentUser;
    private NotificationManagerCompat notificationManager;

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
                Intent bookIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(bookIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This is the first method called when an instance of this class is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);

        notificationManager = NotificationManagerCompat.from(this);

        // Set the top icons
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Pull the current user from the global class
        currentUser = Booked.getCurrentUser();

        // Initialize the buttons
        logOutBtn = (Button) findViewById(R.id.logOutBtn);
        feedbackBtn = (Button) findViewById(R.id.feedbackBtn);
        changePasswordBtn = (Button) findViewById(R.id.changePasswordBtn);
        notificationBtn = (Button) findViewById(R.id.notificationBtn);

        // Initialize the authorization database object
        mAuth = FirebaseAuth.getInstance();

        // Logout button sign outs and exits the app
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity( new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        // Feedback button opens the feedback dialog
        feedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFeedbackDialog();

            }
        });

        // Change password button opens the change password activity
        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangePassword.class);
                startActivity(intent);
            }
        });

        // Notification button changes the notification situation of the user and updates the database
        notificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNotificationSettings();
            }
        });
    }

    private void openNotificationSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent1 = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent1.putExtra( Settings.EXTRA_APP_PACKAGE, getPackageName());
            startActivity( intent1);
        }
        else {
            Intent intent2 = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent2.setData(Uri.parse("package:" + getPackageName()));
            startActivity( intent2);
        }
    }

    /**
     * This method creates and opens a new FeedbackDialog
     */
    public void openFeedbackDialog(){
        FeedbackDialog feedbackDialog = new FeedbackDialog(SettingsActivity.this, currentUser.getName());
        feedbackDialog.show(getSupportFragmentManager(),"feedback dialog");

    }
}