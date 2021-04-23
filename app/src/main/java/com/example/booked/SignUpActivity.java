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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity  {

    EditText mEmail;
    EditText mUserName;
    EditText mPassword;
    Button mSignUpButton;
    Button mBackToLoginButton;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().hide();

        mEmail = (EditText) findViewById(R.id.mailAdressEditText);
        mUserName = (EditText) findViewById(R.id.userNameEditText);
        mPassword = (EditText) findViewById(R.id.passwordEditText);
        mSignUpButton = (Button) findViewById(R.id.signUpBtn);
        //mBackToLoginButton = (Button) findViewById(R.id.buttonBackToLogin);

        mAuth = FirebaseAuth.getInstance();

        // delete this later
        // if the user already signed in and not logged out it directs the user to main page
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(com.example.booked.SignUpActivity.this, MainActivity.class));
            finish();
        }


        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

//    @Override
//    public void onClick(View v) {
//        switch ( v.getId()) {
//            case R.id.signUpBtn:
//                registerUser();
//                break;
//            //case R.id.buttonBackToLogin:
//            //    startActivity( new Intent( this, MainActivity.class));
//            //    break;
//        }
//    }

    private void registerUser() {
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

                    //FirebaseUser fUser = mAuth.getCurrentUser();
                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(com.example.booked.SignUpActivity.this, "Registered successfully! Please check your email for verification", Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(com.example.booked.SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });


                    User user = new User( email, userName);

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser()
                                    .getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(com.example.booked.SignUpActivity.this, "User has been registered successfully!  Account has been created", Toast.LENGTH_LONG).show();
                                startActivity( new Intent( com.example.booked.SignUpActivity.this, MainActivity.class));
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
