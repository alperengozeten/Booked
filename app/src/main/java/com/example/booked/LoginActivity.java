package com.example.booked;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

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

        mEmail = (EditText) findViewById(R.id.emailAddressEditTextL);
        mPassword = (EditText) findViewById(R.id.passwordEditTextL);
        mLoginButton = (Button) findViewById(R.id.loginBtn);
        mSignUpButton = (Button) findViewById(R.id.signUpBtnL);
        mForgetPasswordButton = (Button) findViewById(R.id.forgotYourPasswordBtn);
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId()) {
            case R.id.loginBtn:
                loginUser();
                break;
            case R.id.signUpBtn:
                startActivity( new Intent( this, SignUpActivity.class));
                break;
            case R.id.forgotYourPasswordBtn:
                startActivity( new Intent( this, ForgetPasswordActivity.class));
                break;
        }
    }

    private void loginUser() {

    }
}