package com.example.booked.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booked.Booked;
import com.example.booked.OtherUsersProfile;
import com.example.booked.PostPage;
import com.example.booked.R;
import com.example.booked.models.BookProfile;
import com.example.booked.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

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
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("usersObj").whereEqualTo(FieldPath.documentId(), myBookProfile.getOffers().get(position).getSeller().getDocumentId())
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //burada currentPost.getSeller vardı
                        for (DocumentSnapshot documentSnapshot : task.getResult() ) {

                            Booked.setCurrentSeller(documentSnapshot.toObject(User.class));

                            Intent intent = new Intent(holder.visitSeller.getContext(), OtherUsersProfile.class);
                            startActivity(holder.visitSeller.getContext(),intent,null);
                        }
                    }
                });
                // seller page e gidilecek:
               // Booked.setCurrentSeller(myBookProfile.getOffers().get(position).getSeller());
            }
        });

    }

    @Override
    public int getItemCount() {
        return myBookProfile.getOffers().size();
    }







}
