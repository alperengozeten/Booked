package com.example.booked;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booked.models.Book;
import com.example.booked.models.Post;
import com.example.booked.models.User;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class OtherUsersProfile extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private TextView otherUsersProfilePostTextView;
    private TextView otherUsersProfileUsernameTextView;
    private TextView otherUsersProfileUniversityNameTextView;

    private ImageButton otherUsersProfileFacebookBtn;
    private ImageButton otherUsersProfileTwitterBtn;
    private ImageButton otherUsersProfileInstagramBtn;
    private ImageButton otherUsersProfileMailBtn;
    private ImageButton otherUsersProfilePhoneBtn;

    private ImageView otherUsersProfilePhotoImageView;

    private User currentSeller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_users_profile);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        otherUsersProfilePostTextView = (TextView) findViewById(R.id.otherUsersProfilePostTextView);
        otherUsersProfileUsernameTextView = (TextView) findViewById(R.id.otherUsersProfileUsernameTextView);
        otherUsersProfileUniversityNameTextView = (TextView) findViewById(R.id.otherUsersProfileUniversityNameTextView);

        otherUsersProfileFacebookBtn = (ImageButton) findViewById(R.id.otherUsersProfileFacebookBtn);
        otherUsersProfileTwitterBtn = (ImageButton) findViewById(R.id.otherUsersProfileTwitterBtn);
        otherUsersProfileInstagramBtn = (ImageButton) findViewById(R.id.otherUsersProfileInstagramBtn);
        otherUsersProfileMailBtn = (ImageButton) findViewById(R.id.otherUsersProfileMailBtn);
        otherUsersProfilePhoneBtn = (ImageButton) findViewById(R.id.otherUsersProfilePhoneBtn);

        otherUsersProfilePhotoImageView = (ImageView) findViewById(R.id.otherUsersProfilePhotoImageView);

        recyclerView = (RecyclerView) findViewById(R.id.otherUserPostList);

        recyclerView.setHasFixedSize(true);

        // Set the layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // TO BE REPLACED WITH THE DATABASE
        // buraya iki yerden geliniyo 1)visit button in book profile 2) visit sellerProfile in postpage gitmeden: önce current sellerı (global) eşitlersek olur
        currentSeller = Booked.getCurrentSeller();
        Toast.makeText(this,"User photo: " + currentSeller.getAvatar(),Toast.LENGTH_LONG).show();
        Book book = new Book("Introduction to Python", "No pic");

        //otherUsersProfilePhotoImageView.setImageURI(Uri.parse(currentSeller.getAvatar()));

        ArrayList<Post> posts = new ArrayList<>();
        posts.add(new Post("Cs book", "In good state", "Cs-115",70, 1,"No pic", book, currentSeller));
        posts.add(new Post("Math book", "In good state", "Math-101",60,2,"No pic", book, currentSeller));
        posts.add(new Post("Phys book", "In good state", "Phys-101",50,3,"No pic", book, currentSeller));

        mAdapter = new OtherUsersPostAdapter(posts, this);
        recyclerView.setAdapter(mAdapter);

        otherUsersProfileUsernameTextView.setText(currentSeller.getName().toString());
        otherUsersProfileUniversityNameTextView.setText(currentSeller.getUniversity().toString());
        otherUsersProfilePostTextView.setText(currentSeller.getName().toString() + "'s Posts");

        otherUsersProfileFacebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog("No Info");
            }
        });

        otherUsersProfileTwitterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog("No Info");
            }
        });

        otherUsersProfileInstagramBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog("No Info");
            }
        });

        otherUsersProfileMailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog("No Info");
            }
        });

        otherUsersProfilePhoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(currentSeller.getPhoneNumber().toString());
            }
        });
    }

    public void openDialog(String socialMedia) {
        SocialMediaDialog dialog = new SocialMediaDialog(socialMedia);
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
}