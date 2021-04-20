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

public class MainActivity extends AppCompatActivity {

    ImageButton showroomImageBtn;
    ImageButton myPostsImageBtn;
    ImageButton profileImageBtn;

    Button showroomBtn;
    Button myPostsBtn;
    Button profileBtn;

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
    }
}