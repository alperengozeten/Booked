package com.example.booked;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ChangePassword2 extends AppCompatActivity {


    TextView mOldPassword;
    TextView mNewPassword;
    TextView mNewPasswordAgain;

    Button passwordConfirmBtn;

    FirebaseAuth mAuth;

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
                Intent settingsIntent = new Intent(getApplicationContext(), Settings2.class);
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

        passwordConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPassword()){
                    changePassword();
                    Intent intent = new Intent(getApplicationContext(), SuccessfulChangePassword.class);
                    startActivity(intent);

                    }

                }

        });
    }

    private boolean checkPassword(){
        String oldPassword = mOldPassword.getText().toString().trim();
        return true;



    }
    private void changePassword(){
        String newPassword = mNewPassword.getText().toString().trim();
        String newPasswordAgain = mNewPasswordAgain.getText().toString().trim();

        if(newPassword.equals(newPasswordAgain))
            mAuth.getCurrentUser().updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("password","User password updated.");
                        }
                    }
                });

    }

}