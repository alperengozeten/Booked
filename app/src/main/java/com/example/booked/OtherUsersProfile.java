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
import android.widget.TextView;

import com.example.booked.models.Book;
import com.example.booked.models.Post;
import com.example.booked.models.User;

import java.util.ArrayList;

public class OtherUsersProfile extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private TextView otherUsersProfilePostTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_users_profile);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        otherUsersProfilePostTextView = (TextView) findViewById(R.id.otherUsersProfilePostTextView);

        recyclerView = (RecyclerView) findViewById(R.id.otherUserPostList);

        recyclerView.setHasFixedSize(true);

        // Set the layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // TO BE REPLACED WITH THE DATABASE
        User currentUser = new User("Alperen", "alperengozeten@gmail.com", "None", "05392472224", "Bilkent University");
        Book book = new Book("Introduction to Python", "No pic");

        ArrayList<Post> posts = new ArrayList<>();
        posts.add(new Post("Cs book", "In good state", "Cs-115",70,"No pic", book, currentUser));
        posts.add(new Post("Math book", "In good state", "Math-101",60,"No pic", book, currentUser));
        posts.add(new Post("Phys book", "In good state", "Phys-101",50,"No pic", book, currentUser));

        mAdapter = new OtherUsersPostAdapter(posts, this);
        recyclerView.setAdapter(mAdapter);

        otherUsersProfilePostTextView.setText(currentUser.getName().toString() + "'s Posts");
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