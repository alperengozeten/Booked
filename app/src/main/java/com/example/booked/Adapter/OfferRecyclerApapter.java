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

/**
 * Adapter for offer rcyclerview in book profile page
 * */
public class OfferRecyclerApapter extends RecyclerView.Adapter<OfferRecyclerApapter.OfferViewHolder> {


    BookProfile myBookProfile;

    /**Constructor to initialze myBookProfile
     * @param profile
     * */
    public OfferRecyclerApapter(BookProfile profile)
    {
        myBookProfile = profile;
    }

    /**
     * This is an inner class whose objects holds reference to the gui elements
     */
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

    /**
     * This method creates aOfferViewHolder object which holds references to the gui (view) elements
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_single,parent,false);
        return new OfferViewHolder(view);
    }

    /**
     * This method is called when binding rows (elements)
     * @param holder
     * @param position
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OfferViewHolder holder, int position) {
        // set texts according to the post's properties
        holder.sellerText.setText(myBookProfile.getOffers().get(position).getSeller().getName());
        holder.priceText.setText( String.valueOf(myBookProfile.getOffers().get(position).getPrice()) + "₺");

        //onclick listener for visit button which lead user to seler's (updated) profile page
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

            }
        });

    }

    /**
     * This method returns the number of elements(rows)
     * @return
     */
    @Override
    public int getItemCount() {
        return myBookProfile.getOffers().size();
    }







}
