package com.example.booked;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class SuccessfulChangePassword extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succesful_change_password);
    }

    @Override
    public void onClick(View v) {
        if ( v.getId() == R.id.goToMainPageBtn) {
            startActivity( new Intent( this, MainActivity.class));
            finish();
        }
    }
}
