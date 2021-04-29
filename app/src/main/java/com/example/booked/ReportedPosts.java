package com.example.booked;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.booked.Adapter.OfferRecyclerApapter;
import com.example.booked.Adapter.ReportedListAdapter;
import com.example.booked.models.Book;
import com.example.booked.models.Post;
import com.example.booked.models.Report;
import com.example.booked.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ReportedPosts extends AppCompatActivity {

    ArrayList<Post> reportedPostList;
    RecyclerView reportedList;
    ReportedListAdapter reportedAdapter;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reported_posts);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = FirebaseFirestore.getInstance();
        reportedPostList = new ArrayList<Post>();


        //resim eklenmesi lazım

        pullFromDateBase();

        viewReports();

    }

    private void pullFromDateBase() {


        db.collection("postsObj").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot : task.getResult() )
                        {
                            Post post = documentSnapshot.toObject(Post.class);
                            if(post.getReports().size() >= 1) {
                                reportedPostList.add(post);
                            }

                        }

                        reportedAdapter.notifyDataSetChanged();
                    }
                });
    }


    void viewReports() {
        reportedList = (RecyclerView) findViewById(R.id.reportedList);
        Log.d("reportedv1",reportedPostList.toString());
        reportedAdapter= new ReportedListAdapter(reportedPostList);
        Log.d("reportedv2",reportedPostList.toString());
        reportedList.setAdapter(reportedAdapter);
        Log.d("reportedv3",reportedPostList.toString());
        reportedList.setLayoutManager(new LinearLayoutManager(this));
        Log.d("reportedv4",reportedPostList.toString());
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
                Intent bookIntent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(bookIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}