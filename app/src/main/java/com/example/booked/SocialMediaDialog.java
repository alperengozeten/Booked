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

public class SocialMediaDialog extends AppCompatDialogFragment {

    String socialMedia;

    public SocialMediaDialog(String socialMedia) {
        this.socialMedia = socialMedia;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_social_media_dialog,null);

        builder.setView(view);
        builder.setTitle("Contact Info");
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }
        );

        TextView socialMediaInfo = (TextView) view.findViewById(R.id.socialMediaInfo);
        socialMediaInfo.setText(socialMedia);

        return builder.create();
    }
}
