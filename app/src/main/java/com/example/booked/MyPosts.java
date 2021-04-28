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
import android.widget.Toast;

import com.example.booked.models.Book;
import com.example.booked.models.Post;
import com.example.booked.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class MyPosts extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private User currentUser;

    private FirebaseFirestore db;

    private ArrayList<Post> posts;

    private ImageButton myPostsAddPostBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);

        //getSupportActionBar().setTitle("My Posts");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = FirebaseFirestore.getInstance();

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
        User currentUser = Booked.getCurrentUser();

        posts = new ArrayList<>();
        db.collection("posts").whereEqualTo("username", currentUser.getName()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if ( task.isSuccessful() ) {
                    for ( DocumentSnapshot document : task.getResult()) {
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
                    Toast.makeText(MyPosts.this, String.valueOf(posts.size()), Toast.LENGTH_SHORT).show();
                    mAdapter = new MyPostAdapter(posts, MyPosts.this);
                    recyclerView.setAdapter(mAdapter);
                }
            }
        });

        /*
        ArrayList<String> names = new ArrayList<>();
        names.add("Alperen");
        names.add("Alpozo");
        names.add("Alponzo");
         */
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