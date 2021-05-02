package com.example.booked.Adapter;

import android.app.PendingIntent;
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
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booked.Booked;
import com.example.booked.EditPost;
import com.example.booked.MyPosts;
import com.example.booked.PostPage;
import com.example.booked.R;
import com.example.booked.models.BookProfile;
import com.example.booked.models.Post;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * This is the adapter class for the recycler view in the MyPosts page
 */
public class MyPostAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<MyPostAdapter.PostViewHolder> {

    ArrayList<Post> posts;
    Context context;

    /**
     * This constructor takes list of posts and also a reference to the MyPost page as a Context object
     * @param posts
     * @param context
     */
    public MyPostAdapter(ArrayList<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    /**
     * This method creates a PostViewHolder object which holds references to the gui (view) elements
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the view from the layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_post, parent, false);

        PostViewHolder holder = new PostViewHolder(view);

        return holder;
    }

    /**
     * This method is called when binding rows (elements)
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        // Fil the textviews with the related information
        holder.postDescriptionTextView.setText(posts.get(position).getTitle().toString());
        holder.postPriceTextView.setText(String.valueOf(posts.get(position).getPrice()) + " ₺");

        // If the post has a picture, then load it to ImageView
        if ( ! posts.get(position).getPicture().equals("")  ) {
            Picasso.get().load(posts.get(position).getPicture()).fit().centerCrop().into(holder.postPictureImageView);
        }

        // If the delete button is clicked, remove it
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Delete post from the database using the deletePost() method of the Global class
                Booked.deletePost(posts.get(position));
                notifyUser();
                posts.remove(position);
                notifyDataSetChanged();
            }
        });

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

        // When the post edit button is clicked, it will direct to the EditPost page
        holder.postEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Before calling the intent, set the currentPost of the global class to the current row item
                Booked book = new Booked();
                book.setCurrentPost(posts.get(position));
                Intent intent = new Intent(context, EditPost.class);
                context.startActivity(intent);
            }
        });
    }

    /**
     * Sends a notification to user when the user deleted a post.
     */
    public void notifyUser() {
        Intent resultIntent = new Intent(context, MyPosts.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 2,
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, Booked.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Post Deleted!")
                .setContentText("Post has been deleted.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent);

        NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(context);
        mNotificationManager.notify(2, mBuilder.build());
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
    public class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView postPictureImageView;
        TextView postDescriptionTextView;
        TextView postPriceTextView;
        ImageButton postEditBtn;
        ImageButton deleteButton;
        ConstraintLayout parentLayout;


        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize the view elements
            deleteButton = itemView.findViewById(R.id.deleteMyPost);
            postPictureImageView = itemView.findViewById(R.id.postPictureImageView);
            postDescriptionTextView = itemView.findViewById(R.id.postDescriptionTextView);
            postPriceTextView = itemView.findViewById(R.id.postPriceTextView);
            postEditBtn = itemView.findViewById(R.id.postEditBtn);
            parentLayout = itemView.findViewById(R.id.postLayout);
        }
    }
}
