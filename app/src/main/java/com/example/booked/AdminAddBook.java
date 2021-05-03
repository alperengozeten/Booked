package com.example.booked;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.booked.models.Book;
import com.example.booked.models.BookProfile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

/**
 * This is the class of the AdminAddBook page
 * This class is used when the admin wants to add a book so that users can choose this book when
 * they are creating a new post
 *
 * @author NoExpection
 * @version 2021 Spring
 */
public class AdminAddBook extends AppCompatActivity {

    private EditText newBookTitleEditText;
    private ImageView newBookImageView;
    private Button addNewBookBtn;

    private boolean isPhotoPicked;

    private FirebaseFirestore db;
    private StorageReference storageReference;

    private Uri newBookImageUri;
    private String title;

    /**
     * This is the first method called when an instance of this class is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_book);

        // Set the boolean photoPicked to false
        isPhotoPicked = false;

        // Initialize the edit texts and buttons
        newBookTitleEditText = (EditText) findViewById(R.id.newBookTitleEditText);
        newBookImageView = (ImageView) findViewById(R.id.newBookImageView);
        addNewBookBtn = (Button) findViewById(R.id.addNewBookBtn);

        // Initialize the database objects
        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("images");

        // newBookImage view opens a file chooser when clicked
        newBookImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        // add button adds the changes to the database
        addNewBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the title
                title = newBookTitleEditText.getText().toString();

                // Check if the title is not null and photo is picked
                if ( isPhotoPicked && title != null ) {
                    // Call uploadFile() method
                    uploadFile();
                    Intent intent = new Intent( getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(AdminAddBook.this, "Please enter title and an image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Uploads the changes to the firestore and storage databases
     */
    private void uploadFile() {
        if ( newBookImageUri != null ) {

            // Create a new book with the title
            Book newBook = new Book( title);

            StorageReference fileReference = storageReference.child("book_pictures/" + newBook.getId());

            // Upload the image to the storage
            fileReference.putFile(newBookImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(AdminAddBook.this, "Upload succesful!", Toast.LENGTH_SHORT).show();
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    newBook.setPicture(uri.toString());


                                    // Save the book to the firestore database
                                    db.collection("booksObj").document(newBook.getId()).set(newBook).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(AdminAddBook.this,"The Book uploaded to database!", Toast.LENGTH_LONG).show();
                                        }
                                    });

                                    // Create new book profile with the given book
                                    BookProfile bookProfile = new BookProfile(newBook);

                                    // Call the updateBookProfile() method of the Booked class which updates the database
                                    if (Booked.updateBookProfileInDatabase(newBook.getId(), bookProfile))
                                        Toast.makeText(AdminAddBook.this,"The BookProfile uploaded to database!", Toast.LENGTH_LONG).show();

                                    /**db.collection("bookProfileObj").document(newBook.getId()).set(bookProfile).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(AdminAddBook.this,"The BookProfile uploaded to database!", Toast.LENGTH_LONG).show();
                                        }
                                    });*/

                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdminAddBook.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this,"No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method is called to open file chooser when the imageView is clicked to add a photo
     */
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    /**
     * This method is called when the application directs to another application. In our case it's document picker
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == 1 && resultCode == RESULT_OK && data != null ) {

            // Set the imageUri and the isPhotoPicked booleans
            newBookImageUri = data.getData();
            isPhotoPicked = true;

            // Load the image
            Picasso.get().load(newBookImageUri).into(newBookImageView);
        }
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