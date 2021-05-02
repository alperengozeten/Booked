package com.example.booked;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booked.Adapter.OtherUsersPostAdapter;
import com.example.booked.models.Book;
import com.example.booked.models.Post;
import com.example.booked.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * This is the class of the OtherUsersProfile page
 */
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

    /**
     * This is the first method called when an instance of this class is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_users_profile);

        // Set the top icons
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize the database instance
        db = FirebaseFirestore.getInstance();

        // Initialize the text views
        otherUsersProfilePostTextView = (TextView) findViewById(R.id.otherUsersProfilePostTextView);
        otherUsersProfileUsernameTextView = (TextView) findViewById(R.id.otherUsersProfileUsernameTextView);
        otherUsersProfileUniversityNameTextView = (TextView) findViewById(R.id.otherUsersProfileUniversityNameTextView);

        // Initialize the image buttons
        otherUsersProfileFacebookBtn = (ImageButton) findViewById(R.id.otherUsersProfileFacebookBtn);
        otherUsersProfileTwitterBtn = (ImageButton) findViewById(R.id.otherUsersProfileTwitterBtn);
        otherUsersProfileInstagramBtn = (ImageButton) findViewById(R.id.otherUsersProfileInstagramBtn);
        otherUsersProfileMailBtn = (ImageButton) findViewById(R.id.otherUsersProfileMailBtn);
        otherUsersProfilePhoneBtn = (ImageButton) findViewById(R.id.otherUsersProfilePhoneBtn);

        // Initialize the imageview for the profile photo
        otherUsersProfilePhotoImageView = (ImageView) findViewById(R.id.otherUsersProfilePhotoImageView);

        // Initialize the recycler view for the posts
        recyclerView = (RecyclerView) findViewById(R.id.otherUserPostList);
        recyclerView.setHasFixedSize(true);

        // Set the layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Pull the current seller from the global class Booked
        currentSeller = Booked.getCurrentSeller();

        // Pull all the posts of this user from the database using whereEqualTo() method and a for-loop
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

        // If there's no avatar in the user's profile, then load a placeholder. Load user's photo, otherwise.
        if ( currentSeller.getAvatar() != "" ) {
            Picasso.get().load(currentSeller.getAvatar()).fit().into(otherUsersProfilePhotoImageView);
        }
        else {
            Picasso.get().load(R.drawable.ic_user_male).error(R.drawable.ic_user_male).fit().into(otherUsersProfilePhotoImageView);
        }

        // Set the textviews with related information about the user
        otherUsersProfileUsernameTextView.setText(currentSeller.getName().toString());
        otherUsersProfileUniversityNameTextView.setText(currentSeller.getUniversity().toString());
        otherUsersProfilePostTextView.setText(currentSeller.getName().toString() + "'s Posts");

        // Open the social media dialog box if the user has social media info
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

        // Open the social media dialog box if the user has social media info
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

        // Open the social media dialog box if the user has social media info
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

        // Open the social media dialog box with the email info
        otherUsersProfileMailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(currentSeller.getEmail());
            }
        });

        // Open the social media dialog box with the phone info
        otherUsersProfilePhoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(currentSeller.getPhoneNumber().toString());
            }
        });
    }

    /**
     * This method creates and opens up SocialMediaDialog
     * @param socialMedia
     */
    public void openDialog(String socialMedia) {
        SocialMediaDialog dialog = new SocialMediaDialog(socialMedia);
        dialog.show(getSupportFragmentManager(),"social media dialog");
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
}