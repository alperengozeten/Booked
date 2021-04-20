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
import android.widget.ImageButton;
import android.widget.SearchView;

import java.util.ArrayList;

public class ShowroomActivity extends AppCompatActivity implements ShowroomMenuDialog.ShowroomMenuDialogListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private SearchView searchView;

    private ImageButton menuImageBtn;
    private ImageButton showroomAddPostBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showroom);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_book_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.showroomPostList);

        recyclerView.setHasFixedSize(true);

        // Set the layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<String> names = new ArrayList<>();
        names.add("Alperen");
        names.add("Alpozo");
        names.add("Alponzo");
        names.add("Safa");
        names.add("LOLO");

        mAdapter = new MyShowroomPostAdapter(names, this);
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
        //Toast.makeText(this,"First price is " + firstPrice +" Second Price is " + secondPrice + "Filtered Uni: " +filteredUniversity, Toast.LENGTH_SHORT).show();
    }

}