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

public class AddPost2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText enterTitleEditText;
    private EditText setPriceEditText;
    private EditText addDescriptionEditText;

    private Spinner selectUniversitySpinner;
    private Spinner selectCourseSpinner;

    private Context parentContext;

    private Button submitPostBtn;

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
        setContentView(R.layout.activity_add_post2);

        enterTitleEditText = (EditText) findViewById(R.id.enterTitleEditText);
        setPriceEditText = (EditText) findViewById(R.id.enterPriceEditText);
        addDescriptionEditText = (EditText) findViewById(R.id.addDescriptionEditText);

        selectUniversitySpinner = (Spinner) findViewById(R.id.selectUniversitySpinner);
        selectCourseSpinner = (Spinner) findViewById(R.id.selectCourseSpinner);

        submitPostBtn = (Button) findViewById(R.id.submitPostBtn);

        ArrayAdapter<CharSequence> universityAdapter = ArrayAdapter.createFromResource(parentContext,R.array.universities, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> courseAdapter = ArrayAdapter.createFromResource(parentContext,R.array.courses, android.R.layout.simple_spinner_item);

        universityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        selectUniversitySpinner.setAdapter(universityAdapter);
        selectCourseSpinner.setAdapter(courseAdapter);

        selectUniversitySpinner.setOnItemSelectedListener( (AdapterView.OnItemSelectedListener) this);
        selectCourseSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        submitPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPost();
                Intent intent = new Intent( getApplicationContext(), MyPosts.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if ( parent.getId() == selectUniversitySpinner.getId() ) {
            selectedUniversity = selectUniversitySpinner.getItemAtPosition(position).toString();
        }
        else if ( parent.getId() == selectCourseSpinner.getId() ) {
            selectedUniversity = selectCourseSpinner.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void createPost(){



    }
}