package com.example.booked;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * This is the class of the AddPost page
 */
public class AddPost extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Properties
    private EditText enterTitleEditText;
    private EditText setPriceEditText;
    private EditText addDescriptionEditText;

    private Spinner selectUniversitySpinner;
    private Spinner selectCourseSpinner;
    private Spinner bookNameSpinner;

    private Button submitPostBtn;

    private ImageView addPostPhotoImageView;

    private String selectedUniversity;
    private String selectedCourse;

    private StorageReference storageReference;

    private User currentUser;

    private FirebaseFirestore db;

    private boolean photoPicked;

    private Book selectedBook;

    private ArrayList<Book> allBooks;
    private ArrayList<String> allBookNames;

    private String selectedBookName;
    private String key;

    private Uri postImageUri;

    private Post newPost;

    private static final String CHANNEL_ID = "add_post";

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


    /**
     * This is the first method called when an instance of this class is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post2);

        // Set the top icons
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create notification channel
        createNotificationChannel();

        // Initialize the database objects
        storageReference = FirebaseStorage.getInstance().getReference("images");

        db = FirebaseFirestore.getInstance();

        // Set the photoPicked to false as default
        photoPicked = false;

        // Pull the user from the Booked global class
        currentUser = Booked.getCurrentUser();

        // Pull two arraylists, allBooks and allBookNames from the database
        allBooks = new ArrayList<>();
        allBookNames = new ArrayList<>();
        db.collection("booksObj").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for(QueryDocumentSnapshot documentSnapshots: queryDocumentSnapshots)
                {
                    // Pull the book and add to arraylists
                    Book aBookFromBase = documentSnapshots.toObject(Book.class);
                    Log.d("name",aBookFromBase.getBookName());
                    allBookNames.add(aBookFromBase.getBookName());
                    allBooks.add(aBookFromBase);
                }

            }
        })
        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if ( task.isSuccessful() ) {

                    // When the process is completed, set the adapter for the bookNameSpinner which displays all the book names
                    ArrayAdapter<CharSequence> bookAdapter = new ArrayAdapter(AddPost.this,android.R.layout.simple_spinner_item, allBookNames);

                    bookAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    bookNameSpinner.setAdapter(bookAdapter);

                    bookNameSpinner.setOnItemSelectedListener(AddPost.this);

                }
            }
        });

        // Initialize all the EditText, Spinner, Button and ImageView objects
        enterTitleEditText = (EditText) findViewById(R.id.enterTitleEditText);
        setPriceEditText = (EditText) findViewById(R.id.enterPriceEditText);
        addDescriptionEditText = (EditText) findViewById(R.id.addDescriptionEditText);

        selectUniversitySpinner = (Spinner) findViewById(R.id.selectUniversitySpinner);
        selectCourseSpinner = (Spinner) findViewById(R.id.selectCourseSpinner);
        bookNameSpinner = (Spinner) findViewById(R.id.bookNameSpinner);

        submitPostBtn = (Button) findViewById(R.id.submitPostBtn);

        addPostPhotoImageView = (ImageView) findViewById(R.id.addPostPhotoImageView);

        // Create the adapters for the university and course options
        ArrayAdapter<CharSequence> universityAdapter = ArrayAdapter.createFromResource(this,R.array.universities, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> courseAdapter = ArrayAdapter.createFromResource(this,R.array.courses, android.R.layout.simple_spinner_item);

        // Set drop down view resource for the two adapters
        universityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapters for the spinners
        selectUniversitySpinner.setAdapter(universityAdapter);
        selectCourseSpinner.setAdapter(courseAdapter);

        // Set listeners (this class in this case since it implements the AdapterView.OnItemSelectedListener interface) for the spinners
        selectUniversitySpinner.setOnItemSelectedListener( (AdapterView.OnItemSelectedListener) this);
        selectCourseSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        // When the ImageView is clicked, open the file chooser
        addPostPhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        // When submit button is clicked upload the changes to database
        submitPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( !photoPicked ) {
                    Toast.makeText(AddPost.this, "Please pick a photo", Toast.LENGTH_SHORT).show();
                }
                else if ( enterTitleEditText.getText() == null || enterTitleEditText.getText().toString().equals("") ) {
                     Toast.makeText(AddPost.this, "Please enter title", Toast.LENGTH_SHORT).show();
                }
                else if ( setPriceEditText.getText() == null || setPriceEditText.getText().toString().equals("") ) {
                     Toast.makeText(AddPost.this, "Please enter a price", Toast.LENGTH_SHORT).show();
                }
                else if ( addDescriptionEditText.getText() == null || addDescriptionEditText.getText().toString().equals("") ) {
                     Toast.makeText(AddPost.this, "Please enter a description", Toast.LENGTH_SHORT).show();
                }
                else {

                     uploadFile();

                     notifyUser();

                     Intent intent = new Intent( getApplicationContext(), MainActivity.class);
                     startActivity(intent);
                }
            }
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channelOne = new NotificationChannel(
                    CHANNEL_ID, "channel 1", NotificationManager.IMPORTANCE_DEFAULT);
            channelOne.setDescription( "Add Post Notification");

            NotificationManager manager = getSystemService( NotificationManager.class);
            manager.createNotificationChannel( channelOne);
        }
    }

    private void notifyUser() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("New Post!")
                .setContentText("Your new post has been created and featured in Showroom.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(this);
        mNotificationManager.notify(1, mBuilder.build());
    }

    /**
     * This method uploads the changes to both storage and firestore databases
     */
    private void uploadFile() {
        if ( postImageUri != null ) {
            key = randomKeyGenerator(11);
            StorageReference fileReference = storageReference.child("posts_pictures/" + key);

            fileReference.putFile(postImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(AddPost.this, "Upload succesful!", Toast.LENGTH_SHORT).show();
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Create a new post
                                    newPost = new Post(enterTitleEditText.getText().toString().trim(), addDescriptionEditText.getText().toString().trim(), selectedUniversity,selectedCourse,
                                            Integer.parseInt(setPriceEditText.getText().toString().trim()), uri.toString(), selectedBook, currentUser, key);
                                    Toast.makeText(AddPost.this,"Post created", Toast.LENGTH_SHORT).show();
                                    Booked.getCurrentBookProfile().addPost(newPost);

                                    // Update the database through Booked global class
                                    Booked.updatePostInDatabase(key, newPost);
                                    addToOffers();


                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddPost.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this,"No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method adds the created post to the related BookProfile
     */
    private void addToOffers()
    {
        if (Booked.updateBookProfileInDatabase(newPost.getBook().getId(), Booked.getCurrentBookProfile()))
            Toast.makeText(AddPost.this,"Post uploaded to book profile offers", Toast.LENGTH_LONG).show();
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

        if ( requestCode == 1 && resultCode == RESULT_OK && data != null ) {
            // Set the related image data
            postImageUri = data.getData();

            photoPicked = true;

            // Load the image
            Picasso.get().load(postImageUri).into(addPostPhotoImageView);
        }
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
        if ( parent.getId() == selectUniversitySpinner.getId() ) {
            selectedUniversity = selectUniversitySpinner.getItemAtPosition(position).toString();
        }
        else if ( parent.getId() == selectCourseSpinner.getId() ) {
            selectedCourse = selectCourseSpinner.getItemAtPosition(position).toString();
        }
        else if ( parent.getId() == bookNameSpinner.getId() ) {
            selectedBookName = bookNameSpinner.getItemAtPosition(position).toString();
            selectedBook = allBooks.get(position);

            db.collection("bookProfileObj").document(selectedBook.getId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Toast.makeText(AddPost.this,"Current profile setlendi", Toast.LENGTH_LONG).show();
                    Booked.setCurrentBookProfile(documentSnapshot.toObject(BookProfile.class));
                    //Booked.getCurrentBookProfile().addPost(newPost);
                }
            });
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * This is a function to generate random key
     */
    private String randomKeyGenerator(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}