package com.example.booked;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booked.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

/**
 * MyProfile Page class
 */
public class MyProfile extends AppCompatActivity {

    private Button editProfileBtn;
    private Button changePasswordBtn;
    private Button postsBtn;
    private Button goWishlistBtn;

    private ImageButton facebookBtn;
    private ImageButton twitterBtn;
    private ImageButton instagramBtn;
    private ImageButton mailBtn;
    private ImageButton phoneBtn;
    private ImageButton imageEditBtn;

    private TextView profileUsernameTextView;
    private TextView profileUniversityNameTextView;

    private ImageView profilePhotoImageView;

    private Uri imageUri;

    private FirebaseAuth mAuth;

    private FirebaseFirestore db;

    private StorageReference storageReference;

    private User currentUser;

    /**
     * First method that is called when this activity is open
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        // Get the current user from global class Booked
        if ( Booked.getCurrentUser() != null ) {
            currentUser = Booked.getCurrentUser();
        }
        else {
            currentUser = new User("Error", "Error", "", "05392472224", "Hata.Myprofile da" );
            Booked.setCurrentUser(currentUser);
        }

        // Button that goes to Wishlist Page
        goWishlistBtn = (Button) findViewById(R.id.goWishlistBtn);
        goWishlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WishlistActivity.class);
                startActivity(intent);
            }
        });

        // Set the top icons
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize other buttons
        editProfileBtn = (Button) findViewById(R.id.editProfileBtn);
        changePasswordBtn = (Button) findViewById(R.id.changePasswordBtn);
        postsBtn = (Button) findViewById(R.id.postsBtn);

        // Initialize image buttons
        facebookBtn = (ImageButton) findViewById(R.id.otherUsersProfileFacebookBtn);
        twitterBtn = (ImageButton) findViewById(R.id.otherUsersProfileTwitterBtn);
        instagramBtn = (ImageButton) findViewById(R.id.otherUsersProfileInstagramBtn);
        mailBtn = (ImageButton) findViewById(R.id.otherUsersProfileMailBtn);
        phoneBtn = (ImageButton) findViewById(R.id.otherUsersProfilePhoneBtn);
        imageEditBtn = (ImageButton) findViewById(R.id.imageEditBtn);

        // Initialize textviews
        profileUniversityNameTextView = (TextView) findViewById(R.id.otherUsersProfileUniversityNameTextView);
        profileUsernameTextView = (TextView) findViewById(R.id.otherUsersProfileUsernameTextView);

        // Initialize imageview
        profilePhotoImageView = (ImageView) findViewById(R.id.usersProfilePhotoImageView);

        // If the user has an avatar, then load it. Else, load an error photo
        if ( currentUser.getAvatar() != "" ) {
            //profilePhotoImageView.setImageURI(Uri.parse(currentUser.getAvatar()));
            Picasso.get().load(currentUser.getAvatar()).fit().into(profilePhotoImageView);
        }
        else {
            Picasso.get().load(R.drawable.ic_user_male).error(R.drawable.ic_user_male).fit().into(profilePhotoImageView);
        }

        // Set the texts for the university and username
        profileUsernameTextView.setText(currentUser.getName().toString());
        profileUniversityNameTextView.setText(currentUser.getUniversity().toString());

        // These three are for the database connections
        storageReference = FirebaseStorage.getInstance().getReference("images");

        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();

        // editProfileBtn directs the user to the Edit Profile page
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfile.class);
                startActivity(intent);
            }
        });

        // changePasswordBtn directs the user to the ChangePassword page
        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangePassword.class);
                startActivity(intent);
            }
        });

        // postsBtn directs the user to the My Posts page
        postsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPosts.class);
                startActivity(intent);
            }
        });

        // facebookBtn opens up the dialog through openDialog() method
        facebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( currentUser.getSocialMedia().size() < 3 ) {
                    openDialog("No Info");
                } else {
                    openDialog(currentUser.getSocialMedia().get(0));
                }
            }
        });

        // twitterBtn opens up the dialog through openDialog() method
        twitterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( currentUser.getSocialMedia().size() < 3 ) {
                    openDialog("No Info");
                } else {
                    openDialog(currentUser.getSocialMedia().get(1));
                }
            }
        });

        // instagramBtn opens up the dialog through openDialog() method
        instagramBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( currentUser.getSocialMedia().size() < 3 ) {
                    openDialog("No Info");
                } else {
                    openDialog(currentUser.getSocialMedia().get(2));
                }
            }
        });

        // mailBtn opens up the dialog through openDialog() method
        mailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(currentUser.getEmail());
            }
        });

        // phoneBtn opens up the dialog through openDialog() method
        phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(currentUser.getPhoneNumber().toString());
            }
        });

        // imageEditBtn opens up the document picker
        imageEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });
    }

    /**
     * This method is called when the document picker is called.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Take the imageUri from the data
        if ( resultCode == Activity.RESULT_OK && data != null ) {
            imageUri = data.getData();

            // Upload image
            uploadFile();

            // Load the image into imageview
            Picasso.get().load(imageUri).fit().into(profilePhotoImageView);
        }
    }

    /**
     * This method is to upload the image file into firebase storage and update the user data in firebase firestore
     */
    private void uploadFile() {
        if ( imageUri != null ) {
            StorageReference imageReference = storageReference.child("profile_photos/" + mAuth.getCurrentUser().getUid());
            // Put the image file into firebase storage
            imageReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(MyProfile.this,"Upload succesful!",Toast.LENGTH_SHORT).show();
                            imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Set the url for the user
                                    currentUser.setAvatar(uri.toString());

                                    // Update the firestore database
                                    if (Booked.updateUserInDatabase(currentUser.getDocumentId(),currentUser))
                                    {
                                        Toast.makeText(MyProfile.this,"User uploaded to database!", Toast.LENGTH_LONG).show();
                                    }

                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MyProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this,"No file selected",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method is to open the social media dialog
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
                Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
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