package com.example.booked;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProviderClient;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MyProfile extends AppCompatActivity {

    Button editProfileBtn;
    Button changePasswordBtn;
    Button postsBtn;

    ImageButton facebookBtn;
    ImageButton twitterBtn;
    ImageButton instagramBtn;
    ImageButton mailBtn;
    ImageButton phoneBtn;

    ImageView profilePhotoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        editProfileBtn = (Button) findViewById(R.id.editProfileBtn);
        changePasswordBtn = (Button) findViewById(R.id.changePasswordBtn);
        postsBtn = (Button) findViewById(R.id.postsBtn);

        facebookBtn = (ImageButton) findViewById(R.id.facebookBtn);
        twitterBtn = (ImageButton) findViewById(R.id.twitterBtn);
        instagramBtn = (ImageButton) findViewById(R.id.instagramBtn);
        mailBtn = (ImageButton) findViewById(R.id.mailBtn);
        phoneBtn = (ImageButton) findViewById(R.id.phoneBtn);

        profilePhotoImageView = (ImageView) findViewById(R.id.profilePhotoImageView);

        Picasso.get().load(R.drawable.ic_user_male).error(R.drawable.ic_user_male).resize(223,244).centerCrop().into(profilePhotoImageView);

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfile.class);
                startActivity(intent);
            }
        });

        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangePassword.class);
                startActivity(intent);
            }
        });

        postsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPosts.class);
                startActivity(intent);
            }
        });

        facebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        twitterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        instagramBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        mailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    public void openDialog() {
        SocialMediaDialog dialog = new SocialMediaDialog();
        dialog.show(getSupportFragmentManager(),"social media dialog");
    }

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
                Intent settingsIntent = new Intent(getApplicationContext(),Settings.class);
                startActivity( settingsIntent);
                return true;
            case R.id.book_icon:
                Intent bookIntent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(bookIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}