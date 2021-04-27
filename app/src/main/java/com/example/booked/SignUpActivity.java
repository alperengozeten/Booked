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

import com.example.booked.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity  {

    EditText mEmail;
    EditText mUserName;
    EditText mPassword;
    Button mSignUpButton;
    Button mBackToLoginButton;
    Button mGoBackToLogin;

    FirebaseAuth mAuth;

    FirebaseFirestore db;

    User user;


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


        //delete this later
        // if the user already signed in and not logged out it directs the user to main page
//        if (mAuth.getCurrentUser() != null) {
////            startActivity(new Intent(com.example.booked.SignUpActivity.this, MainActivity.class));
////            finish();
//
//            if ( mAuth.getCurrentUser().isEmailVerified() ) {
//                Toast.makeText(getApplicationContext(), "Logged in Successfully", Toast.LENGTH_LONG).show();
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                finish();
//            }
////            else {
////                Toast.makeText(getApplicationContext(), "Please verify your email address!", Toast.LENGTH_LONG).show();
////            }
//        }

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        mGoBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
    }

    public void registerUser() {
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String userName = mUserName.getText().toString().trim();

        if (email.isEmpty() ) {
            mEmail.setError("Please enter your email address");
            mEmail.requestFocus();
            return;
        }
        else {
            mEmail.setError( null);
        }

        if (userName.isEmpty() ) {
            mUserName.setError("Please enter your user name");
            mUserName.requestFocus();
            return;
        }

        if ( !Patterns.EMAIL_ADDRESS.matcher(email).matches() ) {
            mEmail.setError("Please provide a valid email address!");
            mEmail.requestFocus();
            return;
        }

        if (password.isEmpty() ) {
            mPassword.setError("Please enter a password");
            mPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword( email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if ( task.isSuccessful()) {

                    FirebaseUser fUser = mAuth.getCurrentUser();
                    fUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user = new User( email, userName);
                                Booked.setCurrentUser(user);
                                HashMap<String,Object> newData = new HashMap<>();
                                newData.put("username", user.getName());
                                newData.put("email", user.getEmail());
                                newData.put("avatar", user.getAvatar());
                                newData.put("socialmedia", user.getSocialMedia());
                                newData.put("phonenumber", user.getPhoneNumber());
                                newData.put("university", user.getUniversity());
                                newData.put("notifications", user.isNotifications());
                                newData.put("isbanned", user.isBanned());
                                newData.put("wishlist", user.getWishlist());
                                db.collection("users").document(fUser.getUid()).set(newData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(SignUpActivity.this,"Information uploaded to database!", Toast.LENGTH_LONG).show();
                                    }
                                });
                                Toast.makeText(com.example.booked.SignUpActivity.this, "Registered successfully! Please check your email for verification", Toast.LENGTH_LONG).show();
                                Intent i =  new Intent( getApplicationContext(), EmailVerificationCheckActivity.class);
                                i.putExtra("email",email);
                                i.putExtra("password", password);
                                //startActivity(i);
//                                finish();
                            }
                            else {
                                Toast.makeText(com.example.booked.SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });


                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser()
                                    .getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
//                                Toast.makeText(com.example.booked.SignUpActivity.this, "User has been registered successfully!  Account has been created", Toast.LENGTH_LONG).show();
//                                startActivity( new Intent( com.example.booked.SignUpActivity.this, MainActivity.class));
                                finish();
                            }
                            else {
                                Toast.makeText(com.example.booked.SignUpActivity.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(com.example.booked.SignUpActivity.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
