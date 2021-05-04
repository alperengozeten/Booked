package com.example.booked;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * A class for the Successful Password Change Page
 *
 * @author NoException
 * @version 2021 Spring
 */
public class SuccessfulPasswordChange extends AppCompatActivity  {

    // Properties
    Button goToMainPageBtn;

    /**
     * This method sets the activity on create by overriding AppCompatActivity's onCreate method.
     *
     * @param savedInstanceState - Bundle
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succesful_change_password);

        goToMainPageBtn = (Button) findViewById( R.id.goToMainPageBtn);

        // Sets ClickListener object for Go to Main Page button.
        goToMainPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // it directs user to Main Page.
                startActivity( new Intent( getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }
}
