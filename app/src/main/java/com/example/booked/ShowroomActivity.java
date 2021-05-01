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
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.booked.Adapter.MyShowroomPostAdapter;
import com.example.booked.models.Post;
import com.example.booked.models.Showroom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * ShowroomActivity page Class
 */
public class ShowroomActivity extends AppCompatActivity implements ShowroomMenuDialog.ShowroomMenuDialogListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private SearchView searchView;

    private ImageButton menuImageBtn;
    private ImageButton showroomAddPostBtn;

    private Button priceLowToHighBtn;
    private Button priceHighToLowBtn;
    private Button aToZBtn;
    private Button zToABtn;

    private FirebaseFirestore db;

    private ArrayList<Post> posts;

    private Showroom showroom;

    /**
     * This method is called first when this activity is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showroom);

        // Initialize the database instance
        db = FirebaseFirestore.getInstance();

        // Set the top icons
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize the recycler view
        recyclerView = (RecyclerView) findViewById(R.id.showroomPostList);
        recyclerView.setHasFixedSize(true);

        // Set the layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Pull the posts from the database
        posts = new ArrayList<>();
        db.collection("postsObj").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if ( task.isSuccessful() ) {
                    for (DocumentSnapshot document : task.getResult() ) {

                        // Add the post to the posts array
                        posts.add(document.toObject(Post.class));
                    }
                    Toast.makeText(ShowroomActivity.this, String.valueOf(posts.size()), Toast.LENGTH_SHORT).show();

                    // Create the adapters
                    showroom = new Showroom(posts);
                    mAdapter = new MyShowroomPostAdapter( showroom, ShowroomActivity.this);
                    recyclerView.setAdapter(mAdapter);

                    // This sets the search views to make the search
                    searchView = (SearchView) findViewById(R.id.showroomSearchView);
                    searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    searchView.setIconifiedByDefault(false);

                    // Set filter listener
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            ((MyShowroomPostAdapter) mAdapter).getFilter().filter(newText);
                            return false;
                        }
                    });
                }
            }
        });

        // Find and initialize the buttons
        menuImageBtn = (ImageButton) findViewById(R.id.menuImageBtn);
        showroomAddPostBtn = (ImageButton) findViewById(R.id.showroomAddPostBtn);

        priceLowToHighBtn = (Button) findViewById(R.id.priceLowToHighBtn);
        priceHighToLowBtn = (Button) findViewById(R.id.priceHighToLowBtn);
        aToZBtn = (Button) findViewById(R.id.aToZBtn);
        zToABtn = (Button) findViewById(R.id.zToABtn);

        // Menu button opens up the menu dialog
        menuImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMenuDialog();
            }
        });

        // add post button opens up the add post page
        showroomAddPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                Intent intent = new Intent(getApplicationContext(), AddPost.class);
                startActivity(intent);
            }
        });

        // atoz button applies sorting
        aToZBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MyShowroomPostAdapter) mAdapter).sort(v);
            }
        });

        // ztoa button applies sorting
        zToABtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText( getApplicationContext(),showroom.getPosts().toString(), Toast.LENGTH_LONG).show();
                ((MyShowroomPostAdapter) mAdapter).sort(v);
            }
        });

        // priceLowToHighBtn applies sorting
        priceLowToHighBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MyShowroomPostAdapter) mAdapter).sort(v);
            }
        });

        // priceHighToLowBtn applies sorting
        priceHighToLowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MyShowroomPostAdapter) mAdapter).sort(v);
            }
        });
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

    /**
     * This method opens up the menu dialog
     */
    public void openMenuDialog() {
        ShowroomMenuDialog menuDialog = new ShowroomMenuDialog();
        menuDialog.show(getSupportFragmentManager(),"menu dialog");
    }

    /**
     *  This method applies the filterings through calling the filter method of the adapter
     */
    @Override
    public void applyTexts(String filteredUniversity, String filteredCourse, String firstPrice, String secondPrice) {
        //Toast.makeText(this,"First price is " + firstPrice +" Second Price is " + secondPrice + "Filtered Uni: " +filteredUniversity + " Filtered Course:" + filteredCourse, Toast.LENGTH_SHORT).show();
        int intFirstPrice = Integer.parseInt(firstPrice);
        int intSecondPrice = Integer.parseInt(secondPrice);

        ((MyShowroomPostAdapter) mAdapter).filter(filteredUniversity, filteredCourse, intFirstPrice, intSecondPrice);
    }

    // resets the filters
    public void resetFilters() {
        ((MyShowroomPostAdapter) mAdapter).resetFilters();
    }
}