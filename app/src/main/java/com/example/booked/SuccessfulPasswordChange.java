package com.example.booked;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class SuccessfulPasswordChange extends AppCompatActivity  {

    Button goToMainPageBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succesful_change_password);

        goToMainPageBtn = (Button) findViewById( R.id.goToMainPageBtn);

        goToMainPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }

}
