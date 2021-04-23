package com.example.booked;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ReportDialog extends DialogFragment {


    int position = 0;

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

        String reports[] = {"Inapropriate Content", "Spam or Misleading Content", "Irrelevant Price", "Hateful or Abusive Content"};

        dialogbuilder.setTitle("Why do you report this post?");



        dialogbuilder.setSingleChoiceItems(reports, position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                position = which;
            }
        });

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
