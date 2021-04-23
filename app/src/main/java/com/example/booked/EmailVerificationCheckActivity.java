package com.example.booked;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class EmailVerificationCheckActivity extends AppCompatActivity {

    Button checkBtn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification_2);

        checkBtn = findViewById(R.id.checkButton);

        mAuth = FirebaseAuth.getInstance();

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.getCurrentUser().reload();

                if ( mAuth.getCurrentUser().isEmailVerified() ) {
                    Toast.makeText(getApplicationContext(), "Logged in Successfully", Toast.LENGTH_SHORT).show();
                    startActivity( new Intent( EmailVerificationCheckActivity.this, SuccessfulVerificationActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(EmailVerificationCheckActivity.this, "Please verify your email address!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}