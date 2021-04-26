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

public class MainActivity extends AppCompatActivity {

    ImageButton showroomImageBtn;
    ImageButton myPostsImageBtn;
    ImageButton profileImageBtn;

    Button showroomBtn;
    Button myPostsBtn;
    Button profileBtn;
    Button mLogOutBtn;

    FirebaseAuth mAuth;

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
                Intent settingsIntent = new Intent(getApplicationContext(), Settings2.class);
                startActivity( settingsIntent);
                return true;
            case android.R.id.home:
                Intent bookIntent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(bookIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // denemek için, sonra silenecek
        Booked.setExample();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showroomImageBtn = (ImageButton) findViewById(R.id.showroomImageBtn);
        myPostsImageBtn = (ImageButton) findViewById(R.id.myPostsImageBtn);
        profileImageBtn = (ImageButton) findViewById(R.id.profileImageBtn);

        showroomBtn = (Button) findViewById(R.id.showroomBtn);
        myPostsBtn = (Button) findViewById(R.id.myPostsBtn);
        profileBtn = (Button) findViewById(R.id.profileBtn);
        mLogOutBtn = (Button) findViewById( R.id.logOutButton1);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
//            if ( mAuth.getCurrentUser().isEmailVerified() ) {
//                Toast.makeText(MainActivity.this, "Logged in Successfully", Toast.LENGTH_LONG).show();
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                finish();
//            }
//            else {
            if (!(mAuth.getCurrentUser().isEmailVerified() )) {
                mAuth.signOut();
                Toast.makeText(MainActivity.this, "Please log in again!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(com.example.booked.MainActivity.this, LoginActivity.class));
                finish();
            }
        }

        showroomImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), ShowroomActivity.class);
                startActivity(intent);
            }
        });

        showroomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), ShowroomActivity.class);
                startActivity(intent);
            }
        });

        myPostsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), MyPosts.class);
                startActivity(intent);
            }
        });

        myPostsImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), MyPosts.class);
                startActivity(intent);
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), MyProfile.class);
                startActivity(intent);
            }
        });

        profileImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), MyProfile.class);
                startActivity(intent);
            }
        });

        mLogOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();

                startActivity( new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
    }
}