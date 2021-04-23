package com.example.booked;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText mEmail;
    EditText mPassword;
    Button mLoginButton;
    Button mSignUpButton;
    Button mForgetPasswordButton;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        mEmail = (EditText) findViewById(R.id.emailAddressEditTextL);
        mPassword = (EditText) findViewById(R.id.passwordEditTextL);
        mLoginButton = (Button) findViewById(R.id.loginBtn);
        mSignUpButton = (Button) findViewById(R.id.signUpBtnL);
        mForgetPasswordButton = (Button) findViewById(R.id.forgotYourPasswordBtn);

        mAuth = FirebaseAuth.getInstance();

//         if the user already signed in and not logged out it directs the user to main page
        if (mAuth.getCurrentUser() != null) {
            if ( mAuth.getCurrentUser().isEmailVerified() ) {
//                Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
//            else {
//                Toast.makeText(LoginActivity.this, "Please verify your email address!", Toast.LENGTH_LONG).show();
//            }
//            startActivity(new Intent(com.example.booked.LoginActivity.this, MainActivity.class));
//            finish();
        }

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext(), SignUpActivity.class));
                finish();
            }
        });

        mForgetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext(), ForgetPasswordActivity.class));
                finish();
            }
        });


    }

    private void loginUser() {
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if (email.isEmpty() ) {
            mEmail.setError("Please enter your email address");
            mEmail.requestFocus();
            return;
        }
        else {
            mEmail.setError( null);
        }

        if (password.isEmpty() ) {
            mPassword.setError("Please enter a password");
            mPassword.requestFocus();
            return;
        }

        if ( !Patterns.EMAIL_ADDRESS.matcher(email).matches() ) {
            mEmail.setError("Please provide a valid email address!");
            mEmail.requestFocus();
            return;
        }

        if (password.length() < 6 ) {
            mPassword.setError("Please enter a password minimum 6 characters long");
            mPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if ( mAuth.getCurrentUser().isEmailVerified() ) {
                        Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                    else {
                        mAuth.signOut();
                        Toast.makeText(LoginActivity.this, "Please verify your email address!", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}