package com.example.booked;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

/**
 * This dialog class pops up when one of the contact buttons is clicked in the my profile page
 */
public class SocialMediaDialog extends AppCompatDialogFragment {

    String socialMedia;

    /**
     * Constructor that takes the social media info
     * @param socialMedia
     */
    public SocialMediaDialog(String socialMedia) {
        this.socialMedia = socialMedia;
    }

    /**
     * This is the first method called when an instance of this class is created
     * @param savedInstanceState
     * @return
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Inflate the menu and create a view
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_social_media_dialog,null);

        // Set view and title
        builder.setView(view);
        builder.setTitle("Contact Info");

        // Add a negative button to close the dialog box
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }
        );

        // Set the text to the social media info
        TextView socialMediaInfo = (TextView) view.findViewById(R.id.socialMediaInfo);
        socialMediaInfo.setText(socialMedia);

        return builder.create();
    }
}
