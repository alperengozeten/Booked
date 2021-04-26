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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AdminAddBook extends AppCompatActivity {

    private EditText newBookTitleEditText;
    private ImageView newBookImageView;
    private Button addNewBookBtn;

    private boolean isPhotoPicked;

    private StorageReference storageReference;

    private Uri newBookImageUri;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_book);

        isPhotoPicked = false;
        newBookTitleEditText = (EditText) findViewById(R.id.newBookTitleEditText);
        newBookImageView = (ImageView) findViewById(R.id.newBookImageView);
        addNewBookBtn = (Button) findViewById(R.id.addNewBookBtn);

        storageReference = FirebaseStorage.getInstance().getReference("images");

        newBookImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        addNewBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = newBookTitleEditText.getText().toString();

                if ( isPhotoPicked && title != null ) {
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

    private void uploadFile() {
        if ( newBookImageUri != null ) {
            StorageReference fileReference = storageReference.child("book_pictures/4");

            fileReference.putFile(newBookImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(AdminAddBook.this, "Upload succesful!", Toast.LENGTH_SHORT).show();
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //Book.setPhoto(uri.toString());
                                }
                            });

                            // CURRENT POST IS NOT INITIALIZED YET; MAKE NEW POST
                            //currentPost.addPicture(fileReference.getDownloadUrl().toString());
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

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == 1 && resultCode == RESULT_OK && data != null ) {

            newBookImageUri = data.getData();
            isPhotoPicked = true;
            Picasso.get().load(newBookImageUri).into(newBookImageView);
        }
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