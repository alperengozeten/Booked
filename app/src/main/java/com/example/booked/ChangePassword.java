package com.example.booked;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {


    TextView mOldPassword;
    TextView mNewPassword;
    TextView mNewPasswordAgain;

    Button passwordConfirmBtn;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_icon:
                Intent settingsIntent = new Intent(getApplicationContext(), Settings.class);
                startActivity( settingsIntent);
                return true;
            case android.R.id.home:
                Intent bookIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(bookIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password2);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mOldPassword = (EditText) findViewById(R.id.oldPasswordEditText);
        mNewPassword = (EditText) findViewById(R.id.newPasswordEditText);
        mNewPasswordAgain = (EditText) findViewById(R.id.newPasswordAgainEditText);
        passwordConfirmBtn = (Button) findViewById(R.id.passwordConfirmBtn);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        passwordConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changePassword();
//                if(checkPassword()){
//                    changePassword();
////                    Intent intent = new Intent(getApplicationContext(), SuccessfulChangePassword.class);
////                    startActivity(intent);
//                }
            }
        });
    }

    private boolean checkPassword(){
        String oldPassword = mOldPassword.getText().toString().trim();
        return true;



    }

    private void changePassword(){
        String email = mUser.getEmail();
        String oldPassword = mOldPassword.getText().toString().trim();
        String newPassword = mNewPassword.getText().toString().trim();
        String newPasswordAgain = mNewPasswordAgain.getText().toString().trim();

        if (oldPassword.length() < 6) {
            mOldPassword.setError("Please enter your password");
            mOldPassword.requestFocus();
            return;
        }

        if (newPassword.isEmpty() ) {
            mNewPassword.setError("Please enter a password");
            mNewPassword.requestFocus();
            return;
        }
        if (newPassword.length() < 6) {
            mNewPassword.setError("Please enter a password with at least 6 characters");
            mNewPassword.requestFocus();
            return;
        }

        if ( oldPassword.equals(newPassword)) {
            mNewPassword.setError("Please enter a new password");
            mNewPassword.requestFocus();
            return;
        }

        if (newPasswordAgain.isEmpty() ) {
            mNewPasswordAgain.setError("Please enter a password");
            mNewPasswordAgain.requestFocus();
            return;
        }

        if( !(newPassword.equals(newPasswordAgain)) ) {
            mNewPasswordAgain.setError("Password Mismatch");
            mNewPasswordAgain.requestFocus();
        }
        else {
            AuthCredential credential = EmailAuthProvider.getCredential(email,oldPassword);

            mUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        mUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(ChangePassword.this, "Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(ChangePassword.this, "Password Successfully Modified", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), SuccessfulPasswordChange.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                    }
                    else {
                        Toast.makeText(ChangePassword.this, "Current password is wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}