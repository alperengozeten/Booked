package com.example.booked;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.HashMap;

public class MyProfile extends AppCompatActivity {

    private Button editProfileBtn;
    private Button changePasswordBtn;
    private Button postsBtn;
    private Button transientBtn;
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
    private Bitmap image;

    private FirebaseAuth mAuth;

    private FirebaseFirestore db;

    private StorageReference storageReference;

    private User currentUser;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( resultCode == Activity.RESULT_OK && data != null ) {
            imageUri = data.getData();

            //BURADA URI'ı değiştirip database haline getirip user'a koy

            //profilePhotoImageView.setImageURI(uri);

            // UPLOAD IMAGE
            uploadFile();

            try {
                ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(imageUri,"r");
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                parcelFileDescriptor.close();
                profilePhotoImageView.setImageBitmap(image);
            } catch (IOException e) {
                Log.e("Image","Image not found",e);
            }
        }
    }

    private void uploadFile() {
        if ( imageUri != null ) {
            StorageReference imageReference = storageReference.child("profile_photos/" + mAuth.getCurrentUser().getUid());

            imageReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(MyProfile.this,"Upload succesful!",Toast.LENGTH_SHORT).show();
                            imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // BURADA FIRESTORE'DA GÜNCELLE
                                    currentUser.setAvatar(uri.toString());

                                    HashMap<String,Object> newData = new HashMap<>();
                                    newData.put("username", currentUser.getName());
                                    newData.put("email", currentUser.getEmail());
                                    newData.put("avatar", currentUser.getAvatar());
                                    newData.put("socialmedia", currentUser.getSocialMedia());
                                    newData.put("phonenumber", currentUser.getPhoneNumber());
                                    newData.put("university", currentUser.getUniversity());
                                    newData.put("notifications", currentUser.isNotifications());
                                    newData.put("isbanned", currentUser.isBanned());
                                    newData.put("wishlist", currentUser.getWishlist());
                                    db.collection("users").document(mAuth.getCurrentUser().getUid()).set(newData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(MyProfile.this,"Information uploaded to database!", Toast.LENGTH_LONG).show();
                                        }
                                    });


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        // GET THIS DATA FROM THE DATABASE
        if ( Booked.getCurrentUser() != null ) {
            currentUser = Booked.getCurrentUser();
            //Toast.makeText(this,"uri:" + currentUser.getAvatar(),Toast.LENGTH_LONG).show();
        }
        else {
            currentUser = new User("Alperen", "alperengozeten@gmail.com", "", "05392472224", "Bilkent University" );
            Booked.setCurrentUser(currentUser);
            //Toast.makeText(this,"uri:" + currentUser.getAvatar(),Toast.LENGTH_LONG).show();
        }

        transientBtn = (Button) findViewById(R.id.transientBtn);
        transientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),OtherUsersProfile.class);
                startActivity(intent);
            }
        });

        goWishlistBtn = (Button) findViewById(R.id.goWishlistBtn);
        goWishlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WishlistActivity.class);
                startActivity(intent);
            }
        });

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editProfileBtn = (Button) findViewById(R.id.editProfileBtn);
        changePasswordBtn = (Button) findViewById(R.id.changePasswordBtn);
        postsBtn = (Button) findViewById(R.id.postsBtn);

        facebookBtn = (ImageButton) findViewById(R.id.otherUsersProfileFacebookBtn);
        twitterBtn = (ImageButton) findViewById(R.id.otherUsersProfileTwitterBtn);
        instagramBtn = (ImageButton) findViewById(R.id.otherUsersProfileInstagramBtn);
        mailBtn = (ImageButton) findViewById(R.id.otherUsersProfileMailBtn);
        phoneBtn = (ImageButton) findViewById(R.id.otherUsersProfilePhoneBtn);
        imageEditBtn = (ImageButton) findViewById(R.id.imageEditBtn);

        profileUniversityNameTextView = (TextView) findViewById(R.id.otherUsersProfileUniversityNameTextView);
        profileUsernameTextView = (TextView) findViewById(R.id.otherUsersProfileUsernameTextView);

        profilePhotoImageView = (ImageView) findViewById(R.id.otherUsersProfilePhotoImageView);

        if ( currentUser.getAvatar() != "" ) {
            //profilePhotoImageView.setImageURI(Uri.parse(currentUser.getAvatar()));
            Picasso.get().load(currentUser.getAvatar()).into(profilePhotoImageView);
        }
        else {
            Picasso.get().load(R.drawable.ic_user_male).error(R.drawable.ic_user_male).resize(223,244).centerCrop().into(profilePhotoImageView);
        }

        profileUsernameTextView.setText(currentUser.getName().toString());
        profileUniversityNameTextView.setText(currentUser.getUniversity().toString());


        storageReference = FirebaseStorage.getInstance().getReference("images");

        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();


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
                Intent intent = new Intent(getApplicationContext(), ChangePassword2.class);
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
                if ( currentUser.getSocialMedia().size() < 3 ) {
                    openDialog("No Info");
                } else {
                    openDialog(currentUser.getSocialMedia().get(0));
                }
            }
        });

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

        mailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(currentUser.getEmail());
            }
        });

        phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(currentUser.getPhoneNumber().toString());
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