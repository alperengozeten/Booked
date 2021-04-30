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

public class ReportedListAdapter extends RecyclerView.Adapter<ReportedListAdapter.ReportHolder> {

    ArrayList<Post> reportedPosts;

    public ReportedListAdapter(ArrayList<Post> reportedPosts)
    {
        this.reportedPosts = reportedPosts;
    }


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

    @NonNull
    @Override
    public ReportHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_single_reported_post,parent,false);
        return new ReportHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ReportHolder holder, int position) {
        holder.postTitle.setText(reportedPosts.get(position).getTitle());

        int[] reportTypes = new int[4];

        //int i= 0; i <
        for (Report r : reportedPosts.get(position).getReports())
        {
            reportTypes[r.getCategory()] ++;
        }

        holder.reportsNum.setText("Reports: \nInappropriate Content: " + reportTypes[0] +
                "\nSpam or Misleading Content: " + reportTypes[1] +
                "\nIrrelevant Price: " + reportTypes[2] +
                "\nHateful or Abusive Content: " + reportTypes[3]);

        Picasso.get().load(reportedPosts.get(position).getPicture()).fit().into(holder.postImage);

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportedPosts.remove(position);
                notifyDataSetChanged();
                // databaseden de silinecek
            }
        });

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Booked.setCurrentPost(reportedPosts.get(position));
                Intent intent = new Intent(holder.deleteBtn.getContext(), PostPage.class);
                startActivity(holder.deleteBtn.getContext(),intent,null);

            }
        });

    }

    @Override
    public int getItemCount() {
        return reportedPosts.size();
    }




}
