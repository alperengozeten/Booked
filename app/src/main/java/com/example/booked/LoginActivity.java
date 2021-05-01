package com.example.booked;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

/**
 * A class for the Login Page
 *
 * @author NoExpection
 * @version 2021 Spring
 */
public class LoginActivity extends AppCompatActivity {

    // Properties
    EditText mEmail;
    EditText mPassword;
    Button mLoginButton;
    Button mSignUpButton;
    Button mForgetPasswordButton;

    FirebaseAuth mAuth;

    FirebaseFirestore db;

    User currentUser;

    /**
     * This method sets the activity on create by overriding AppCompatActivity's onCreate method.
     *
     * @param savedInstanceState - Bundle
     */
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

        // if the user already signed in and not logged out it directs the user to main page.
        if (mAuth.getCurrentUser() != null) {
            if ( mAuth.getCurrentUser().isEmailVerified() ) {

                db.collection("usersObj").document(mAuth.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Booked.setCurrentUser(documentSnapshot.toObject(User.class));
                    }
                });

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }

        // Sets ClickListener object for Login button.
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        // Sets ClickListener object for Sign Up button.
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext(), SignUpActivity.class));
                finish();
            }
        });

        // Sets ClickListener object for Forgot Password button.
        mForgetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext(), ForgotPasswordActivity.class));
                finish();
            }
        });
    }


    /**
     * This method validates entered input in order for the user to login.
     */
    private void loginUser() {
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        // if the email field is empty, show an error message.
        if (email.isEmpty() ) {
            mEmail.setError("Please enter your email address");
            mEmail.requestFocus();
            return;
        }
        else {
            mEmail.setError( null);
        }

        // if the password field is empty, show an error message.
        if (password.isEmpty() ) {
            mPassword.setError("Please enter a password");
            mPassword.requestFocus();
            return;
        }
        else {
            mPassword.setError( null);
        }

        // if the email address is not valid, show an error message.
        if ( !Patterns.EMAIL_ADDRESS.matcher(email).matches() ) {
            mEmail.setError("Please provide a valid email address!");
            mEmail.requestFocus();
            return;
        }
        else {
            mEmail.setError( null);
        }

        // if it is not an edu.tr type email address, show an error
        if (!email.substring(email.indexOf("@")).contains(".edu.tr"))
        {
            mEmail.setError("This is not an email address with edu.tr extension");
            mEmail.requestFocus();
            return;
        }
        else {
            mEmail.setError( null);
        }

        // if the password is less than 6 chars, show an error message
        if (password.length() < 6 ) {
            mPassword.setError("Your password should be minimum 6 characters long");
            mPassword.requestFocus();
            return;
        }
        else {
            mEmail.setError( null);
        }

        // Signs in the user with the given email and password if the user's
        // email address is verified and pulls the user's data from the database.
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // if the user's email address is verified, it pulls user's data and transfers user to Main Page.
                    if ( mAuth.getCurrentUser().isEmailVerified() ) {
                        db.collection("usersObj").document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
                        {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                /**String newUserName = documentSnapshot.getString("username");
                                String newUserEmail = documentSnapshot.getString("email");
                                String newUserAvatar = documentSnapshot.getString("avatar");
                                String newUserUniversity = documentSnapshot.getString("university");
                                String newUserPhoneNumber = documentSnapshot.getString("phonenumber");
                                boolean newUserIsBanned = documentSnapshot.getBoolean("isbanned");
                                boolean newUserNotifications = documentSnapshot.getBoolean("notifications");
                                ArrayList<Book> newUserWishList = (ArrayList<Book>) documentSnapshot.get("wishlist");
                                ArrayList<String> newUserSocialMedia = (ArrayList<String>) documentSnapshot.get("socialmedia");
                                */
                                //currentUser = new User(newUserName, newUserEmail, newUserAvatar, newUserSocialMedia, newUserPhoneNumber, newUserUniversity, newUserNotifications, newUserIsBanned, newUserWishList);
                                //current user çıkartılabilir benje
                                currentUser = documentSnapshot.toObject(User.class);
                                Booked.setCurrentUser(currentUser);
                                Toast.makeText(LoginActivity.this, "User Pulled", Toast.LENGTH_LONG).show();

                            }
                        });
                        Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class)); // bunu user pulledın altına alabiliz
                        finish();
                    }
                    // otherwise, set an error message.
                    else {
                        mAuth.signOut();
                        Toast.makeText(LoginActivity.this, "Please verify your email address!", Toast.LENGTH_LONG).show();
                    }
                }
                // otherwise, set an error message.
                else{
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}