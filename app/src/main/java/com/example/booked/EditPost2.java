package com.example.booked;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class EditPost2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText changeTitleEditText;
    private EditText changePriceEditText;
    private EditText changeDescriptionEditText;

    private Spinner changeUniversitySpinner;
    private Spinner changeCourseSpinner;

    private Context parentContext;

    private Button applyChangesBtn;

    private String selectedUniversity;
    private String selectedCourse;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post2);




        changeTitleEditText = (EditText) findViewById(R.id.changeTitleEditText);
        changePriceEditText = (EditText) findViewById(R.id.changePriceEditText);
        changeDescriptionEditText = (EditText) findViewById(R.id.changeDescriptionEditText);

        changeUniversitySpinner = (Spinner) findViewById(R.id.changeUniversitySpinner);
        changeCourseSpinner = (Spinner) findViewById(R.id.changeCourseSpinner);

        applyChangesBtn = (Button) findViewById(R.id.applyChangePostBtn);

        ArrayAdapter<CharSequence> universityAdapter = ArrayAdapter.createFromResource(parentContext,R.array.universities, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> courseAdapter = ArrayAdapter.createFromResource(parentContext,R.array.courses, android.R.layout.simple_spinner_item);

        universityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        changeUniversitySpinner.setAdapter(universityAdapter);
        changeCourseSpinner.setAdapter(courseAdapter);

        changeUniversitySpinner.setOnItemSelectedListener( (AdapterView.OnItemSelectedListener) this);
        changeCourseSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        applyChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyChangesPost();
                Intent intent = new Intent( getApplicationContext(), MyPosts.class);
                startActivity(intent);
            }
        });

    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if ( parent.getId() == changeUniversitySpinner.getId() ) {
            selectedUniversity = changeUniversitySpinner.getItemAtPosition(position).toString();
        }
        else if ( parent.getId() == changeCourseSpinner.getId() ) {
            selectedUniversity = changeCourseSpinner.getItemAtPosition(position).toString();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void applyChangesPost(){


    }
}