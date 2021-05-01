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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

/**
 * This is the class of the MainActivity page
 */
public class MainActivity extends AppCompatActivity {

    ImageButton showroomImageBtn;
    ImageButton myPostsImageBtn;
    ImageButton profileImageBtn;

    Button showroomBtn;
    Button myPostsBtn;
    Button profileBtn;

    private Button adminPanel;

    FirebaseAuth mAuth;

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
                Intent settingsIntent = new Intent(getApplicationContext(), Settings.class);
                startActivity( settingsIntent);
                return true;
            case android.R.id.home:
                Intent bookIntent = new Intent(getApplicationContext(),MainActivity.class);
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
        setContentView(R.layout.activity_main);

        // Initialize the admin panel button and set a listener to it
        adminPanel = (Button) findViewById(R.id.adminNewBookBtn);
        adminPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AdminPanel.class);
                startActivity(intent);
            }
        });

        // Set the top icons
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize the buttons
        showroomImageBtn = (ImageButton) findViewById(R.id.showroomImageBtn);
        myPostsImageBtn = (ImageButton) findViewById(R.id.myPostsImageBtn);
        profileImageBtn = (ImageButton) findViewById(R.id.profileImageBtn);

        // Initialize the image buttons
        showroomBtn = (Button) findViewById(R.id.showroomBtn);
        myPostsBtn = (Button) findViewById(R.id.myPostsBtn);
        profileBtn = (Button) findViewById(R.id.profileBtn);

        // Initialize the firebase authentication instance
        mAuth = FirebaseAuth.getInstance();

        // If the email of the user is not verified, then sign the user out
        if (mAuth.getCurrentUser() != null) {
            if (!(mAuth.getCurrentUser().isEmailVerified() )) {
                mAuth.signOut();
                Toast.makeText(MainActivity.this, "Please log in again!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(com.example.booked.MainActivity.this, LoginActivity.class));
                finish();
            }
        }

        // When the showroom button is clicked, it leads to Showroom Page
        showroomImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), ShowroomActivity.class);
                startActivity(intent);
            }
        });

        // When the showroom button is clicked, it leads to Showroom Page
        showroomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), ShowroomActivity.class);
                startActivity(intent);
            }
        });

        // When the my posts button is clicked, it leads to MyPosts page
        myPostsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), MyPosts.class);
                startActivity(intent);
            }
        });

        // When the my posts button is clicked, it leads to MyPosts page
        myPostsImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), MyPosts.class);
                startActivity(intent);
            }
        });

        // When the profile button is clicked, it leads to MyProfile page
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), MyProfile.class);
                startActivity(intent);
            }
        });

        // When the profile button is clicked, it leads to MyProfile page
        profileImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), MyProfile.class);
                startActivity(intent);
            }
        });
    }
}