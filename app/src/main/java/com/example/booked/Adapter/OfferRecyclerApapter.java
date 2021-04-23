package com.example.booked.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booked.AddPost2;
import com.example.booked.Booked;
import com.example.booked.OtherUsersProfile;
import com.example.booked.R;
import com.example.booked.models.BookProfile;

import static androidx.core.content.ContextCompat.startActivity;


public class OfferRecyclerApapter extends RecyclerView.Adapter<OfferRecyclerApapter.OfferViewHolder> {


    BookProfile myBookProfile;

    public OfferRecyclerApapter(BookProfile profile)
    {
        myBookProfile = profile;
    }

    public class OfferViewHolder extends RecyclerView.ViewHolder{
        TextView sellerText, priceText;
        Button visitSeller;

        public OfferViewHolder(@NonNull View itemView) {
            super(itemView);
            sellerText = itemView.findViewById(R.id.offer_seller);
            priceText = itemView.findViewById(R.id.offer_price);
            visitSeller = itemView.findViewById(R.id.offer_visit);
        }
    }


    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_single,parent,false);
        return new OfferViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OfferViewHolder holder, int position) {
        holder.sellerText.setText(myBookProfile.getOffers().get(position).getSeller().getName());
        holder.priceText.setText( String.valueOf(myBookProfile.getOffers().get(position).getPrice()) + "₺");
        holder.visitSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // seller page e gidilecek:
                Booked.setCurrentSeller(myBookProfile.getOffers().get(position).getSeller());

                Intent intent = new Intent(holder.visitSeller.getContext(), OtherUsersProfile.class);
                startActivity(holder.visitSeller.getContext(),intent,null);

                //denemek için (yukarısı yazınca  silinecek)
                holder.visitSeller.setText("visited");

            }
        });

    }

    @Override
    public int getItemCount() {
        return myBookProfile.getOffers().size();
    }







}
