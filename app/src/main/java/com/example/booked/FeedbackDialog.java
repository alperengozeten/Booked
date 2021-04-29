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

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_feedback_dialog, null);

        db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();

        builder.setView(view);
        builder.setTitle("Feedback");

        feedbackEditText =  (EditText) view.findViewById(R.id.feedbackEditText);

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if ( feedbackEditText.getText() == null || feedbackEditText.getText().equals("")) {
                    Toast.makeText(context, "Please enter a feedback", Toast.LENGTH_SHORT).show();
                }
                else {
                    // MAKE IT A MAP
                    HashMap<String,Object> newData = new HashMap<>();
                    newData.put("feedback", (String) "From User:" + username + " " + feedbackEditText.getText().toString());
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
