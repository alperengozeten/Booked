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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class EditPost2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText changeTitleEditText;
    private EditText changePriceEditText;
    private EditText changeDescriptionEditText;

    private Spinner changeUniversitySpinner;
    private Spinner changeCourseSpinner;

    private Button applyChangesBtn;

    private String selectedUniversity;
    private String selectedCourse;

    private StorageReference storageReference;

    private Uri postImageUri;

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

        changeTitleEditText = (EditText) findViewById(R.id.changeTitleEditText);
        changePriceEditText = (EditText) findViewById(R.id.changePriceEditText);
        changeDescriptionEditText = (EditText) findViewById(R.id.changeDescriptionEditText);

        changeUniversitySpinner = (Spinner) findViewById(R.id.changeUniversitySpinner);
        changeCourseSpinner = (Spinner) findViewById(R.id.changeCourseSpinner);

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

        storageReference = FirebaseStorage.getInstance().getReference("images");

        editPostPhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        applyChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyChangesPost();
                uploadFile();
                Intent intent = new Intent( getApplicationContext(), MyPosts.class);
                startActivity(intent);
            }
        });

    }

    private void uploadFile() {
        if ( postImageUri != null ) {
            StorageReference fileReference = storageReference.child("posts_pictures/3");

            fileReference.putFile(postImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(EditPost2.this, "Upload succesful!", Toast.LENGTH_SHORT).show();

                            // CURRENT POST IS NOT INITIALIZED YET; EDIT THE POST
                            //currentPost.addPicture(fileReference.getDownloadUrl().toString());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditPost2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if ( parent.getId() == changeUniversitySpinner.getId() ) {
            selectedUniversity = changeUniversitySpinner.getItemAtPosition(position).toString();
        }
        else if ( parent.getId() == changeCourseSpinner.getId() ) {
            selectedUniversity = changeCourseSpinner.getItemAtPosition(position).toString();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void applyChangesPost(){


    }
}