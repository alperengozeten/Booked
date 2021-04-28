package com.example.booked;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.booked.models.Book;
import com.example.booked.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    EditText mEmail;
    EditText mPassword;
    Button mLoginButton;
    Button mSignUpButton;
    Button mForgetPasswordButton;

    FirebaseAuth mAuth;

    FirebaseFirestore db;

    User currentUser;

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

        db = FirebaseFirestore.getInstance();

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
                startActivity( new Intent( getApplicationContext(), ForgotPasswordActivity.class));
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
                        db.collection("users").document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
                        {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                String newUserName = documentSnapshot.getString("username");
                                String newUserEmail = documentSnapshot.getString("email");
                                String newUserAvatar = documentSnapshot.getString("avatar");
                                String newUserUniversity = documentSnapshot.getString("university");
                                String newUserPhoneNumber = documentSnapshot.getString("phonenumber");
                                boolean newUserIsBanned = documentSnapshot.getBoolean("isbanned");
                                boolean newUserNotifications = documentSnapshot.getBoolean("notifications");
                                ArrayList<Book> newUserWishList = (ArrayList<Book>) documentSnapshot.get("wishlist");
                                ArrayList<String> newUserSocialMedia = (ArrayList<String>) documentSnapshot.get("socialmedia");

                                currentUser = new User(newUserName, newUserEmail, newUserAvatar, newUserSocialMedia, newUserPhoneNumber, newUserUniversity, newUserNotifications, newUserIsBanned, newUserWishList);
                                Booked.setCurrentUser(currentUser);
                                Toast.makeText(LoginActivity.this, "User Pulled", Toast.LENGTH_LONG).show();
                            }
                        });
                        Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
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