package com.example.booked;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.booked.models.Book;
import com.example.booked.models.Post;
import com.example.booked.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class PostPage extends AppCompatActivity implements ReportDialog.ReportypeListener {

    TextView title, sellerName, course, features, price;
    Button profilePageButton;
    Button visitSeller;
    ImageButton report;
    Post currentPost;

    private User currentSeller;

    ImageView postImageView;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postpage);

        db = FirebaseFirestore.getInstance();

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setCurrentPost();

        setTextViews();

        setButtons();

    }


    public void setCurrentPost()
    {

        currentPost = Booked.getCurrentPost();
    }

    @SuppressLint("SetTextI18n")
    public void setTextViews()
    {
        price = (TextView) findViewById(R.id.postPrice);
        price.setText(String.valueOf(currentPost.getPrice()) + "₺");

        title = (TextView) findViewById(R.id.postTitle);
        title.setText(currentPost.getTitle());

        sellerName = (TextView) findViewById(R.id.postseller);
        sellerName.setText(currentPost.getSeller().getName());

        course = (TextView) findViewById(R.id.postschoolLesson);
        course.setText(currentPost.getUniversity() + "/ "+ currentPost.getCourse() );

        features = (TextView) findViewById(R.id.postFeatures);
        features.setText(currentPost.getDescription());

        postImageView = (ImageView) findViewById(R.id.postImageView);
        Picasso.get().load(currentPost.getPicture()).fit().into(postImageView);

    }

    public void setButtons()
    {
        visitSeller = (Button) findViewById(R.id.GoSeller);
        visitSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("usersObj").whereEqualTo(FieldPath.documentId(), currentPost.getSeller().getDocumentId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //burada currentPost.getSeller vardı
                        for (DocumentSnapshot documentSnapshot : task.getResult() ) {

                            currentSeller = documentSnapshot.toObject(User.class);
                            Toast.makeText(PostPage.this, "User Pulled", Toast.LENGTH_LONG).show();

                            Booked.setCurrentSeller(currentSeller);
                            Intent sellerPage = new Intent(getApplicationContext(), OtherUsersProfile.class);
                            startActivity(sellerPage);
                        }
                    }
                });
            }
        });

        profilePageButton = (Button) findViewById(R.id.GoProfilePage);
        profilePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("bookProfileObj").whereEqualTo(FieldPath.documentId(), currentPost.getBook().getId()).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                            @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        for (DocumentSnapshot documentSnapshot : task.getResult() ) {

                            Booked.setCurrentBookProfile(documentSnapshot.toObject(com.example.booked.models.BookProfile.class));

                            Toast.makeText(PostPage.this, "Book Profile Pulled", Toast.LENGTH_LONG).show();

                            Intent profilePage = new Intent(getApplicationContext(), BookProfile.class);
                            startActivity(profilePage);
                        }
                    }
                });




            }
        });

        report = (ImageButton) findViewById(R.id.reportPost);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment popUp = new ReportDialog();
                popUp.show(getSupportFragmentManager(), "ReportDialog");

            }
        });

    }

    @Override
    public void positiveClicked(String[] reportTypes, int position) {
        //en fazla 1 kere reportlama ekle

        currentPost.report(reportTypes[position],position,Booked.getCurrentUser());
        db.collection("postsObj").document(currentPost.getId()).update("reports",currentPost.getReports());


    }

    @Override
    public void negativeClicked() {

    }
}