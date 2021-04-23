package com.example.booked;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddEvaluationDialog extends DialogFragment {


    public interface CommentListener{
        void positiveClicked(String comment, double rate );
        //void negativeClicked();
    }

   CommentListener myListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myListener = (CommentListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.addevalution);

        RatingBar rateBar = dialog.findViewById(R.id.ratingBar);
        rateBar.setRating(5);
        rateBar.setStepSize(1);
        EditText comment = dialog.findViewById(R.id.commentwrite);
        Button save = dialog.findViewById(R.id.savecomment);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListener.positiveClicked( comment.getText().toString(), rateBar.getRating() );
                dialog.dismiss();
            }
        });

        return dialog;
    }
}
