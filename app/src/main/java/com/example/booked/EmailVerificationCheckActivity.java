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

//        if (mAuth.getCurrentUser() != null) {
//
////            if ( mAuth.getCurrentUser().isEmailVerified() ) {
//////                Toast.makeText(getApplicationContext(), "Logged in Successfully", Toast.LENGTH_LONG).show();
////                startActivity(new Intent(EmailVerificationCheckActivity.this, SuccessfulVerificationActivity.class));
////                finish();
////            }
////            else {
////                Toast.makeText(getApplicationContext(), "Please verify your email address!", Toast.LENGTH_LONG).show();
////            }
////            startActivity(new Intent(EmailVerificationCheckActivity.this, MainActivity.class));
////            finish();
//        }


        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.getCurrentUser().reload();
//                        .addOnSuccessListener(new OnSuccessListener() {
//                    @Override
//                    public void onSuccess(Object o) {
////                        boolean useremailveri = mAuth.getCurrentUser().isEmailVerified();
////                        String useremailuid = mAuth.getCurrentUser().getUid();
//                    }
//
//                });

                if ( mAuth.getCurrentUser().isEmailVerified() ) {
                    Toast.makeText(getApplicationContext(), "Logged in Successfully", Toast.LENGTH_SHORT).show();
                    startActivity( new Intent( EmailVerificationCheckActivity.this, SuccessfulVerificationActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(EmailVerificationCheckActivity.this, "Please check your email address", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}