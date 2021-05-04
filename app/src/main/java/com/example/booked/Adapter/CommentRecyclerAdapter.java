package com.example.booked.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booked.R;
import com.example.booked.models.BookProfile;

/**
 * This class is adapter of recyclerview in the Book Profile comments
 */
public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentRecyclerAdapter.CommentViewHolder> {

    BookProfile myBookProfile;

    /**This constructor initializes myBookProfile.
     * @param profile
     *
     * */
    public CommentRecyclerAdapter(BookProfile profile)
    {
        myBookProfile = profile;
    }


     /**
     * This is an inner class whose objects holds reference to the gui elements
     */
    public class CommentViewHolder extends RecyclerView.ViewHolder{

        TextView commentOwner,commentDescription;
        ImageView star1,star2,star3,star4,star5;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            commentDescription = itemView.findViewById(R.id.comment);
            commentOwner = itemView.findViewById(R.id.commentOwner);
            star1 = itemView.findViewById(R.id.star1);
            star2 = itemView.findViewById(R.id.star2);
            star3 = itemView.findViewById(R.id.star3);
            star4 = itemView.findViewById(R.id.star4);
            star5 = itemView.findViewById(R.id.star5);
        }
    }


    /**
     * This method creates a CommentViewHolder object which holds references to the gui (view) elements
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_single,parent,false);
        return new CommentViewHolder(view);
    }

    /**
     * This method is called when binding rows (elements)
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        // set texts according to properties of evaluation
        holder.commentOwner.setText(myBookProfile.getEvalutions().get(position).getEvaluater().getName());
        holder.commentDescription.setText(myBookProfile.getEvalutions().get(position).getComment());

        ImageView stars[] = {holder.star1,holder.star2,holder.star3,holder.star4,holder.star5};

        for(int i = 0; i < myBookProfile.getEvalutions().get(position).getRate(); i++)
        {
            stars[i].setImageResource(R.drawable.ic_action_star);
        }

    }

    /**
     * This method returns the number of elements(rows)
     * @return
     */
    @Override
    public int getItemCount() {
        return myBookProfile.getEvalutions().size();
    }


}
