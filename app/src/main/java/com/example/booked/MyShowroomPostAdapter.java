package com.example.booked;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

public class MyShowroomPostAdapter extends RecyclerView.Adapter<MyShowroomPostAdapter.ShowroomPostViewHolder> implements Filterable {

    private ArrayList<String> names;
    private ArrayList<String> namesFull;
    private Context context;

    public MyShowroomPostAdapter(ArrayList<String> names, Context context) {
        this.names = names;
        namesFull = new ArrayList<>(names);
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
                Intent intent = new Intent(context, PostActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    @Override
    public Filter getFilter() {
        return showroomFilter;
    }

    private Filter showroomFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<String> filteredList = new ArrayList<>();

            if ( constraint == null || constraint.length() == 0 ) {
                filteredList.addAll(namesFull);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for ( String name: namesFull ) {
                    if ( name.toLowerCase().contains(filterPattern) ) {
                        filteredList.add(name);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            names.clear();
            names.addAll((ArrayList) results.values);
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
