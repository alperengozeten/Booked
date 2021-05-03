package com.example.booked;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

/**
 * This dialog class  pops up when the user clicks give feedback button in settings page
 *
 * @author NoExpection
 * @version 2021 Spring
 */
public class FeedbackDialog extends AppCompatDialogFragment {
    private EditText feedbackEditText;

    private Context context;

    private FirebaseFirestore db;

    private FirebaseAuth mAuth;

    private String username;

    public FeedbackDialog(Context context, String username) {
        this.context = context;
        this.username = username;
    }

    /**
     * This method creates the dialog and its properties such as submit, close button and feedback editText
     * @param savedInstanceState
     * @return is the created dialog
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Inflating the layout for the dialog
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_feedback_dialog, null);

        //Title for builder
        builder.setView(view);
        builder.setTitle("Feedback");

        //Initializing database and editTexts
        db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        feedbackEditText =  (EditText) view.findViewById(R.id.feedbackEditText);

        //Negative button to close the feedback dialog
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        //Positive button to submit the feedback
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if ( feedbackEditText.getText() == null || feedbackEditText.getText().equals("")) {
                    Toast.makeText(context, "Please enter a feedback", Toast.LENGTH_SHORT).show();
                }
                else {
                    // Create HashMap to save the feedbacks
                    HashMap<String,Object> newData = new HashMap<>();
                    newData.put("feedback", (String) feedbackEditText.getText().toString());
                    newData.put("username", (String) username);
                    db.collection("feedbacks").document(mAuth.getCurrentUser().getUid()).set(newData)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(context, "Feedback uploaded", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }


        });


        return builder.create();
    }
}
