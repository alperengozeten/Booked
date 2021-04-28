package com.example.booked;

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

import com.example.booked.models.Post;
import com.example.booked.models.Showroom;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyShowroomPostAdapter extends RecyclerView.Adapter<MyShowroomPostAdapter.ShowroomPostViewHolder> implements Filterable {

    //private ArrayList<String> name;
    //private ArrayList<String> nameFull;

    private ArrayList<Post> posts;
    private ArrayList<Post> postsFull;
    private Context context;
    private Showroom showroom;

    public MyShowroomPostAdapter(Showroom showroom, Context context) {
        this.showroom = showroom;
        this.posts = this.showroom.getPosts();
        postsFull = new ArrayList<>(posts);
        this.context = context;
    }

    public void updateData( ArrayList<Post> newPosts) {
        posts.clear();
        posts.addAll(newPosts);
        Log.e("CHECK4", String.valueOf(newPosts.size()));
        Log.e("CHECK", String.valueOf(posts.size()));
        Log.e("CHECK2", String.valueOf(newPosts.size()));

    }

    public void sort( View view) {
        Showroom filteredShowroom = showroom;

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

        posts = new ArrayList<>(filteredShowroom.getPosts());
        notifyDataSetChanged();
    }

    public void filter(String filteredUniversity, String filteredCourse, int firstPrice, int secondPrice) {
        Showroom filteredShowroom = showroom;

        Log.e("CHECK", "Uni:" + filteredUniversity);
        filteredShowroom = filteredShowroom.filterByUniversity(filteredUniversity).filterByCourse(filteredCourse).filterByPrice(firstPrice,secondPrice);

        posts = new ArrayList<>(filteredShowroom.getPosts());
        notifyDataSetChanged();
    }

    public void resetFilters() {
        posts = postsFull;
        notifyDataSetChanged();
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
        holder.showroomPostDescriptionTextView.setText(posts.get(position).getTitle());
        holder.showroomPostPriceTextView.setText(String.valueOf(posts.get(position).getPrice()) + " ₺");
        holder.showroomPostSellerTextView.setText(posts.get(position).getSeller().getName());

        if ( showroom.getPosts().get(position).getPicture() != "" ) {
            Picasso.get().load(showroom.getPosts().get(position).getPicture()).fit().centerCrop().into(holder.showroomPostPictureImageView);
        }

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

    @Override
    public int getItemCount() {
        return posts.size();
    }

    @Override
    public Filter getFilter() {
        return showroomFilter;
    }

    private Filter showroomFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Post> filteredList = new ArrayList<>();

            if ( constraint == null || constraint.length() == 0 ) {
                filteredList.addAll(postsFull);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();

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

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            posts.clear();
            posts.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

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
