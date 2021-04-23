
package com.example.booked;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class SuccessfulVerificationActivity extends AppCompatActivity {

    Button goToMain;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_verification);

        goToMain = findViewById(R.id.goToMainPageBtn);

        mAuth = FirebaseAuth.getInstance();

        getSupportActionBar().hide();

        goToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }

}