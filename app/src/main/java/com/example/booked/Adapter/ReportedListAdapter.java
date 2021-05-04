package com.example.booked.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.example.booked.Booked;
import com.example.booked.PostPage;
import com.example.booked.R;
import com.example.booked.models.Post;
import com.example.booked.models.Report;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

/**
 * Adapter for reportedposts page's recyclerview
 */
public class ReportedListAdapter extends RecyclerView.Adapter<ReportedListAdapter.ReportHolder> {

    ArrayList<Post> reportedPosts;

    /**
     * This constructor initializes reportedPost list.
     * @param reportedPosts
     * */
    public ReportedListAdapter(ArrayList<Post> reportedPosts)
    {
        this.reportedPosts = reportedPosts;
    }

    /**
     * This is an inner class whose objects holds reference to the gui elements
     */
    public class ReportHolder extends RecyclerView.ViewHolder{

        TextView postTitle, reportsNum;
        ImageButton deleteBtn;
        ImageView postImage;
        ConstraintLayout parentLayout;

        public ReportHolder(@NonNull View itemView) {
            super(itemView);
            postTitle = itemView.findViewById(R.id.reportedListPostText);
            reportsNum = itemView.findViewById(R.id.reportedList_reportNum);
            deleteBtn = itemView.findViewById(R.id.reportedDeleteBtn);
            parentLayout = itemView.findViewById(R.id.reported_parent_layout);
            postImage = itemView.findViewById(R.id.reportedListImage);
        }
    }

    /**
     * This method creates ReportHolder object which holds references to the gui (view) elements
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ReportHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_single_reported_post,parent,false);
        return new ReportHolder(view);
    }

    /**
     * This method is called when binding rows (elements)
     * @param holder
     * @param position
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ReportHolder holder, int position) {
        holder.postTitle.setText(reportedPosts.get(position).getTitle());

        //calculates how many time the post is reported and for what type of complain
        int[] reportTypes = new int[4];

        //int i= 0; i <
        for (Report r : reportedPosts.get(position).getReports())
        {
            reportTypes[r.getCategory()] ++;
        }

        // shows how many time the post is reported and for what type of complain
        holder.reportsNum.setText("Reports: \nInappropriate Content: " + reportTypes[0] +
                "\nSpam or Misleading Content: " + reportTypes[1] +
                "\nIrrelevant Price: " + reportTypes[2] +
                "\nHateful or Abusive Content: " + reportTypes[3]);

        //post image
        Picasso.get().load(reportedPosts.get(position).getPicture()).fit().into(holder.postImage);


        //delete button delete this post from database
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Booked.deletePost(reportedPosts.get(position));
                reportedPosts.remove(position);
                notifyDataSetChanged();

            }
        });

        //on click listener for parent, it will open New PostPage whose post is reported
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Booked.setCurrentPost(reportedPosts.get(position));
                Intent intent = new Intent(holder.deleteBtn.getContext(), PostPage.class);
                startActivity(holder.deleteBtn.getContext(),intent,null);

            }
        });

    }

    /**
     * This method returns the number of elements(rows)
     * @return
     */
    @Override
    public int getItemCount() {
        return reportedPosts.size();
    }




}
