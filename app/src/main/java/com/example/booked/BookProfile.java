package com.example.booked;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
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
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booked.Adapter.CommentRecyclerAdapter;
import com.example.booked.Adapter.OfferRecyclerApapter;
import com.example.booked.models.Evaluation;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

/**
 * This is the class of BookProfile
 *
 */

public class BookProfile extends AppCompatActivity implements AddEvaluationDialog.CommentListener {

    com.example.booked.models.BookProfile myBookProfile;

    TextView title, rating, textNumOfComments;

    RecyclerView offersList, commentList;
    OfferRecyclerApapter offerAdapter;
    CommentRecyclerAdapter commentAdapter;
    Button addEvaluation, addToWishList, lowToHigh, highToLow, aToz, zToA;
    ImageView s1,s2,s3,s4,s5, bookProfileImageView;
    private FirebaseFirestore db;

    boolean isEvaluatedBefore;

    /**
     * This is the first method called when an instance of this class is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_profile);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = FirebaseFirestore.getInstance();

        setBookProfile();
        isEvaluatedBefore = isEvaluatedBefore();

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

    /**
     * This method checks if there are evaluations on this book
     * @return true if there are evaluations on this book profile
     * @return false if there are not any evaluations on this book profile
     */
    private boolean isEvaluatedBefore() {

        for( Evaluation e : myBookProfile.getEvalutions() )
        {
            if(e.getEvaluater().equals(Booked.getCurrentUser())) {
                return true;
            }
        }

        return false;
    }

    /**
     * This method creates the rating stars on book profile
     */
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

    /**
     * This method initializes TextViews of this class
     */
    void setTextViews()
    {
        title = (TextView) findViewById(R.id.bookprofiletitle);
        rating = (TextView) findViewById(R.id.bookProfileRating);
        textNumOfComments = (TextView) findViewById(R.id.commentNum);

        title.setText(myBookProfile.getBook().getBookName());
        rating.setText(String.valueOf("    " + Math.round(myBookProfile.getRating() * 100 ) / 100.0));
        textNumOfComments.setText(String.valueOf(myBookProfile.getEvalutions().size() + " comments"));

    }

    /**
     * This method initializes Buttons of this class
     */
    @SuppressLint("SetTextI18n")
    void setButtons()
    {
        addEvaluation = (Button) findViewById(R.id.bookprofile_evaluate);

        if(!isEvaluatedBefore)
        {
            addEvaluation.setText("Add Evaluation");
        }

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
                Booked.updateUserInDatabase(Booked.getCurrentUser().getDocumentId(),Booked.getCurrentUser());

                notifyUser();
                Toast.makeText(BookProfile.this, "Wish List Updated", Toast.LENGTH_LONG).show();

                /**db.collection("usersObj").document(Booked.getCurrentUser().getDocumentId()).set(Booked.getCurrentUser())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });*/
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

    private void notifyUser() {
        Intent resultIntent = new Intent(this, WishlistActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 4,
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, Booked.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Wishlist Updated!")
                .setContentText("Book has been added to your wishlist.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent);

        NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(this);
        mNotificationManager.notify(4, mBuilder.build());
    }

    /**
     * This method shows offers(posts) made on this book profile
     */
    void viewOffers()
    {
        offersList = (RecyclerView) findViewById(R.id.profileoffers);
        offerAdapter = new OfferRecyclerApapter(myBookProfile);

        offersList.setAdapter(offerAdapter);
        offersList.setLayoutManager(new LinearLayoutManager(this));
        offerAdapter.notifyDataSetChanged();

    }

    /**
     * This method shows comments made on this book profile
     */
    void viewComments()
    {
        commentList = (RecyclerView) findViewById(R.id.bookprofile_comments);
        commentList.setHasFixedSize(true);
        commentAdapter = new CommentRecyclerAdapter(myBookProfile);

        commentList.setAdapter(commentAdapter);
        commentList.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        commentList.addItemDecoration(decoration);

        commentAdapter.notifyDataSetChanged();

    }

    /**
     * This method initializes the current Book Profile and sorts offers
     */
    void setBookProfile()
    {
        myBookProfile  = Booked.getCurrentBookProfile();
        myBookProfile.sortByPrice(true);
    }

    /**
     * This method updates the comments and rating of the Book Profile when a new comment is made
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void positiveClicked(String comment, double rate) {

        if(isEvaluatedBefore)
        {
            for(int i = 0; i < myBookProfile.getEvalutions().size(); i++)
            {
                if(myBookProfile.getEvalutions().get(i).getEvaluater().equals(Booked.getCurrentUser())) {
                    myBookProfile.getEvalutions().remove(i);
                }
            }

        }

        myBookProfile.addEvalution(new Evaluation(comment, rate, Booked.getCurrentUser()));
        setStars();
        commentAdapter.notifyDataSetChanged();

        //setlemek yerine updatele
        Booked.updateBookProfileInDatabase(myBookProfile.getBook().getId(), myBookProfile);
        /**db.collection("bookProfileObj").document(myBookProfile.getBook().getId()).set(myBookProfile)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(BookProfile.this, "Evaluations Updated", Toast.LENGTH_LONG).show();
                }
            });*/

        isEvaluatedBefore = true;
        addEvaluation.setText("Change Your Evaluation");

        textNumOfComments.setText(String.valueOf(myBookProfile.getEvalutions().size() + " comments"));
        rating.setText(String.valueOf(Math.round(myBookProfile.getRating() * 100 ) / 100.0));


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
                Intent bookIntent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(bookIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}