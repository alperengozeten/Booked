package com.example.booked;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.booked.models.User;


public class EditProfile extends AppCompatActivity {

    User currentUser;
    Button confirmButton;
    EditText userName, university, telephone,email, facebook, twitter , instagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentUser = Booked.getCurrentUser();

        setEditTexts();

        setButtons();

    }

    void setButtons() {
        confirmButton = (Button) findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //currentUser.setName(userName.getText().toString());// isim değiştirme eklersek yorumlardaki isimleri de değiştirmek gerekir

                currentUser.setUniversity(university.getText().toString());
                currentUser.setPhoneNumber(telephone.getText().toString());
                currentUser.setEmail(email.getText().toString());
                //social media?

                //post

                //open new page
                Intent profile = new Intent(getApplicationContext(), PostPage.class);
                startActivity(profile);

            }
        });
    }

    void setEditTexts()
    {
        userName = (EditText) findViewById(R.id.username);
        university = (EditText) findViewById(R.id.university);
        telephone = (EditText) findViewById(R.id.telephone);
        email = (EditText) findViewById(R.id.email);
        twitter = (EditText) findViewById(R.id.twitter);
        facebook = (EditText) findViewById(R.id.facebook);
        instagram = (EditText) findViewById(R.id.instagram);

        userName.setText(currentUser.getName().toString());
        university.setText(currentUser.getUniversity().toString());
        telephone.setText(currentUser.getPhoneNumber().toString());
        email.setText(currentUser.getEmail().toString());
        // social media?

    }
}