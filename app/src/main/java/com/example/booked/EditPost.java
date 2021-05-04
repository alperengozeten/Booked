package com.example.booked;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.booked.models.Book;
import com.example.booked.models.BookProfile;
import com.example.booked.models.Post;
import com.example.booked.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * This is the class of the EditPost page
 *
 * @author NoException
 * @version 2021 Spring
 */
public class EditPost extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Properties
    private EditText changeTitleEditText;
    private EditText changePriceEditText;
    private EditText changeDescriptionEditText;

    private Spinner changeUniversitySpinner;
    private Spinner changeCourseSpinner;
    private Spinner changeBookSpinner;

    private Button applyChangesBtn;

    private String selectedUniversity;
    private String selectedCourse;

    private StorageReference storageReference;
    private FirebaseFirestore db;

    private ArrayList<Book> allBooks;
    private ArrayList<String> allBookNames;
    private String selectedBookName;

    private Book selectedBook;

    private Post currentPost;

    private Uri postImageUri;

    private User currentUser;

    private ArrayList<Post> offers = new ArrayList<Post>();
    private com.example.booked.models.BookProfile matchingBookProfile;

    private ImageView editPostPhotoImageView;

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

    /**
     * This method sets the activity on create by overriding AppCompatActivity's onCreate method.
     *
     * @param savedInstanceState - Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post2);

        // Set the top icons
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Take the currentPost and currentUser from the global class Booked
        currentPost = Booked.getCurrentPost();
        currentUser = Booked.getCurrentUser();

        setEditTextFields();
        setSpinners();

        // Initialize the buttons
        applyChangesBtn = (Button) findViewById(R.id.applyChangePostBtn);
        editPostPhotoImageView = (ImageView) findViewById(R.id.editPostPhotoImageView);

        // Fill the related textviews, spinners, etc.
        fill();

        // Initialize the database objects
        storageReference = FirebaseStorage.getInstance().getReference("images");

        db = FirebaseFirestore.getInstance();

        // Pull two arraylists, allBooks and allBookNames from the database
        allBooks = new ArrayList<>();
        allBookNames = new ArrayList<>();
        db.collection("booksObj").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if ( task.isSuccessful() ) {
                    for ( DocumentSnapshot document : task.getResult() ) {

                        // Pull the book and add to arraylists
                        allBooks.add(document.toObject(Book.class));
                        allBookNames.add(document.toObject(Book.class).getBookName());
                    }

                    // Set the adapter and listener
                    Toast.makeText(EditPost.this,String.valueOf(allBookNames.size()) + ", " + String.valueOf(allBooks.size()),Toast.LENGTH_LONG).show();
                    ArrayAdapter<String> bookAdapter = new ArrayAdapter<>(EditPost.this,android.R.layout.simple_spinner_item, allBookNames);
                    bookAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    changeBookSpinner.setAdapter(bookAdapter);

                    changeBookSpinner.setOnItemSelectedListener(EditPost.this);
                    changeBookSpinner.setSelection(((ArrayAdapter) changeBookSpinner.getAdapter()).getPosition(currentPost.getBook().getBookName()));
                }
            }
        });

        // When the ImageView is clicked, open the file chooser
        editPostPhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        // When the apply button is clicked, update the information in the database
        applyChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( changeTitleEditText.getText() == null || changeTitleEditText.getText().toString().equals("") ) {
                    Toast.makeText(EditPost.this, "Please enter title", Toast.LENGTH_SHORT).show();
                }
                else if ( changePriceEditText.getText() == null || changePriceEditText.getText().toString().equals("") ) {
                    Toast.makeText(EditPost.this, "Please enter a price", Toast.LENGTH_SHORT).show();
                }
                else if ( changeDescriptionEditText.getText() == null || changeDescriptionEditText.getText().toString().equals("") ) {
                    Toast.makeText(EditPost.this, "Please enter a description", Toast.LENGTH_SHORT).show();
                }
                else {
                    // Update
                    uploadFile();

                    notifyUser();

                    Intent intent = new Intent( getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    /**
     * Sends a notification to user when the user edited a post.
     */
    private void notifyUser() {
        Intent resultIntent = new Intent(this, MyPosts.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 3,
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, Booked.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Post Edited!")
                .setContentText("Post has been edited and featured in Showroom.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent);

        NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(this);
        mNotificationManager.notify(3, mBuilder.build());
    }

    /**
     * Fills the EditTexts
     */
    private void setEditTextFields()
    {
        changeTitleEditText = (EditText) findViewById(R.id.changeTitleEditText);
        changePriceEditText = (EditText) findViewById(R.id.changePriceEditText);
        changeDescriptionEditText = (EditText) findViewById(R.id.changeDescriptionEditText);

    }

    /**
     * Initializes the spinners
     */
    private void setSpinners()
    {
        changeUniversitySpinner = (Spinner) findViewById(R.id.changeUniversitySpinner);
        changeCourseSpinner = (Spinner) findViewById(R.id.changeCourseSpinner);
        changeBookSpinner = (Spinner) findViewById(R.id.changeBookSpinner);


        ArrayAdapter<CharSequence> universityAdapter = ArrayAdapter.createFromResource(this,R.array.universities, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> courseAdapter = ArrayAdapter.createFromResource(this,R.array.courses, android.R.layout.simple_spinner_item);

        universityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        changeUniversitySpinner.setAdapter(universityAdapter);
        changeCourseSpinner.setAdapter(courseAdapter);

        changeUniversitySpinner.setOnItemSelectedListener( (AdapterView.OnItemSelectedListener) this);
        changeCourseSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
    }

    /**
     * Fills the spinners, ImageView, and EditTexts with the related information
     */
    private void fill() {
        changeTitleEditText.setText(currentPost.getTitle());
        changeDescriptionEditText.setText(currentPost.getDescription());
        changePriceEditText.setText(String.valueOf(currentPost.getPrice()));

        changeUniversitySpinner.setSelection( ((ArrayAdapter) changeUniversitySpinner.getAdapter()).getPosition(currentPost.getUniversity()));
        changeCourseSpinner.setSelection(((ArrayAdapter) changeCourseSpinner.getAdapter()).getPosition(currentPost.getCourse()));
        Picasso.get().load(currentPost.getPicture()).fit().into(editPostPhotoImageView);
    }

    /**
     * Uploads information to database
     */
    private void uploadFile() {
        if ( postImageUri != null ) {
            StorageReference fileReference = storageReference.child("posts_pictures/" + currentPost.getId());

            fileReference.putFile(postImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(EditPost.this, "Upload succesful!", Toast.LENGTH_SHORT).show();
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Create a new post
                                    currentPost = new Post(changeTitleEditText.getText().toString().trim(), changeDescriptionEditText.getText().toString().trim(), selectedUniversity,selectedCourse,
                                            Integer.parseInt(changePriceEditText.getText().toString().trim()), uri.toString(), selectedBook, currentUser, currentPost.getId(), currentPost.getReports());
                                    Toast.makeText(EditPost.this,"Post created", Toast.LENGTH_SHORT).show();

                                    // Update the posts
                                    db.collection("postsObj").document(currentPost.getId()).set(currentPost).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(EditPost.this,"Information uploaded to database!", Toast.LENGTH_LONG).show();
                                        }
                                    });

                                    // Update the bookProfiles
                                    db.collection("bookProfileObj").document(currentPost.getBook().getId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            offers = documentSnapshot.toObject(BookProfile.class).getOffers();

                                            offers.remove(currentPost);
                                            offers.add(currentPost);
                                            changeBookProfileOffers(offers);
                                        }
                                    });
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditPost.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            // Make the same if the user didn't pick an image
            currentPost = new Post(changeTitleEditText.getText().toString().trim(), changeDescriptionEditText.getText().toString().trim(), selectedUniversity,selectedCourse,
                    Integer.parseInt(changePriceEditText.getText().toString().trim()), currentPost.getPicture(), selectedBook, currentUser, currentPost.getId(), currentPost.getReports());

            db.collection("postsObj").document(currentPost.getId()).set(currentPost).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(EditPost.this,"Information uploaded to database!", Toast.LENGTH_LONG).show();
                }
            });


            db.collection("bookProfileObj").document(currentPost.getBook().getId()).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    offers = documentSnapshot.toObject(BookProfile.class).getOffers();
                    offers.remove(currentPost);
                    offers.add(currentPost);
                    changeBookProfileOffers(offers);
                }
            });

        }
    }

    /**
     * This method opens the file picker
     */
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    /**
     * This method is called when another application is opened. In our case it's file picker.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == 1 && resultCode == RESULT_OK && data != null ) {
            // Set the related image data
            postImageUri = data.getData();

            // Load the image
            Picasso.get().load(postImageUri).into(editPostPhotoImageView);
        }
    }

    /**
     * This updates the offers (posts) part of the BookProfiles
     * @param offers
     */
    private void changeBookProfileOffers(ArrayList<Post> offers )
    {
        db.collection("bookProfileObj").document(currentPost.getBook().getId()).update("offers", offers).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(EditPost.this, "Book Profile updated", Toast.LENGTH_SHORT).show();
            }
        });

    }


    /**
     * This method checks the origin of the action and holds the data depending on which spinner it is
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if ( parent.getId() == changeUniversitySpinner.getId() ) {
            selectedUniversity = changeUniversitySpinner.getItemAtPosition(position).toString();
        }
        else if ( parent.getId() == changeCourseSpinner.getId() ) {
            selectedCourse = changeCourseSpinner.getItemAtPosition(position).toString();
        }
        else if ( parent.getId() == changeBookSpinner.getId() ) {
            selectedBookName = changeBookSpinner.getItemAtPosition(position).toString();
            selectedBook = allBooks.get(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}