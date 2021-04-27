package com.example.booked;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.booked.Adapter.OfferRecyclerApapter;
import com.example.booked.Adapter.ReportedListAdapter;
import com.example.booked.models.Book;
import com.example.booked.models.Post;
import com.example.booked.models.User;

import java.util.ArrayList;

public class ReportedPosts extends AppCompatActivity {

    ArrayList<Post> reportedPostList;
    RecyclerView reportedList;
    ReportedListAdapter reportedAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reported_posts);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        setExampleR();



        viewReports();

    }

    void viewReports() {
        reportedList = (RecyclerView) findViewById(R.id.reportedList);

        reportedAdapter= new ReportedListAdapter(reportedPostList);

        reportedList.setAdapter(reportedAdapter);
        reportedList.setLayoutManager(new LinearLayoutManager(this));
        reportedAdapter.notifyDataSetChanged();
    }

    void setExampleR()
    {
        User u1 = new User("emaillll","safa");
        Post p1 = new Post("Examlpe1","bad post","Mat111",50,"no", new Book("ww","sd"), u1 );
        Post p2 = new Post("Examlpe2","ugly post","Mat121",20,"no", Booked.getCurrentBook(), u1 );

        p1.report("w",1, u1);
        p1.report("w",2, Booked.getCurrentUser());
        p2.report("w",2, Booked.getCurrentUser());

        reportedPostList = new ArrayList<Post>();
        reportedPostList.add(p1);
        reportedPostList.add(p2);
    }
}