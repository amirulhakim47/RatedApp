package com.example.rated.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rated.Activities.PostDetailActivity;
import com.example.rated.Models.Post;
import com.example.rated.R;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> implements Filterable {

    Context mContext;
    List<Post> mData;
    List<Post> mDataFiltered;


    public PostAdapter(Context mContext, List<Post> mData ) {
        this.mContext = mContext;
        this.mData = mData;
        this.mDataFiltered = mData;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View row = LayoutInflater.from(mContext).inflate(R.layout.row_post_item,parent,false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //set animation

        holder.imgPost.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));
        holder.imgPostProfile.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));
        holder.tvName.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));
        holder.tvTitle.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));


        holder.tvTitle.setText(mDataFiltered.get(position).getTitle());
        holder.tvName.setText("by " + mDataFiltered.get(position).getUserName());
        Glide.with(mContext).load(mDataFiltered.get(position).getPicture()).into(holder.imgPost);
        Glide.with(mContext).load(mDataFiltered.get(position).getUserPhoto()).into(holder.imgPostProfile);
      //  holder.rateBar.setRating(Float.parseFloat(mDataFiltered.get(position).getRating()));


    }

    @Override
    public int getItemCount() {
        return mDataFiltered.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String Key = charSequence.toString();
                if (Key.isEmpty()) {

                    mDataFiltered = mData;

                }
                else {

                    List<Post> Lstfiltered = new ArrayList<>();
                    for (Post row : mData) {

                        if (row.getTitle().toLowerCase().contains(Key.toLowerCase())){
                            Lstfiltered.add(row);
                        }
                    }

                    mDataFiltered = Lstfiltered;

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mDataFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                mDataFiltered = (List<Post>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle, tvName;
        ImageView imgPost;
        ImageView imgPostProfile;
     //   RatingBar rateBar;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.row_poster_name);
            tvTitle = itemView.findViewById(R.id.row_post_title);
            imgPost = itemView.findViewById(R.id.row_post_img);
            imgPostProfile = itemView.findViewById(R.id.row_post_profile_img);
          //  rateBar = itemView.findViewById(R.id.row_rating);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent postDetailActivity = new Intent(mContext, PostDetailActivity.class);
                    int position = getAdapterPosition();

                    postDetailActivity.putExtra("title",mData.get(position).getTitle());
                    postDetailActivity.putExtra("postImage",mData.get(position).getPicture());
                    postDetailActivity.putExtra("description",mData.get(position).getDescription());
                    postDetailActivity.putExtra("postKey",mData.get(position).getPostKey());
                    postDetailActivity.putExtra("userPhoto",mData.get(position).getUserPhoto());
                    // will fix this later i forgot to add user name to post object
                    postDetailActivity.putExtra("userName",mData.get(position).getUserName());
                    //  TODO: add timestamp in model
                    //  long timestamp  = (long) mData.get(position).getTimeStamp();
                    //  postDetailActivity.putExtra("postDate",timestamp) ;
                   // postDetailActivity.putExtra("rating",mData.get(position).getRating());

                    mContext.startActivity(postDetailActivity);
                }
            });

        }
    }
}
