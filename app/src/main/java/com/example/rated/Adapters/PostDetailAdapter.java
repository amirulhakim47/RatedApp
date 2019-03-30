package com.example.rated.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rated.Activities.PostDetailActivity;
import com.example.rated.Models.Comment;
import com.example.rated.R;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostDetailAdapter extends RecyclerView.Adapter<PostDetailAdapter.MyViewHolder> {


    private Context mContext;
   private List<Comment> mData ;

    public PostDetailAdapter(Context mContext, List<Comment> mData) {
        this.mContext = mContext;
        this.mData = mData;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View row2 = LayoutInflater.from(mContext).inflate(R.layout.row_comment_item,viewGroup,false);
        return new MyViewHolder(row2);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        Glide.with(mContext).load(mData.get(position).getUimg()).into(myViewHolder.uimg);
        myViewHolder.content.setText(mData.get(position).getContent());
        myViewHolder.uname.setText(mData.get(position).getUname());
        // myViewHolder.usrTimeStamp.setText(mData.get(position).getTimestamp());
        // Glide.with(mContext).load(mData.get(position).getTimestamp()).into(myViewHolder.usrTimeStamp);


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView content,uname;
        //TextView usrTimeStamp;
        CircleImageView uimg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            content = (TextView) itemView.findViewById(R.id.comment_content);
            uname = (TextView) itemView.findViewById(R.id.comment_uname);
          //  usrTimeStamp = itemView.findViewById(R.id.row_comment_time);
            uimg = (CircleImageView) itemView.findViewById(R.id.comment_img);


            //get data

           /* Intent postDetailActivity = new Intent(mContext, PostDetailActivity.class);
            int position = getAdapterPosition();

            postDetailActivity.putExtra("content",mData.get(position).getContent());
            postDetailActivity.putExtra("uimg",mData.get(position).getUimg());
            mContext.startActivity(postDetailActivity);*/
        }

    }


    private String timestampToString(long time) {

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy", calendar).toString();
        return date;
    }


}
