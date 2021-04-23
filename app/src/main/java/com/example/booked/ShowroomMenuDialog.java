package com.example.booked;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.booked.models.Showroom;


public class ShowroomMenuDialog extends AppCompatDialogFragment implements AdapterView.OnItemSelectedListener{

    private EditText firstPriceEditText;
    private EditText secondPriceEditText;

    private TextView enterUniversityTextView;
    private TextView enterCourseTextView;

    private Spinner universitySpinner;
    private Spinner courseSpinner;

    private ShowroomMenuDialogListener listener;

    private Context parentContext;

    private String filteredUniversity;
    private String filteredCourse;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_showroom_menu_dialog,null);

        builder.setView(view);
        builder.setTitle("Filter Posts");
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String firstPrice = firstPriceEditText.getText().toString();
                String secondPrice = secondPriceEditText.getText().toString();
                listener.applyTexts(filteredUniversity, filteredCourse, firstPrice, secondPrice);
            }
        });

        builder.setNeutralButton("Clear Filters", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((ShowroomActivity) listener).resetFilters();
            }
        });

        firstPriceEditText = (EditText) view.findViewById(R.id.firstPriceEditText);
        secondPriceEditText = (EditText) view.findViewById(R.id.secondPriceEditText);
        enterUniversityTextView = (TextView) view.findViewById(R.id.enterUniversityTextView);
        enterCourseTextView = (TextView) view.findViewById(R.id.enterCourseTextView);
        universitySpinner = (Spinner) view.findViewById(R.id.universitySpinner);
        courseSpinner = (Spinner) view.findViewById(R.id.courseSpinner);

        enterUniversityTextView.setText("Filter By University");
        enterCourseTextView.setText("Filter By Course");

        ArrayAdapter<CharSequence> universityAdapter = ArrayAdapter.createFromResource(parentContext,R.array.universities, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> courseAdapter = ArrayAdapter.createFromResource(parentContext,R.array.courses, android.R.layout.simple_spinner_item);

        universityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        universitySpinner.setAdapter(universityAdapter);
        courseSpinner.setAdapter(courseAdapter);

        universitySpinner.setOnItemSelectedListener( (AdapterView.OnItemSelectedListener) this);
        courseSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        parentContext = context;

        try {
            listener = (ShowroomMenuDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ShowroomMenuDialogListener");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if ( parent.getId() == universitySpinner.getId() ) {
            filteredUniversity = universitySpinner.getItemAtPosition(position).toString();
        }
        else if ( parent.getId() == courseSpinner.getId() ) {
            filteredCourse = courseSpinner.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface ShowroomMenuDialogListener {
        void applyTexts(String filteredUniversity, String filteredCourse, String firstPrice, String secondPrice);
    }
}
