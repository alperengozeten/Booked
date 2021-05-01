
package com.example.booked;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

/**
 * A class for the Successful Email Verification Page
 *
 * @author NoExpection
 * @version 2021 Spring
 */
public class SuccessfulVerificationActivity extends AppCompatActivity {

    // Properties
    Button goToMain;
    FirebaseAuth mAuth;

    /**
     * This method sets the activity on create by overriding AppCompatActivity's onCreate method.
     *
     * @param savedInstanceState - Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_verification);

        goToMain = findViewById(R.id.goToMainPageBtn);
        mAuth = FirebaseAuth.getInstance();

        getSupportActionBar().hide();

        // Sets ClickListener object for Go to Main Page button.
        goToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }
}