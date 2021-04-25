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

import com.example.booked.models.Post;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AddPost2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText enterTitleEditText;
    private EditText setPriceEditText;
    private EditText addDescriptionEditText;

    private Spinner selectUniversitySpinner;
    private Spinner selectCourseSpinner;

    private Button submitPostBtn;

    private ImageView addPostPhotoImageView;

    private String selectedUniversity;
    private String selectedCourse;

    private StorageReference storageReference;

    private Post currentPost;

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

        enterTitleEditText = (EditText) findViewById(R.id.enterTitleEditText);
        setPriceEditText = (EditText) findViewById(R.id.enterPriceEditText);
        addDescriptionEditText = (EditText) findViewById(R.id.addDescriptionEditText);

        selectUniversitySpinner = (Spinner) findViewById(R.id.selectUniversitySpinner);
        selectCourseSpinner = (Spinner) findViewById(R.id.selectCourseSpinner);

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

        storageReference = FirebaseStorage.getInstance().getReference("images");

        addPostPhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        submitPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPost();
                uploadFile();
                Intent intent = new Intent( getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void uploadFile() {
        if ( postImageUri != null ) {
            StorageReference fileReference = storageReference.child("posts_pictures/2");

            fileReference.putFile(postImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(AddPost2.this, "Upload succesful!", Toast.LENGTH_SHORT).show();
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Booked.setExamplePath(uri.toString());
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
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void createPost(){



    }
}