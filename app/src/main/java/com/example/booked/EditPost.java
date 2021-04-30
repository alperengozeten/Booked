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

public class EditPost extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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

    //bunlar pekKUllanılmamaış
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == 1 && resultCode == RESULT_OK && data != null ) {
            postImageUri = data.getData();

            Picasso.get().load(postImageUri).into(editPostPhotoImageView);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post2);

        currentPost = Booked.getCurrentPost();
        currentUser = Booked.getCurrentUser();

        changeTitleEditText = (EditText) findViewById(R.id.changeTitleEditText);
        changePriceEditText = (EditText) findViewById(R.id.changePriceEditText);
        changeDescriptionEditText = (EditText) findViewById(R.id.changeDescriptionEditText);

        changeUniversitySpinner = (Spinner) findViewById(R.id.changeUniversitySpinner);
        changeCourseSpinner = (Spinner) findViewById(R.id.changeCourseSpinner);
        changeBookSpinner = (Spinner) findViewById(R.id.changeBookSpinner);

        applyChangesBtn = (Button) findViewById(R.id.applyChangePostBtn);

        ArrayAdapter<CharSequence> universityAdapter = ArrayAdapter.createFromResource(this,R.array.universities, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> courseAdapter = ArrayAdapter.createFromResource(this,R.array.courses, android.R.layout.simple_spinner_item);

        universityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        changeUniversitySpinner.setAdapter(universityAdapter);
        changeCourseSpinner.setAdapter(courseAdapter);

        changeUniversitySpinner.setOnItemSelectedListener( (AdapterView.OnItemSelectedListener) this);
        changeCourseSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        editPostPhotoImageView = (ImageView) findViewById(R.id.editPostPhotoImageView);

        // FILL MUST BE AFTER
        fill();

        storageReference = FirebaseStorage.getInstance().getReference("images");

        db = FirebaseFirestore.getInstance();

        allBooks = new ArrayList<>();
        allBookNames = new ArrayList<>();
        db.collection("booksObj").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if ( task.isSuccessful() ) {
                    for ( DocumentSnapshot document : task.getResult() ) {
                        allBooks.add(document.toObject(Book.class));
                        allBookNames.add(document.toObject(Book.class).getBookName());

                        //Book newBook = new Book(document.getString("title"), document.getString("picture"), document.getString("id"));

                    }
                    Toast.makeText(EditPost.this,String.valueOf(allBookNames.size()) + ", " + String.valueOf(allBooks.size()),Toast.LENGTH_LONG).show();
                    ArrayAdapter<String> bookAdapter = new ArrayAdapter<>(EditPost.this,android.R.layout.simple_spinner_item, allBookNames);
                    bookAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    changeBookSpinner.setAdapter(bookAdapter);

                    changeBookSpinner.setOnItemSelectedListener(EditPost.this);
                    changeBookSpinner.setSelection(((ArrayAdapter) changeBookSpinner.getAdapter()).getPosition(currentPost.getBook().getBookName()));
                }
            }
        });

        editPostPhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

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
                    uploadFile();
                    //changeBookProfileOffers(offers);
                    Intent intent = new Intent( getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    private void fill() {
        changeTitleEditText.setText(currentPost.getTitle());
        changeDescriptionEditText.setText(currentPost.getDescription());
        changePriceEditText.setText(String.valueOf(currentPost.getPrice()));

        changeUniversitySpinner.setSelection( ((ArrayAdapter) changeUniversitySpinner.getAdapter()).getPosition(currentPost.getUniversity()));
        changeCourseSpinner.setSelection(((ArrayAdapter) changeCourseSpinner.getAdapter()).getPosition(currentPost.getCourse()));
        Picasso.get().load(currentPost.getPicture()).fit().into(editPostPhotoImageView);
    }

    private void uploadFile() {
        if ( postImageUri != null ) {
            StorageReference fileReference = storageReference.child("posts_pictures/" + currentPost.getId());

            fileReference.putFile(postImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(EditPost.this, "Upload succesful!", Toast.LENGTH_SHORT).show();
                            // CURRENT POST IS NOT INITIALIZED YET; EDIT THE POST
                            //currentPost.addPicture(fileReference.getDownloadUrl().toString());
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // PRICE I PARSE LA
                                    currentPost = new Post(changeTitleEditText.getText().toString().trim(), changeDescriptionEditText.getText().toString().trim(), selectedUniversity,selectedCourse,
                                            Integer.parseInt(changePriceEditText.getText().toString().trim()), uri.toString(), selectedBook, currentUser, currentPost.getId(), currentPost.getReports());
                                    Toast.makeText(EditPost.this,"Post created", Toast.LENGTH_SHORT).show();

                                    db.collection("postsObj").document(currentPost.getId()).set(currentPost).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(EditPost.this,"Information uploaded to database!", Toast.LENGTH_LONG).show();
                                        }
                                    });


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
            currentPost = new Post(changeTitleEditText.getText().toString().trim(), changeDescriptionEditText.getText().toString().trim(), selectedUniversity,selectedCourse,
                    Integer.parseInt(changePriceEditText.getText().toString().trim()), currentPost.getPicture(), selectedBook, currentUser, currentPost.getId(), currentPost.getReports());
            //Toast.makeText(EditPost2.this,"Post created", Toast.LENGTH_SHORT).show();

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

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    private void changeBookProfileOffers(ArrayList<Post> offers )
    {
        db.collection("bookProfileObj").document(currentPost.getBook().getId()).update("offers", offers).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(EditPost.this, "Book Profile updated", Toast.LENGTH_SHORT).show();
            }
        });

    }


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