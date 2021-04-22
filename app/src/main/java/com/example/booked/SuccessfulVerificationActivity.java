package com.example.booked;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SuccessfulVerificationActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_verification);

    }

    @Override
    public void onClick(View v) {
        if ( v.getId() == R.id.goToMainPageBtn) {
            startActivity( new Intent( this, MainActivity.class));
            finish();
        }
    }
}