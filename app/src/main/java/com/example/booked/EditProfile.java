package com.example.booked;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.booked.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;


public class EditProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    User currentUser;
    Button confirmButton;
    EditText username, telephone, facebook, twitter , instagram;

    private Spinner chooseUniversitySpinner;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private String selectedUniversity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentUser = Booked.getCurrentUser();

        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();

        setEditTexts();

        setButtons();

    }

    void setButtons() {
        confirmButton = (Button) findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //currentUser.setName(userName.getText().toString());// isim değiştirme eklersek yorumlardaki isimleri de değiştirmek gerekir

                // E-mail kaldır, username yap
                currentUser.setUniversity(selectedUniversity);
                currentUser.setPhoneNumber(telephone.getText().toString());
                currentUser.setUserName(username.getText().toString());
                //currentUser.clearSocialMedia();
                currentUser.setSocialMedia(0,facebook.getText().toString());
                currentUser.setSocialMedia(1,twitter.getText().toString());
                currentUser.setSocialMedia(2,instagram.getText().toString());
                //social media?

                db.collection("usersObj").document(mAuth.getCurrentUser().getUid()).set(currentUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditProfile.this,"Information uploaded to database!", Toast.LENGTH_LONG).show();
                    }
                });

                //open new page
                Intent profile = new Intent(getApplicationContext(), MyProfile.class);
                startActivity(profile);

            }
        });
    }

    void setEditTexts()
    {
        username = (EditText) findViewById(R.id.username);
        telephone = (EditText) findViewById(R.id.telephone);
        twitter = (EditText) findViewById(R.id.twitter);
        facebook = (EditText) findViewById(R.id.facebook);
        instagram = (EditText) findViewById(R.id.instagram);

        chooseUniversitySpinner = (Spinner) findViewById(R.id.chooseUniversitySpinner);

        ArrayAdapter<CharSequence> universityAdapter = ArrayAdapter.createFromResource(this,R.array.universities, android.R.layout.simple_spinner_item);

        universityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        chooseUniversitySpinner.setAdapter(universityAdapter);

        chooseUniversitySpinner.setOnItemSelectedListener( (AdapterView.OnItemSelectedListener) this);

        username.setText(currentUser.getName().toString());
        telephone.setText(currentUser.getPhoneNumber().toString());


        if (currentUser.getSocialMedia().get(0) != null) {
                facebook.setText(currentUser.getSocialMedia().get(0));
        }
        if (currentUser.getSocialMedia().get(1) != null) {
                twitter.setText(currentUser.getSocialMedia().get(1));
        }
        if (currentUser.getSocialMedia().get(2) != null) {
                instagram.setText(currentUser.getSocialMedia().get(2));
        }



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if ( parent.getId() == chooseUniversitySpinner.getId() ) {
            selectedUniversity = chooseUniversitySpinner.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}