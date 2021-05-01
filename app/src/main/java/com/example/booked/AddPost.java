package com.example.booked;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
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

public class AddPost extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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

    Post newPost;

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
        db.collection("booksObj").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for(QueryDocumentSnapshot documentSnapshots: queryDocumentSnapshots)
                {
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

                    //Toast.makeText(AddPost2.this,"on complete de",Toast.LENGTH_LONG).show();
                    ArrayAdapter<CharSequence> bookAdapter = new ArrayAdapter(AddPost.this,android.R.layout.simple_spinner_item, allBookNames);

                    bookAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    bookNameSpinner.setAdapter(bookAdapter);

                    bookNameSpinner.setOnItemSelectedListener(AddPost.this);

                }
            }
        });

                /**
                    Toast.makeText(AddPost2.this,String.valueOf(allBookNames.size()) + ", " + String.valueOf(allBooks.size()),Toast.LENGTH_LONG).show();
                    ArrayAdapter<String> bookAdapter = new ArrayAdapter<>(AddPost2.this,android.R.layout.simple_spinner_item, allBookNames);
                    bookAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    bookNameSpinner.setAdapter(bookAdapter);

                    bookNameSpinner.setOnItemSelectedListener(AddPost2.this);
                }
            }
        });*/

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
                            Toast.makeText(AddPost.this, "Upload succesful!", Toast.LENGTH_SHORT).show();
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // PRICE I PARSE LA
                                    newPost = new Post(enterTitleEditText.getText().toString().trim(), addDescriptionEditText.getText().toString().trim(), selectedUniversity,selectedCourse,
                                            Integer.parseInt(setPriceEditText.getText().toString().trim()), uri.toString(), selectedBook, currentUser, key);
                                    Toast.makeText(AddPost.this,"Post created", Toast.LENGTH_SHORT).show();
                                    Booked.getCurrentBookProfile().addPost(newPost);

                                    //burada key i çıkarılabilir daha sonra yukarısıyla senkronize edilerek , şuan dokunmadım
                                    Booked.updatePostInDatabase(key, newPost);
                                    /**db.collection("postsObj").document(key).set(newPost).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(AddPost.this,"Post uploaded to database!", Toast.LENGTH_LONG).show();
                                        }
                                    });*/



                                    addToOffers();


                                }
                            });

                            // CURRENT POST IS NOT INITIALIZED YET; MAKE NEW POST
                            //currentPost.addPicture(fileReference.getDownloadUrl().toString());
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

    private void addToOffers()
    {
        if (Booked.updateBookProfileInDatabase(newPost.getBook().getId(), Booked.getCurrentBookProfile()))
            Toast.makeText(AddPost.this,"Post uploaded to book profile offers", Toast.LENGTH_LONG).show();

        /**db.collection("bookProfileObj").document(newPost.getBook().getId()).set(Booked.getCurrentBookProfile())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });*/
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