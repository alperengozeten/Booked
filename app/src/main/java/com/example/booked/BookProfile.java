package com.example.booked;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booked.Adapter.CommentRecyclerAdapter;
import com.example.booked.Adapter.OfferRecyclerApapter;
import com.example.booked.models.Evaluation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;


public class BookProfile extends AppCompatActivity implements AddEvaluationDialog.CommentListener {

    com.example.booked.models.BookProfile myBookProfile;

    TextView title, rating, textNumOfComments;

    RecyclerView offersList, commentList;
    OfferRecyclerApapter offerAdapter;
    CommentRecyclerAdapter commentAdapter;
    Button addEvaluation, addToWishList, lowToHigh, highToLow, aToz, zToA;
    ImageView s1,s2,s3,s4,s5, bookProfileImageView;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_profile);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = FirebaseFirestore.getInstance();

        createBookProfile();

        bookProfileImageView = (ImageView) findViewById(R.id.bookProfileImageView);
        Picasso.get().load(myBookProfile.getBook().getPicture()).fit().into(bookProfileImageView);

        setButtons();
        setTextViews();

        viewOffers();
        viewComments();


        s1 = (ImageView) findViewById(R.id.rate1);
        s2 = (ImageView) findViewById(R.id.rate2);
        s3 = (ImageView) findViewById(R.id.rate3);
        s4 = (ImageView) findViewById(R.id.rate4);
        s5 = (ImageView) findViewById(R.id.rate5);
        setStars();
    }

    private void setStars() {

        ImageView[] stars = {s1,s2,s3,s4,s5};
        double rate = myBookProfile.getRating();

        for(int i = 0; i < 5; i++)
        {
            stars[i].setImageResource(R.drawable.ic_action_starempty);
        }

        for(int i = 0; i <= rate - 1 ; i++)
        {
            stars[i].setImageResource(R.drawable.ic_action_star);
        }


        if( rate - (int) rate > 0.25 )
        {
            int l = (int) rate;
            if( rate - (int) rate > 0.75 )
            {
                stars[l].setImageResource(R.drawable.ic_action_star);
            }
            else
            {
                stars[l].setImageResource(R.drawable.ic_action_starhalf);
            }
        }

    }


    void setTextViews()
    {
        title = (TextView) findViewById(R.id.bookprofiletitle);
        rating = (TextView) findViewById(R.id.bookProfileRating);
        textNumOfComments = (TextView) findViewById(R.id.commentNum);

        title.setText(myBookProfile.getBook().getBookName());
        rating.setText(String.valueOf(Math.round(myBookProfile.getRating() * 100 ) / 100.0));
        textNumOfComments.setText(String.valueOf(myBookProfile.getEvalutions().size() + " comments"));

    }

    void setButtons()
    {
        addEvaluation = (Button) findViewById(R.id.bookprofile_evaluate);
        addEvaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment evaluationPopUp = new AddEvaluationDialog();
                evaluationPopUp.show(getSupportFragmentManager(),"Evaluate");
            }
        });

        addToWishList = (Button) findViewById(R.id.bookProfile_addTowWishlist);
        addToWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( Booked.getCurrentUser().getWishlist().contains(myBookProfile.getBook()) ) // bunu başa ekleyeyip visble lığını ayarlayabilirz
                {
                    Toast.makeText(BookProfile.this, "You already have this book in your Wish List", Toast.LENGTH_LONG).show();
                }
                else
                {
                Booked.getCurrentUser().addBookToWishlist(myBookProfile.getBook());
                db.collection("usersObj").document(Booked.getCurrentUser().getDocumentId()).set(Booked.getCurrentUser())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(BookProfile.this, "Wish List Updated", Toast.LENGTH_LONG).show();
                            }
                        });
                }
            }
        });

        lowToHigh = (Button) findViewById(R.id.bookProfile_lowToHigh);
        lowToHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBookProfile.sortByPrice(true);
                offerAdapter.notifyDataSetChanged();
            }
        });

        highToLow = (Button) findViewById(R.id.bookprofile_highToLow);
        highToLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBookProfile.sortByPrice(false);
                offerAdapter.notifyDataSetChanged();
            }
        });

        aToz = (Button) findViewById(R.id.aToz);
        aToz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBookProfile.sortByLetter(true);
                offerAdapter.notifyDataSetChanged();
            }
        });

        zToA = (Button) findViewById(R.id.Ztoa);
        zToA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBookProfile.sortByLetter(false);
                offerAdapter.notifyDataSetChanged();
            }
        });
    }

    void viewOffers()
    {
        offersList = (RecyclerView) findViewById(R.id.profileoffers);
        offerAdapter = new OfferRecyclerApapter(myBookProfile);

        offersList.setAdapter(offerAdapter);
        offersList.setLayoutManager(new LinearLayoutManager(this));
        offerAdapter.notifyDataSetChanged();

    }

    void viewComments()
    {
        commentList = (RecyclerView) findViewById(R.id.bookprofile_comments);
        commentAdapter = new CommentRecyclerAdapter(myBookProfile);

        commentList.setAdapter(commentAdapter);
        commentList.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        commentList.addItemDecoration(decoration);

        commentAdapter.notifyDataSetChanged();

    }


    void createBookProfile()
    {
        myBookProfile  = Booked.getCurrentBookProfile();
        myBookProfile.sortByPrice(true);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void positiveClicked(String comment, double rate) {

        //evaluation silme
        //daha önce eklediyse ekleyemsein
        myBookProfile.addEvalution(new Evaluation(comment, rate, Booked.getCurrentUser()));

        //setlemek yerine updatele
        db.collection("bookProfileObj").document(myBookProfile.getBook().getId()).set(myBookProfile)
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(BookProfile.this, "Evaluations Updated", Toast.LENGTH_LONG).show();
            }
        });


        textNumOfComments.setText(String.valueOf(myBookProfile.getEvalutions().size() + " comments"));
        rating.setText(String.valueOf(Math.round(myBookProfile.getRating() * 100 ) / 100.0));
        setStars();
        commentAdapter.notifyDataSetChanged();
    }

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
                Intent settingsIntent = new Intent(getApplicationContext(), Settings.class);
                startActivity( settingsIntent);
                return true;
            case android.R.id.home:
                Intent bookIntent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(bookIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}