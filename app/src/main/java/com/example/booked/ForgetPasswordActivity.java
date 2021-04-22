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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText mEmail = findViewById( R.id.emailAddressEditTextF);
    Button mSendResetEmailBtn = findViewById( R.id.sendResetEmailButton);

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        mEmail = (EditText) findViewById(R.id.emailAddressEditTextF);
        mSendResetEmailBtn = (Button) findViewById(R.id.sendResetEmailButton);

        mAuth = FirebaseAuth.getInstance();

        mSendResetEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();

                if (email.isEmpty() ) {
                    mEmail.setError("Please enter your email address");
                    mEmail.requestFocus();
                    return;
                }

                if ( !Patterns.EMAIL_ADDRESS.matcher(email).matches() ) {
                    mEmail.setError("Please provide a valid email address!");
                    mEmail.requestFocus();
                    return;
                }

                mAuth.sendPasswordResetEmail( email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgetPasswordActivity.this, "Password reset ink has been sent to your email address: " + email, Toast.LENGTH_LONG).show();
                            startActivity( new Intent(getApplicationContext(), LoginActivity.class));

                        }
                        else{
                            Toast.makeText(ForgetPasswordActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }
        });
    }


}