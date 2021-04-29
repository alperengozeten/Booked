package com.example.booked;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booked.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MyFeedbackAdapter extends RecyclerView.Adapter<MyFeedbackAdapter.FeedbackViewHolder> {

    private ArrayList<String> names;

    private ArrayList<String> feedbacks;

    private Context context;

    private FirebaseFirestore db;

    private User currentSeller;

    public MyFeedbackAdapter(ArrayList<String> names, ArrayList<String> feedbacks, Context context) {
        this.names = names;
        this.feedbacks = feedbacks;
        this.context = context;
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_feedback, parent,false);

        FeedbackViewHolder holder = new FeedbackViewHolder(view);

        db = FirebaseFirestore.getInstance();

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        holder.feedbackUsernameTextView.setText("From: " + names.get(position));
        holder.feedbackTextView.setText("Message: " + feedbacks.get(position));

        holder.feedbackVisitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("usersObj").whereEqualTo("name", names.get(position)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //burada currentPost.getSeller vardı
                        for (DocumentSnapshot documentSnapshot : task.getResult() ) {

                            currentSeller = documentSnapshot.toObject(User.class);
                            Toast.makeText(context, "User Pulled", Toast.LENGTH_LONG).show();

                            Booked.setCurrentSeller(currentSeller);
                            Intent sellerPage = new Intent(context, OtherUsersProfile.class);
                            context.startActivity(sellerPage);
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class FeedbackViewHolder extends RecyclerView.ViewHolder {

        TextView feedbackUsernameTextView;
        TextView feedbackTextView;
        ConstraintLayout parentLayout;
        Button feedbackVisitBtn;

        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);

            feedbackUsernameTextView = (TextView) itemView.findViewById(R.id.feedbackUsernameTextView);
            feedbackTextView = (TextView) itemView.findViewById(R.id.feedbackTextView);
            parentLayout = (ConstraintLayout) itemView.findViewById(R.id.feedbackConstraintLayout);
            feedbackVisitBtn = (Button) itemView.findViewById(R.id.feedbackVisitBtn);
        }
    }
}
