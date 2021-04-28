package com.example.booked;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

public class AddPost2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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

    private Post currentPost;

    private User currentUser;

    private FirebaseFirestore db;

    private boolean photoPicked;

    private Book selectedBook;

    private ArrayList<Book> allBooks;
    private ArrayList<String> allBookNames;

    private String selectedBookName;
    private String key;

    private Uri postImageUri;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post2);

        storageReference = FirebaseStorage.getInstance().getReference("images");

        db = FirebaseFirestore.getInstance();

        photoPicked = false;

        currentUser = Booked.getCurrentUser();
        Toast.makeText(this, "username:" + currentUser.getName(), Toast.LENGTH_LONG).show();

        allBooks = new ArrayList<>();
        allBookNames = new ArrayList<>();
        db.collection("books").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if ( task.isSuccessful() ) {
                    for ( DocumentSnapshot document : task.getResult() ) {
                        allBookNames.add(document.getString("title"));

                        Book newBook = new Book(document.getString("title"), document.getString("picture"), document.getString("id"));
                        allBooks.add(newBook);
                    }
                    Toast.makeText(AddPost2.this,String.valueOf(allBookNames.size()) + ", " + String.valueOf(allBooks.size()),Toast.LENGTH_LONG).show();
                    ArrayAdapter<String> bookAdapter = new ArrayAdapter<>(AddPost2.this,android.R.layout.simple_spinner_item, allBookNames);
                    bookAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    bookNameSpinner.setAdapter(bookAdapter);

                    bookNameSpinner.setOnItemSelectedListener(AddPost2.this);
                }
            }
        });

        enterTitleEditText = (EditText) findViewById(R.id.enterTitleEditText);
        setPriceEditText = (EditText) findViewById(R.id.enterPriceEditText);
        addDescriptionEditText = (EditText) findViewById(R.id.addDescriptionEditText);

        selectUniversitySpinner = (Spinner) findViewById(R.id.selectUniversitySpinner);
        selectCourseSpinner = (Spinner) findViewById(R.id.selectCourseSpinner);
        bookNameSpinner = (Spinner) findViewById(R.id.bookNameSpinner);

        submitPostBtn = (Button) findViewById(R.id.submitPostBtn);

        addPostPhotoImageView = (ImageView) findViewById(R.id.addPostPhotoImageView);

        ArrayAdapter<CharSequence> universityAdapter = ArrayAdapter.createFromResource(this,R.array.universities, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> courseAdapter = ArrayAdapter.createFromResource(this,R.array.courses, android.R.layout.simple_spinner_item);

        universityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        selectUniversitySpinner.setAdapter(universityAdapter);
        selectCourseSpinner.setAdapter(courseAdapter);

        selectUniversitySpinner.setOnItemSelectedListener( (AdapterView.OnItemSelectedListener) this);
        selectCourseSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);


        addPostPhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        submitPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( !photoPicked ) {
                    Toast.makeText(AddPost2.this, "Please pick a photo", Toast.LENGTH_SHORT).show();
                }
                else if ( enterTitleEditText.getText() == null || enterTitleEditText.getText().toString().equals("") ) {
                     Toast.makeText(AddPost2.this, "Please enter title", Toast.LENGTH_SHORT).show();
                }
                else if ( setPriceEditText.getText() == null || setPriceEditText.getText().toString().equals("") ) {
                     Toast.makeText(AddPost2.this, "Please enter a price", Toast.LENGTH_SHORT).show();
                }
                else if ( addDescriptionEditText.getText() == null || addDescriptionEditText.getText().toString().equals("") ) {
                     Toast.makeText(AddPost2.this, "Please enter a description", Toast.LENGTH_SHORT).show();
                }
                else {
                     uploadFile();
                     Intent intent = new Intent( getApplicationContext(), MainActivity.class);
                     startActivity(intent);
                }
            }
        });

    }

    private void uploadFile() {
        if ( postImageUri != null ) {
            key = randomKeyGenerator(11);
            StorageReference fileReference = storageReference.child("posts_pictures/" + key);

            fileReference.putFile(postImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(AddPost2.this, "Upload succesful!", Toast.LENGTH_SHORT).show();
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // PRICE I PARSE LA
                                    Post newPost = new Post(enterTitleEditText.getText().toString().trim(), addDescriptionEditText.getText().toString().trim(), selectedUniversity,selectedCourse,
                                            Integer.parseInt(setPriceEditText.getText().toString().trim()), uri.toString(), selectedBook, currentUser, key);
                                    Toast.makeText(AddPost2.this,"Post created", Toast.LENGTH_SHORT).show();
                                }
                            });

                            // CURRENT POST IS NOT INITIALIZED YET; MAKE NEW POST
                            //currentPost.addPicture(fileReference.getDownloadUrl().toString());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddPost2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this,"No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == 1 && resultCode == RESULT_OK && data != null ) {
            postImageUri = data.getData();

            photoPicked = true;

            Picasso.get().load(postImageUri).into(addPostPhotoImageView);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if ( parent.getId() == selectUniversitySpinner.getId() ) {
            selectedUniversity = selectUniversitySpinner.getItemAtPosition(position).toString();
        }
        else if ( parent.getId() == selectCourseSpinner.getId() ) {
            selectedUniversity = selectCourseSpinner.getItemAtPosition(position).toString();
        }
        else if ( parent.getId() == bookNameSpinner.getId() ) {
            selectedBookName = bookNameSpinner.getItemAtPosition(position).toString();
            selectedBook = allBooks.get(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    // function to generate a random string of length n
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