package com.example.booked;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.booked.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * This page helps user to change his*her profile information.
 *
 * @author NoException
 * @version 2021 Spring
 */
public class EditProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    User currentUser;
    Button confirmButton;
    EditText telephone, facebook, twitter , instagram;
    TextView username;

    private Spinner chooseUniversitySpinner;


    private String selectedUniversity;

    /**
     * This method sets the activity on create by overriding AppCompatActivity's onCreate method.
     *
     * @param savedInstanceState - Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentUser = Booked.getCurrentUser();


        setEditTexts();

        setButtons();

    }

    /**
     * This methods sets the buttons on the page
     * */
    void setButtons() {
        confirmButton = (Button) findViewById(R.id.confirmButton);

        //when user click on confirm, information will be uploaded to database
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //currentUser.setName(userName.getText().toString());// isim değiştirme eklersek yorumlardaki isimleri de değiştirmek gerekir

                // E-mail kaldır, username yap
                currentUser.setUniversity(selectedUniversity);
                currentUser.setPhoneNumber(telephone.getText().toString());
                //currentUser.setUserName(username.getText().toString());

                currentUser.setSocialMedia(0,facebook.getText().toString());
                currentUser.setSocialMedia(1,twitter.getText().toString());
                currentUser.setSocialMedia(2,instagram.getText().toString());


                Booked.updateUserInDatabase(currentUser.getDocumentId(), currentUser);


                //open new page
                Intent profile = new Intent(getApplicationContext(), MyProfile.class);
                startActivity(profile);

            }
        });
    }

    /**
     * This method initializes and sets the texts on the edit text
     * and spinner for university
     * */
    void setEditTexts()
    {
        username = (TextView) findViewById(R.id.username);
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

    /**
     * This method is in all pages which creates the top menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
        return true;
    }

    /**
     * This method is in all pages which creates the functionality of the top menu
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_icon:
                Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity( settingsIntent);
                return true;
            case android.R.id.home:
                Intent bookIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(bookIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}