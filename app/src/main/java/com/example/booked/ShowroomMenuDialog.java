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

/**
 * This class represents a dialog box that pops up when menu icon in the showroom is clicked
 */
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

    /**
     * This is the first method called when an instance of this class is created
     * @param savedInstanceState
     * @return
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Inflate the layout for the menu dialog and create a view object
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_showroom_menu_dialog,null);

        // Set title
        builder.setView(view);
        builder.setTitle("Filter Posts");

        // Give a negative button to close
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        // Give a positive button to apply the changes
        builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get the prices
                String firstPrice = firstPriceEditText.getText().toString();
                String secondPrice = secondPriceEditText.getText().toString();

                // Call the Showroom activity (which is the listener) to apply filterings
                listener.applyTexts(filteredUniversity, filteredCourse, firstPrice, secondPrice);
            }
        });

        // This clears all the applied filters
        builder.setNeutralButton("Clear Filters", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((ShowroomActivity) listener).resetFilters();
            }
        });

        // Initialize all the edit texts and spinners and text views
        firstPriceEditText = (EditText) view.findViewById(R.id.firstPriceEditText);
        secondPriceEditText = (EditText) view.findViewById(R.id.secondPriceEditText);
        enterUniversityTextView = (TextView) view.findViewById(R.id.enterUniversityTextView);
        enterCourseTextView = (TextView) view.findViewById(R.id.enterCourseTextView);
        universitySpinner = (Spinner) view.findViewById(R.id.universitySpinner);
        courseSpinner = (Spinner) view.findViewById(R.id.courseSpinner);

        enterUniversityTextView.setText("Filter By University");
        enterCourseTextView.setText("Filter By Course");

        // ArrayAdapter for the spinners
        ArrayAdapter<CharSequence> universityAdapter = ArrayAdapter.createFromResource(parentContext,R.array.universities, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> courseAdapter = ArrayAdapter.createFromResource(parentContext,R.array.courses, android.R.layout.simple_spinner_item);

        // Set the drop down view resource
        universityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapters
        universitySpinner.setAdapter(universityAdapter);
        courseSpinner.setAdapter(courseAdapter);

        // Give listeners to spinners which is this class
        universitySpinner.setOnItemSelectedListener( (AdapterView.OnItemSelectedListener) this);
        courseSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        return builder.create();
    }

    /**
     * This method is called when this dialog box is called from an activity (context). This helps to hold references to that
     * activity (in this case it's showroom)
     * @param context
     */
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

    /**
     * This method is automatically called by the spinner.
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Store the selected data in filteredUniversity and filteredCourse
        if ( parent.getId() == universitySpinner.getId() ) {
            filteredUniversity = universitySpinner.getItemAtPosition(position).toString();
        }
        else if ( parent.getId() == courseSpinner.getId() ) {
            filteredCourse = courseSpinner.getItemAtPosition(position).toString();
        }
    }

    /**
     * Must be implemented
     * @param parent
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * This interface is implemented by the ShowroomActivity
     */
    public interface ShowroomMenuDialogListener {
        void applyTexts(String filteredUniversity, String filteredCourse, String firstPrice, String secondPrice);
    }
}
