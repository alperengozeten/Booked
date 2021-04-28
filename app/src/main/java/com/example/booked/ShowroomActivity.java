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
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.booked.models.Book;
import com.example.booked.models.Post;
import com.example.booked.models.Showroom;
import com.example.booked.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showroom);


        db = FirebaseFirestore.getInstance();

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.showroomPostList);

        recyclerView.setHasFixedSize(true);

        // Set the layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        posts = new ArrayList<>();


        db.collection("posts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if ( task.isSuccessful() ) {
                    for (DocumentSnapshot document : task.getResult() ) {
                        HashMap<String, Object> hashBook = (HashMap<String, Object>) document.get("book");
                        Book postBook = new Book( (String) hashBook.get("bookName"), (String) hashBook.get("picture"), (String) hashBook.get("id"));

                        HashMap<String, Object> hashUser = (HashMap<String, Object>) document.get("user");
                        User postUser = new User((String) hashUser.get("name"), (String) hashUser.get("email"), (String) hashUser.get("avatar"), (ArrayList<String>) hashUser.get("socialMedia"),
                                (String) hashUser.get("phoneNumber"), (String) hashUser.get("university"), (boolean) hashUser.get("notifications"),
                                (boolean) hashUser.get("banned"), (ArrayList<Book>) hashUser.get("wishlist"));

                        Post postToBeAdded = new Post(document.getString("title"), document.getString("description"), document.getString("university"),
                                document.getString("course"), document.getLong("price").intValue(), document.getString("picture"), postBook,
                                postUser, document.getString("id"));
                        posts.add(postToBeAdded);
                    }
                    Toast.makeText(ShowroomActivity.this, String.valueOf(posts.size()), Toast.LENGTH_SHORT).show();

                    mAdapter = new MyShowroomPostAdapter( showroom, ShowroomActivity.this);
                    recyclerView.setAdapter(mAdapter);

                    searchView = (SearchView) findViewById(R.id.showroomSearchView);
                    searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    searchView.setIconifiedByDefault(false);

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


        // TO BE REPLACED WITH THE DATABASE
        User currentUser = new User("Alperen", "alperengozeten@gmail.com", "None", "05392472224", "Bilkent University");
        User secondUser = new User("Batın", "alperengozeten@gmail.com", "None", "05392472224", "ODTU University");
        Book book = new Book("Introduction to Python", "No pic");

        showroom = new Showroom(posts);

        ArrayList<String> names = new ArrayList<>();
        names.add("Alperen");
        names.add("Alpozo");
        names.add("Alponzo");
        names.add("Safa");
        names.add("LOLO");

        menuImageBtn = (ImageButton) findViewById(R.id.menuImageBtn);
        showroomAddPostBtn = (ImageButton) findViewById(R.id.showroomAddPostBtn);

        priceLowToHighBtn = (Button) findViewById(R.id.priceLowToHighBtn);
        priceHighToLowBtn = (Button) findViewById(R.id.priceHighToLowBtn);
        aToZBtn = (Button) findViewById(R.id.aToZBtn);
        zToABtn = (Button) findViewById(R.id.zToABtn);

        menuImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMenuDialog();
            }
        });

        showroomAddPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                Intent intent = new Intent(getApplicationContext(),AddPost2.class);
                startActivity(intent);
            }
        });

        aToZBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MyShowroomPostAdapter) mAdapter).sort(v);
            }
        });

        zToABtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText( getApplicationContext(),showroom.getPosts().toString(), Toast.LENGTH_LONG).show();
                ((MyShowroomPostAdapter) mAdapter).sort(v);
            }
        });

        priceLowToHighBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MyShowroomPostAdapter) mAdapter).sort(v);
            }
        });

        priceHighToLowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MyShowroomPostAdapter) mAdapter).sort(v);
            }
        });
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

    public void openMenuDialog() {
        ShowroomMenuDialog menuDialog = new ShowroomMenuDialog();
        menuDialog.show(getSupportFragmentManager(),"menu dialog");
    }

    @Override
    public void applyTexts(String filteredUniversity, String filteredCourse, String firstPrice, String secondPrice) {
        //Toast.makeText(this,"First price is " + firstPrice +" Second Price is " + secondPrice + "Filtered Uni: " +filteredUniversity + " Filtered Course:" + filteredCourse, Toast.LENGTH_SHORT).show();
        int intFirstPrice = Integer.parseInt(firstPrice);
        int intSecondPrice = Integer.parseInt(secondPrice);

        ((MyShowroomPostAdapter) mAdapter).filter(filteredUniversity, filteredCourse, intFirstPrice, intSecondPrice);
    }

    public void resetFilters() {
        ((MyShowroomPostAdapter) mAdapter).resetFilters();
    }
}