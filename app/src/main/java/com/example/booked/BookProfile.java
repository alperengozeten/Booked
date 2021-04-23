package com.example.booked;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booked.Adapter.CommentRecyclerAdapter;
import com.example.booked.Adapter.OfferRecyclerApapter;
import com.example.booked.models.Evaluation;


public class BookProfile extends AppCompatActivity implements AddEvaluationDialog.CommentListener {

    com.example.booked.models.BookProfile myBookProfile;

    TextView title, rating, course, textNumOfComments;

    RecyclerView offersList, commentList;
    OfferRecyclerApapter offerAdapter;
    CommentRecyclerAdapter commentAdapter;
    Button addEvaluation;
    ImageView s1,s2,s3,s4,s5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_profile);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        createBookProfile();


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
        course = (TextView) findViewById(R.id.bookprofile_school_lesson);
        textNumOfComments = (TextView) findViewById(R.id.commentNum);

        title.setText(myBookProfile.getBook().getBookName());
        rating.setText(String.valueOf(Math.round(myBookProfile.getRating() * 100 ) / 100.0));
        textNumOfComments.setText(String.valueOf(myBookProfile.getEvalutions().size() + " comments"));

        //bookProfile da course ve lesson yok, ya bunu burdan çıkarıcaz
        //ya da add post yaparken university ve course yerine sadece book seçecek ve book da university,course özellikleri olacak
        // bunun yerine adminin düzenlediği bir description bölmü olabilir, orada hangi unilerde kullanıldığını yazar
        //course.setText(myBookProfile.);
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
    }

    void viewOffers()
    {
        offersList = (RecyclerView) findViewById(R.id.profileoffers);
        offerAdapter = new OfferRecyclerApapter(myBookProfile);

        offersList.setAdapter(offerAdapter);
        offersList.setLayoutManager(new LinearLayoutManager(this));
        offerAdapter.notifyDataSetChanged();

        //offer sıralamsı ekle

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

    //sonrasil
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
        textNumOfComments.setText(String.valueOf(myBookProfile.getEvalutions().size() + " comments"));
        rating.setText(String.valueOf(Math.round(myBookProfile.getRating() * 100 ) / 100.0));
        setStars();
        commentAdapter.notifyDataSetChanged();
    }
}