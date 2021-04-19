package com.example.booked;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.app.Activity;
import android.content.ContentProviderClient;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MyProfile extends AppCompatActivity {

    private Button editProfileBtn;
    private Button changePasswordBtn;
    private Button postsBtn;

    private ImageButton facebookBtn;
    private ImageButton twitterBtn;
    private ImageButton instagramBtn;
    private ImageButton mailBtn;
    private ImageButton phoneBtn;
    private ImageButton imageEditBtn;

    private ImageView profilePhotoImageView;

    private Bitmap image;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( resultCode == Activity.RESULT_OK && data != null ) {
            Uri uri = data.getData();

            try {
                ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri,"r");
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                parcelFileDescriptor.close();
                profilePhotoImageView.setImageBitmap(image);
            } catch (IOException e) {
                Log.e("Image","Image not found",e);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editProfileBtn = (Button) findViewById(R.id.editProfileBtn);
        changePasswordBtn = (Button) findViewById(R.id.changePasswordBtn);
        postsBtn = (Button) findViewById(R.id.postsBtn);

        facebookBtn = (ImageButton) findViewById(R.id.facebookBtn);
        twitterBtn = (ImageButton) findViewById(R.id.twitterBtn);
        instagramBtn = (ImageButton) findViewById(R.id.instagramBtn);
        mailBtn = (ImageButton) findViewById(R.id.mailBtn);
        phoneBtn = (ImageButton) findViewById(R.id.phoneBtn);
        imageEditBtn = (ImageButton) findViewById(R.id.imageEditBtn);

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

        imageEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                startActivityForResult(intent,1);
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
            case android.R.id.home:
                Intent bookIntent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(bookIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}