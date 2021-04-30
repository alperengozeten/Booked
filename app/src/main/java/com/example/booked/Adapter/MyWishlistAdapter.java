package com.example.booked.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booked.BookProfile;
import com.example.booked.Booked;
import com.example.booked.R;
import com.example.booked.models.Book;
import com.example.booked.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyWishlistAdapter extends RecyclerView.Adapter<MyWishlistAdapter.WishlistViewHolder> {

    private ArrayList<Book> wishlist;
    private Context context;
    private User currentUser; // current usera gerek yok gibi
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MyWishlistAdapter(User currentUser, ArrayList<Book> wishlist, Context context) {
        this.wishlist = wishlist;
        this.context = context;
        this.currentUser = currentUser;
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

        Picasso.get().load(wishlist.get(position).getPicture()).fit().into(holder.wishListImageView);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                // Buraya book'u ekle

                db.collection("bookProfileObj").document(wishlist.get(position).getId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                       Booked.setCurrentBookProfile(documentSnapshot.toObject(com.example.booked.models.BookProfile.class));
                        Intent intent = new Intent(context, BookProfile.class);
                        context.startActivity(intent);
                    }
                });

            }
        });

        holder.wishListTrashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("current wishlist",Booked.getCurrentUser().getWishlist().toString());
                wishlist.remove(position);
                notifyDataSetChanged();


                saveUserDataBase(wishlist);
            }
        });
    }

    private void saveUserDataBase(ArrayList<Book> wishlist)
    {
        db.collection("usersObj").document(Booked.getCurrentUser().getDocumentId()).update("wishlist",wishlist)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context,"The Book is removed from Wishlist!",Toast.LENGTH_SHORT).show();
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
        ImageButton wishListTrashBtn;

        public WishlistViewHolder(@NonNull View itemView) {
            super(itemView);

            wishListImageView = itemView.findViewById(R.id.wishListImageView);
            wishListTextView = itemView.findViewById(R.id.wishListTextView);
            wishListTrashBtn = itemView.findViewById(R.id.wishListTrashBtn);
            parentLayout = itemView.findViewById(R.id.wishListLayout);
        }
    }
}
