package com.example.booked;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.booked.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class EmailVerificationCheckActivity extends AppCompatActivity {

    Button checkBtn;
    Button resendBtn;
    Button goToLoginBtn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);

        checkBtn = findViewById(R.id.checkVerificationBtn);
        resendBtn = findViewById( R.id.resendCodeBtn);
        goToLoginBtn = findViewById( R.id.goToLoginPageBtn);

        mAuth = FirebaseAuth.getInstance();

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.getCurrentUser().reload();

                if ( mAuth.getCurrentUser().isEmailVerified() ) {
                    Toast.makeText(getApplicationContext(), "Logged in Successfully", Toast.LENGTH_SHORT).show();
                    startActivity( new Intent( getApplicationContext(), SuccessfulVerificationActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please verify your email address!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.getCurrentUser().reload();

                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "New verification email have been sent.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Wait and try later.", Toast.LENGTH_SHORT).show();
                        }

//                        task.getException().getMessage()
                    }
                });
            }
        });

        goToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
    }
}