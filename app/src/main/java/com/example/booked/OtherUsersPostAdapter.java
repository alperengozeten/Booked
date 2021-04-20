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

import com.example.booked.models.Post;

import java.util.ArrayList;

public class OtherUsersPostAdapter extends RecyclerView.Adapter<OtherUsersPostAdapter.OtherUsersPostViewHolder> {

    ArrayList<Post> posts;
    Context context;

    public OtherUsersPostAdapter(ArrayList<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    @NonNull
    @Override
    public OtherUsersPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_other_users_posts,parent,false);

        OtherUsersPostViewHolder holder = new OtherUsersPostViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OtherUsersPostViewHolder holder, int position) {
        holder.otherUsersPostDescriptionTextView.setText(posts.get(position).getTitle().toString());
        holder.otherUsersPostPriceTextView.setText(String.valueOf(posts.get(position).getPrice()) +" TL");

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                Intent intent = new Intent(context, PostActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    public class OtherUsersPostViewHolder extends RecyclerView.ViewHolder {

        ImageView otherUsersPostPictureImageView;
        TextView otherUsersPostDescriptionTextView;
        TextView otherUsersPostPriceTextView;
        ConstraintLayout parentLayout;

        public OtherUsersPostViewHolder(@NonNull View itemView) {
            super(itemView);

            otherUsersPostPictureImageView = (ImageView) itemView.findViewById(R.id.otherUsersPostPictureImageView);
            otherUsersPostDescriptionTextView = (TextView) itemView.findViewById(R.id.otherUsersPostDescriptionTextView);
            otherUsersPostPriceTextView = (TextView) itemView.findViewById(R.id.otherUsersPostPriceTextView);
            parentLayout = (ConstraintLayout) itemView.findViewById(R.id.otherUsersPostLayout);
        }
    }
}
