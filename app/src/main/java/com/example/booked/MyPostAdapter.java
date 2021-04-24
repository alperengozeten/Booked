package com.example.booked;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booked.models.Post;

import java.util.ArrayList;

public class MyPostAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<MyPostAdapter.PostViewHolder> {

    //ArrayList<String> nam;
    ArrayList<Post> posts;
    Context context;

    public MyPostAdapter(ArrayList<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_post, parent, false);

        PostViewHolder holder = new PostViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.postDescriptionTextView.setText(posts.get(position).getTitle().toString());
        holder.postPriceTextView.setText(String.valueOf(posts.get(position).getPrice()) + " ₺");

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                Booked.setCurrentPost(posts.get(position));
                Intent intent = new Intent(context, PostPage.class);
                context.startActivity(intent);
            }
        });

        holder.postEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Booked book = new Booked();
                book.setCurrentPost(posts.get(position));
                Intent intent = new Intent(context, EditPost2.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    public class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView postPictureImageView;
        TextView postDescriptionTextView;
        TextView postPriceTextView;
        ImageButton postEditBtn;
        ConstraintLayout parentLayout;


        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            postPictureImageView = itemView.findViewById(R.id.postPictureImageView);
            postDescriptionTextView = itemView.findViewById(R.id.postDescriptionTextView);
            postPriceTextView = itemView.findViewById(R.id.postPriceTextView);
            postEditBtn = itemView.findViewById(R.id.postEditBtn);
            parentLayout = itemView.findViewById(R.id.postLayout);

        }
    }
}
