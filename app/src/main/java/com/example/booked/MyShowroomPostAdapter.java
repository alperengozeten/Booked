package com.example.booked;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyShowroomPostAdapter extends RecyclerView.Adapter<MyShowroomPostAdapter.ShowroomPostViewHolder> {

    ArrayList<String> names;
    Context context;

    public MyShowroomPostAdapter(ArrayList<String> names, Context context) {
        this.names = names;
        this.context = context;
    }

    @NonNull
    @Override
    public ShowroomPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_showroom_post,parent,false);

        ShowroomPostViewHolder holder = new ShowroomPostViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowroomPostViewHolder holder, int position) {
        holder.showroomPostDescriptionTextView.setText(names.get(position));
        holder.showroomPostPriceTextView.setText("80 TL");
        holder.showroomPostSellerTextView.setText("Seller here");

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Post.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }


    public class ShowroomPostViewHolder extends RecyclerView.ViewHolder {
        ImageView showroomPostPictureImageView;
        TextView showroomPostDescriptionTextView;
        TextView showroomPostPriceTextView;
        TextView showroomPostSellerTextView;
        ConstraintLayout parentLayout;


        public ShowroomPostViewHolder(@NonNull View itemView) {
            super(itemView);

            showroomPostPictureImageView = (ImageView) itemView.findViewById(R.id.showroomPostPictureImageView);
            showroomPostDescriptionTextView = (TextView) itemView.findViewById(R.id.showroomPostDescriptionTextView);
            showroomPostPriceTextView = (TextView) itemView.findViewById(R.id.showroomPostPriceTextView);
            showroomPostSellerTextView = (TextView) itemView.findViewById(R.id.showroomPostSellerTextView);
            parentLayout = (ConstraintLayout) itemView.findViewById(R.id.showroomPostLayout);
        }
    }
}
