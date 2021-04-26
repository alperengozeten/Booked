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

import java.util.ArrayList;

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

    private Showroom showroom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showroom);

        // denemek için, sonra silenecek
        Booked.setExample();


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.showroomPostList);

        recyclerView.setHasFixedSize(true);

        // Set the layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // TO BE REPLACED WITH THE DATABASE
        User currentUser = new User("Alperen", "alperengozeten@gmail.com", "None", "05392472224", "Bilkent University");
        User secondUser = new User("Batın", "alperengozeten@gmail.com", "None", "05392472224", "ODTU University");
        Book book = new Book("Introduction to Python", "No pic");

        ArrayList<Post> posts = new ArrayList<>();
        posts.add(new Post("Cs book", "A In good state", "Cs-115",70, 1,"No pic", book, currentUser));
        posts.add(new Post("Math book", "In good state", "Math-101",60, 2, "No pic", book, currentUser));
        posts.add(new Post("Phys book", "In good state", "Phys-101",50,3, "No pic", book, secondUser));

        showroom = new Showroom(posts);

        ArrayList<String> names = new ArrayList<>();
        names.add("Alperen");
        names.add("Alpozo");
        names.add("Alponzo");
        names.add("Safa");
        names.add("LOLO");

        mAdapter = new MyShowroomPostAdapter( showroom, this);
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