package com.example.booked;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.booked.Adapter.ReportedListAdapter;
import com.example.booked.models.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**This class shows admins reported posts and how many time they are reported.
 * */
public class ReportedPosts extends AppCompatActivity {

    ArrayList<Post> reportedPostList;
    RecyclerView reportedList;
    ReportedListAdapter reportedAdapter;
    private FirebaseFirestore db;

    /**
     * First method that is called when this activity is open
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reported_posts);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = FirebaseFirestore.getInstance();
        reportedPostList = new ArrayList<Post>();

    /**
     * This method called first to initialize properties and create view
     * @param savedInstanceState
     */
        pullFromDateBase();

        viewReports();

    }

    /**
     * this methods pulls reported posts from database.
     * */
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

    /**
     * This method set rcylerview and its adapter.
     * */
    void viewReports() {
        reportedList = (RecyclerView) findViewById(R.id.reportedList);

        reportedAdapter= new ReportedListAdapter(reportedPostList);

        reportedList.setAdapter(reportedAdapter);

        reportedList.setLayoutManager(new LinearLayoutManager(this));

        reportedAdapter.notifyDataSetChanged();
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