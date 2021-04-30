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

import com.example.booked.Adapter.MyWishlistAdapter;
import com.example.booked.models.User;

public class WishlistActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //SİLİNECEK
        /**Booked.setExample();
        currentUser = Booked.getCurrentUser();

        Book b1 = new Book("Sefiller", "");
        Book b2 = new Book("Savaş ve Barış", "");
        Book b3 = new Book("Suç ve Ceza","");

        currentUser.addBookToWishlist(b1);
        currentUser.addBookToWishlist(b2);
        currentUser.addBookToWishlist(b3);*/

        currentUser = Booked.getCurrentUser();

        recyclerView = (RecyclerView) findViewById(R.id.wishList);

        recyclerView.setHasFixedSize(true);

        // Set the layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Set tbe Adapter
        mAdapter = new MyWishlistAdapter( currentUser, currentUser.getWishlist(), this);
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
}