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

import com.example.booked.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

/**
 * A class for the Register Screen
 *
 * @author NoExpection
 * @version 2021 Spring
 */
public class SignUpActivity extends AppCompatActivity  {

    // Properties
    EditText mEmail;
    EditText mUserName;
    EditText mPassword;
    Button mSignUpButton;
    Button mGoBackToLogin;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    User user;

    /**
     * This method sets the activity on create by overriding AppCompatActivity's onCreate method.
     *
     * @param savedInstanceState - Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().hide();

        mEmail = (EditText) findViewById(R.id.mailAdressEditText);
        mUserName = (EditText) findViewById(R.id.userNameEditText);
        mPassword = (EditText) findViewById(R.id.passwordEditText);
        mSignUpButton = (Button) findViewById(R.id.signUpBtn);
        mGoBackToLogin = (Button) findViewById(R.id.goBackToLogin);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Sets ClickListener object for Sign Up button.
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        // Sets ClickListener object for Back to Login button.
        mGoBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
    }

    /**
     * Validates all of the input, registers a new user with the given email,
     * username and password and saves the user's data to database.
     */
    private void registerUser() {
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String userName = mUserName.getText().toString().trim();

        // if the email field is empty, show an error message.
        if (email.isEmpty() ) {
            mEmail.setError("Please enter your email address");
            mEmail.requestFocus();
            return;
        }
        else {
            mEmail.setError( null);
        }

        // if the username field is empty, show an error message.
        if (userName.isEmpty() ) {
            mUserName.setError("Please enter your user name");
            mUserName.requestFocus();
            return;
        }
        else {
            mUserName.setError( null);
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
        if (password.length() < 6) {
            mPassword.setError("Please enter a password with at least 6 characters");
            mPassword.requestFocus();
            return;
        }
        else {
            mEmail.setError( null);
        }

        // create a user with the given email and password and add the user's data to database.
        mAuth.createUserWithEmailAndPassword( email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // if task is successful, send an email verification link.
                if ( task.isSuccessful()) {

                    FirebaseUser fUser = mAuth.getCurrentUser();
                    fUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // if the sending process successful, create a new user, add the data to database and transfer the data to the verification activity.
                            if (task.isSuccessful()) {
                                user = new User( userName,email ,mAuth.getUid());
                                Booked.setCurrentUser(user);

                                /**HashMap<String,Object> newData = new HashMap<>();
                                newData.put("username", user.getName());
                                newData.put("email", user.getEmail());
                                newData.put("avatar", user.getAvatar());
                                newData.put("socialmedia", user.getSocialMedia());
                                newData.put("phonenumber", user.getPhoneNumber());
                                newData.put("university", user.getUniversity());
                                newData.put("notifications", user.isNotifications());
                                newData.put("isbanned", user.isBanned());
                                newData.put("wishlist", user.getWishlist());*/
                                db.collection("usersObj").document(mAuth.getUid()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Toast.makeText(SignUpActivity.this,"Information uploaded to database!", Toast.LENGTH_SHORT).show();

                                    }
                                });
                                Toast.makeText(com.example.booked.SignUpActivity.this, "Registered successfully! Please check your email for verification", Toast.LENGTH_SHORT).show();
                                Intent i =  new Intent( getApplicationContext(), EmailVerificationCheckActivity.class);
                                i.putExtra("email",email);
                                i.putExtra("password", password);
                                startActivity(i);
                                finish();
                            }
                            // otherwise, show an error message.
                            else {
                                Toast.makeText(com.example.booked.SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                // otherwise, show an error message.
                else {
                    Toast.makeText(com.example.booked.SignUpActivity.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
