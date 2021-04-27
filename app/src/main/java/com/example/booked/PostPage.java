package com.example.booked;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.booked.models.Post;


public class PostPage extends AppCompatActivity implements ReportDialog.ReportypeListener {

    TextView title, sellerName, course, features, price;
    Button profilePageButton;
    Button visitSeller;
    ImageButton report;
    Post currentPost;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postpage);

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

    }

    public void setButtons()
    {
        visitSeller = (Button) findViewById(R.id.GoSeller);
        visitSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {// düzelt
                Booked.setCurrentSeller(currentPost.getSeller());
                Intent sellerPage = new Intent(getApplicationContext(), OtherUsersProfile.class);
                startActivity(sellerPage);
            }
        });

        profilePageButton = (Button) findViewById(R.id.GoProfilePage);
        profilePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profilePage = new Intent(getApplicationContext(), BookProfile.class);
                startActivity(profilePage);
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

        currentPost.report(reportTypes[position],position,Booked.getCurrentUser());
        // burada reportu kaydedicez


    }

    @Override
    public void negativeClicked() {

    }
}