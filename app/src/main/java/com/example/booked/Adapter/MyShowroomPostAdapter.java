package com.example.booked.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booked.Booked;
import com.example.booked.PostPage;
import com.example.booked.R;
import com.example.booked.models.Post;
import com.example.booked.models.Showroom;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * This is the adapter class for the recycler view in the Showroom page
 */
public class MyShowroomPostAdapter extends RecyclerView.Adapter<MyShowroomPostAdapter.ShowroomPostViewHolder> implements Filterable {

    // Properties
    private ArrayList<Post> posts;
    private ArrayList<Post> postsFull;
    private Context context;
    private Showroom showroom;

    /**
     * This constructor takes a showroom object and also a reference to ShowroomActivity as a Context object
     * @param showroom
     * @param context
     */
    public MyShowroomPostAdapter(Showroom showroom, Context context) {
        this.showroom = showroom;
        this.posts = this.showroom.getPosts();
        postsFull = new ArrayList<>(posts);
        this.context = context;
    }

    /**
     * This method updates the data by adding a list of posts
     * @param newPosts
     */
    public void updateData( ArrayList<Post> newPosts) {
        posts.clear();
        posts.addAll(newPosts);
    }

    /**
     * This method takes a view as parameter so that we can understand where the method call came from (i.e. aToZ, ZtoA, and etc.)
     * @param view
     */
    public void sort( View view) {
        Showroom filteredShowroom = showroom;

        // Check the id and then apply the related sorting
        if ( view.getId() == R.id.aToZBtn ) {
            filteredShowroom.sortByLetter(true);
        }

        else if ( view.getId() == R.id.zToABtn ) {
            filteredShowroom.sortByLetter(false);
        }

        else if ( view.getId() == R.id.priceLowToHighBtn ) {
            filteredShowroom.sortByPrice(true);
        }

        else if ( view.getId() == R.id.priceHighToLowBtn ) {
            filteredShowroom.sortByPrice(false);
        }

        // Create a new posts arraylist and call notifyDataSetChanged() method
        posts = new ArrayList<>(filteredShowroom.getPosts());
        notifyDataSetChanged();
    }

    /**
     * This method takes university, course, price, second price information as parameters and applies them to the showroom
     * @param filteredUniversity
     * @param filteredCourse
     * @param firstPrice
     * @param secondPrice
     */
    public void filter(String filteredUniversity, String filteredCourse, int firstPrice, int secondPrice) {
        Showroom filteredShowroom = showroom;

        // Apply the filters
        filteredShowroom = filteredShowroom.filterByUniversity(filteredUniversity).filterByCourse(filteredCourse).filterByPrice(firstPrice,secondPrice);

        // Create a new posts arraylist and call notifyDataSetChanged() method
        posts = new ArrayList<>(filteredShowroom.getPosts());
        notifyDataSetChanged();
    }

    /**
     * This method resets the filtering or sorting changes
     */
    public void resetFilters() {
        posts = postsFull;
        notifyDataSetChanged();
    }

    /**
     * This method creates a ShowroomPostViewHolder object which holds references to the gui (view) elements
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ShowroomPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the view from the layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_showroom_post,parent,false);

        ShowroomPostViewHolder holder = new ShowroomPostViewHolder(view);

        return holder;
    }

    /**
     * This method is called when binding rows (elements)
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ShowroomPostViewHolder holder, int position) {
        // Set the TextViews with related information
        holder.showroomPostDescriptionTextView.setText(posts.get(position).getTitle());
        holder.showroomPostPriceTextView.setText(String.valueOf(posts.get(position).getPrice()) + " ₺");
        holder.showroomPostSellerTextView.setText(posts.get(position).getSeller().getName());

        // If the post has a picture, then load it
        if ( showroom.getPosts().get(position).getPicture() != "" ) {
            Picasso.get().load(showroom.getPosts().get(position).getPicture()).fit().centerCrop().into(holder.showroomPostPictureImageView);
        }

        // When the item is clicked, it will lead to the Post page of that post
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                Intent intent = new Intent(context, PostPage.class);
                Booked.setCurrentPost(posts.get(position));
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
     * This method is from Filterable interface and returns a Filter object
     * @return
     */
    @Override
    public Filter getFilter() {
        return showroomFilter;
    }

    // Filter class implementation
    private Filter showroomFilter = new Filter() {

        /**
         * This method filters the results with a given CharSequence as parameter
         * @param constraint
         * @return
         */
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Post> filteredList = new ArrayList<>();

            if ( constraint == null || constraint.length() == 0 ) {
                filteredList.addAll(postsFull);
            }
            else {
                // Get rid of unnecessary part
                String filterPattern = constraint.toString().toLowerCase().trim();

                // Check all the post titles
                for ( Post aPost: posts ) {
                    if ( aPost.getTitle().toLowerCase().contains(filterPattern) ) {
                        filteredList.add(aPost);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        /**
         * This method helps us to publish the results in the ShowroomActivity page
         * @param constraint
         * @param results
         */
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            posts.clear();
            posts.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

    /**
     * This is an inner class that holds references to the views (gui)
     */
    public class ShowroomPostViewHolder extends RecyclerView.ViewHolder {
        ImageView showroomPostPictureImageView;
        TextView showroomPostDescriptionTextView;
        TextView showroomPostPriceTextView;
        TextView showroomPostSellerTextView;
        ConstraintLayout parentLayout;


        public ShowroomPostViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize the elements
            showroomPostPictureImageView = (ImageView) itemView.findViewById(R.id.showroomPostPictureImageView);
            showroomPostDescriptionTextView = (TextView) itemView.findViewById(R.id.showroomPostDescriptionTextView);
            showroomPostPriceTextView = (TextView) itemView.findViewById(R.id.showroomPostPriceTextView);
            showroomPostSellerTextView = (TextView) itemView.findViewById(R.id.showroomPostSellerTextView);
            parentLayout = (ConstraintLayout) itemView.findViewById(R.id.showroomPostLayout);
        }
    }
}
