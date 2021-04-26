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
import android.widget.ImageButton;

import com.example.booked.models.Book;
import com.example.booked.models.Post;
import com.example.booked.models.User;

import java.util.ArrayList;

public class MyPosts extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private User currentUser;

    private ImageButton myPostsAddPostBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);

        //getSupportActionBar().setTitle("My Posts");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myPostsAddPostBtn = (ImageButton) findViewById(R.id.myPostsAddPostBtn);
        myPostsAddPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddPost2.class);
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.postList);

        recyclerView.setHasFixedSize(true);

        // Set the layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // TO BE REPLACED WITH THE DATABASE
        User currentUser = new User("Alperen", "alperengozeten@gmail.com", "None", "05392472224", "Bilkent University");
        Book book = new Book("Introduction to Python", "No pic");

        ArrayList<Post> posts = new ArrayList<>();
        posts.add(new Post("Cs book", "In good state", "Cs-115",70, "No pic", book, currentUser));
        posts.add(new Post("Math book", "In good state", "Math-101",60, "No pic", book, currentUser));
        posts.add(new Post("Phys book", "In good state", "Phys-101",50,"No pic", book, currentUser));

        /*
        ArrayList<String> names = new ArrayList<>();
        names.add("Alperen");
        names.add("Alpozo");
        names.add("Alponzo");
         */

        mAdapter = new MyPostAdapter(posts, this);
        recyclerView.setAdapter(mAdapter);
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