package com.example.booked.Adapter;

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

import com.example.booked.Booked;
import com.example.booked.OtherUsersProfile;
import com.example.booked.R;
import com.example.booked.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * This is an adapter class for the recycler view in the feedback activity
 */
public class MyFeedbackAdapter extends RecyclerView.Adapter<MyFeedbackAdapter.FeedbackViewHolder> {

    private ArrayList<String> names;

    private ArrayList<String> feedbacks;

    private Context context;

    private FirebaseFirestore db;

    private User currentSeller;

    /**
     * This constructor takes the names and feedbacks arrays and also the FeedbacksActivity as context
     * @param names
     * @param feedbacks
     * @param context
     */
    public MyFeedbackAdapter(ArrayList<String> names, ArrayList<String> feedbacks, Context context) {
        this.names = names;
        this.feedbacks = feedbacks;
        this.context = context;
    }

    /**
     * This method creates and returns a FeedbackViewHolder object which holds the references to the visuals
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_feedback, parent,false);

        FeedbackViewHolder holder = new FeedbackViewHolder(view);

        // Also initialize the database instance here
        db = FirebaseFirestore.getInstance();

        return holder;
    }

    /**
     * This method is called in the creation of each part (row) in the recycler view
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        // Set the username for the username text view
        holder.feedbackUsernameTextView.setText("From: " + names.get(position));
        // Set the feedback for the feedback text view
        holder.feedbackTextView.setText("Message: " + feedbacks.get(position));

        // If the user clicks visit button, it leads to the page of that user by searching for the user's info in the database
        holder.feedbackVisitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Search for the user's info in the database
                db.collection("usersObj").whereEqualTo("name", names.get(position)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //burada currentPost.getSeller vardı
                        for (DocumentSnapshot documentSnapshot : task.getResult() ) {

                            currentSeller = documentSnapshot.toObject(User.class);
                            Toast.makeText(context, "User Pulled", Toast.LENGTH_LONG).show();

                            // Set the currentSeller for the Booked class so that we can pull this object in the OtherUsersProfile page
                            // from the booked class
                            Booked.setCurrentSeller(currentSeller);
                            Intent sellerPage = new Intent(context, OtherUsersProfile.class);
                            context.startActivity(sellerPage);
                        }
                    }
                });
            }
        });
    }

    /**
     * Returns the number of rows
     * @return
     */
    @Override
    public int getItemCount() {
        return names.size();
    }

    /**
     * This class helps to hold the references of the gui
     */
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
