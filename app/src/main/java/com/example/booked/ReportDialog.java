package com.example.booked;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * This class refers to report dialog when user click on report post button.
 * */
public class ReportDialog extends DialogFragment {


    int position = 0;

    /**
     * This interface is used in order to listen positive and negative click on report dialog.
     */
    public interface ReportypeListener{
        void positiveClicked(String[] reportTypes, int position );
        void negativeClicked();
    }

    ReportypeListener myListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myListener = (ReportypeListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(getActivity());

        //report types
        String reports[] = {"Inappropriate Content", "Spam or Misleading Content", "Irrelevant Price", "Hateful or Abusive Content"};

        //title
        dialogbuilder.setTitle("Why do you report this post?");


        // report options users can choose
        dialogbuilder.setSingleChoiceItems(reports, position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                position = which;
            }
        });

        //submit and cancel buttons
        dialogbuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myListener.negativeClicked();
            }
        });
        dialogbuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myListener.positiveClicked(reports, position);
            }
        });

        return dialogbuilder.create();
    }
}
