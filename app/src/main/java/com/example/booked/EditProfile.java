package com.example.booked;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.booked.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;


public class EditProfile extends AppCompatActivity {

    User currentUser;
    Button confirmButton;
    EditText username, university, telephone, facebook, twitter , instagram;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

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
                currentUser.setUniversity(university.getText().toString());
                currentUser.setPhoneNumber(telephone.getText().toString());
                currentUser.setUserName(username.getText().toString());
                //currentUser.clearSocialMedia();
                currentUser.setSocialMedia(0,facebook.getText().toString());
                currentUser.setSocialMedia(1,twitter.getText().toString());
                currentUser.setSocialMedia(2,instagram.getText().toString());
                //social media?

                /**HashMap<String,Object> newData = new HashMap<>();
                newData.put("username", currentUser.getName());
                newData.put("email", currentUser.getEmail());
                newData.put("avatar", currentUser.getAvatar());
                newData.put("socialmedia", currentUser.getSocialMedia());
                newData.put("phonenumber", currentUser.getPhoneNumber());
                newData.put("university", currentUser.getUniversity());
                newData.put("notifications", currentUser.isNotifications());
                newData.put("isbanned", currentUser.isBanned());
                newData.put("wishlist", currentUser.getWishlist());*/
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
        university = (EditText) findViewById(R.id.university);
        telephone = (EditText) findViewById(R.id.telephone);
        twitter = (EditText) findViewById(R.id.twitter);
        facebook = (EditText) findViewById(R.id.facebook);
        instagram = (EditText) findViewById(R.id.instagram);

        username.setText(currentUser.getName().toString());
        university.setText(currentUser.getUniversity().toString());
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
}