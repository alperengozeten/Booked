package com.example.booked.Adapter;

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

import com.example.booked.Booked;
import com.example.booked.PostPage;
import com.example.booked.R;
import com.example.booked.models.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * This is the adapter class for the recycler view in the OtherUsersProfile page
 */
public class OtherUsersPostAdapter extends RecyclerView.Adapter<OtherUsersPostAdapter.OtherUsersPostViewHolder> {

    // Properties
    ArrayList<Post> posts;
    Context context;

    /**
     * This constructor takes a list of posts and a reference to the OtherUsersProfile activity as a Context object
     * @param posts
     * @param context
     */
    public OtherUsersPostAdapter(ArrayList<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    /**
     * This method creates a OtherUsersPostViewHolder object which holds references to the gui (view) elements
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public OtherUsersPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the view from the layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_other_users_posts,parent,false);

        OtherUsersPostViewHolder holder = new OtherUsersPostViewHolder(view);

        return holder;
    }

    /**
     * This method is called when binding rows (elements)
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull OtherUsersPostViewHolder holder, int position) {
        // Set the TextViews with related information
        holder.otherUsersPostDescriptionTextView.setText(posts.get(position).getTitle().toString());
        holder.otherUsersPostPriceTextView.setText(String.valueOf(posts.get(position).getPrice()) +" ₺");

        // Load the picture of the post
        Picasso.get().load(posts.get(position).getPicture()).fit().into(holder.otherUsersPostPictureImageView);

        // When the item is clicked, it will lead to the Post page of that post
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                Booked.setCurrentPost(posts.get(position));
                Intent intent = new Intent(context, PostPage.class);
                context.startActivity(intent);
            }
        });
    }

    /**
     * This method returns the number of elements(rows)
     * @return
     */
    @Override
    public int getItemCount() {
        return posts.size();
    }

    /**
     * This is an inner class that holds references to the views (gui)
     */
    public class OtherUsersPostViewHolder extends RecyclerView.ViewHolder {

        ImageView otherUsersPostPictureImageView;
        TextView otherUsersPostDescriptionTextView;
        TextView otherUsersPostPriceTextView;
        ConstraintLayout parentLayout;

        public OtherUsersPostViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize the gui elements
            otherUsersPostPictureImageView = (ImageView) itemView.findViewById(R.id.otherUsersPostPictureImageView);
            otherUsersPostDescriptionTextView = (TextView) itemView.findViewById(R.id.otherUsersPostDescriptionTextView);
            otherUsersPostPriceTextView = (TextView) itemView.findViewById(R.id.otherUsersPostPriceTextView);
            parentLayout = (ConstraintLayout) itemView.findViewById(R.id.otherUsersPostLayout);
        }
    }
}
