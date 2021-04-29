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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

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

    private FirebaseFirestore db;

    private ArrayList<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_users_profile);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = FirebaseFirestore.getInstance();

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

        Book book = new Book("Introduction to Python", "No pic");

        //otherUsersProfilePhotoImageView.setImageURI(Uri.parse(currentSeller.getAvatar()));

        posts = new ArrayList<>();
        db.collection("postsObj").whereEqualTo("seller.documentId", currentSeller.getDocumentId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if ( task.isSuccessful() ) {
                    for ( DocumentSnapshot document : task.getResult()) {

                        posts.add(document.toObject(Post.class));
                    }
                    Toast.makeText(OtherUsersProfile.this, String.valueOf(posts.size()), Toast.LENGTH_SHORT).show();
                    mAdapter = new OtherUsersPostAdapter(posts, OtherUsersProfile.this);
                    recyclerView.setAdapter(mAdapter);
                }
            }
        });


        if ( !currentSeller.getAvatar().equals("") ) {
            Picasso.get().load(currentSeller.getAvatar()).fit().into(otherUsersProfilePhotoImageView);
        }
        otherUsersProfileUsernameTextView.setText(currentSeller.getName().toString());
        otherUsersProfileUniversityNameTextView.setText(currentSeller.getUniversity().toString());
        otherUsersProfilePostTextView.setText(currentSeller.getName().toString() + "'s Posts");

        otherUsersProfileFacebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( currentSeller.getSocialMedia().size() < 3 ) {
                    openDialog("No Info");
                } else {
                    openDialog(currentSeller.getSocialMedia().get(0));
                }
            }
        });

        otherUsersProfileTwitterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( currentSeller.getSocialMedia().size() < 3 ) {
                    openDialog("No Info");
                } else {
                    openDialog(currentSeller.getSocialMedia().get(1));
                }
            }
        });

        otherUsersProfileInstagramBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( currentSeller.getSocialMedia().size() < 3 ) {
                    openDialog("No Info");
                } else {
                    openDialog(currentSeller.getSocialMedia().get(2));
                }
            }
        });

        otherUsersProfileMailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(currentSeller.getEmail());
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