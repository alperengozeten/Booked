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

import com.example.booked.models.Book;

import java.util.ArrayList;

public class MyWishlistAdapter extends RecyclerView.Adapter<MyWishlistAdapter.WishlistViewHolder> {

    private ArrayList<Book> wishlist;
    private Context context;

    public MyWishlistAdapter(ArrayList<Book> wishlist, Context context) {
        this.wishlist = wishlist;
        this.context = context;
    }

    @NonNull
    @Override
    public WishlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_wishlist, parent,false);

        WishlistViewHolder holder = new WishlistViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistViewHolder holder, int position) {
        holder.wishListTextView.setText(wishlist.get(position).getBookName());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                // Buraya book'u ekle
                Intent intent = new Intent(context, BookProfile.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wishlist.size();
    }

    public class WishlistViewHolder extends RecyclerView.ViewHolder{
        ImageView wishListImageView;
        TextView wishListTextView;
        ConstraintLayout parentLayout;

        public WishlistViewHolder(@NonNull View itemView) {
            super(itemView);

            wishListImageView = itemView.findViewById(R.id.wishListImageView);
            wishListTextView = itemView.findViewById(R.id.wishListTextView);
            parentLayout = itemView.findViewById(R.id.wishListLayout);
        }
    }
}
