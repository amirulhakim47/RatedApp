package com.example.rated.Activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rated.Adapters.PostDetailAdapter;
import com.example.rated.Models.Comment;
import com.example.rated.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PostDetailActivity extends AppCompatActivity{

       ImageView imgPost, imgUserPost, imgCurrentUser;
      TextView txtPostDesc, txtPostDateName, txtPostTitle,contentDetail;
      EditText editTextComment;
      Button btnAddComment;
      String PostKey;
      FirebaseAuth firebaseAuth;
      FirebaseUser firebaseUser;
      RecyclerView detailRecyclerView;
      PostDetailAdapter postDetail;
      FirebaseDatabase firebaseDatabase;
      DatabaseReference firebaseReference;
      List<Comment> commentList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        // set the status bar to transparent
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getSupportActionBar().hide();

        // ini views
        imgPost = findViewById(R.id.post_detail_img);
        imgUserPost = findViewById(R.id.post_detail_user_img);
        imgCurrentUser = findViewById(R.id.post_view_poster_img);

        txtPostTitle = findViewById(R.id.post_detail_title);
        txtPostDesc = findViewById(R.id.post_detail_desc);
        txtPostDateName = findViewById(R.id.post_detail_timestamp);

        editTextComment = findViewById(R.id.post_detail_comment);
        btnAddComment = findViewById(R.id.post_detail_add_comment);



        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        detailRecyclerView = findViewById(R.id.post_detail_rv);
        detailRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        detailRecyclerView.setHasFixedSize(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseReference = firebaseDatabase.getReference("Comment");
        firebaseReference.keepSynced(true);

        commentList = new ArrayList<>();
        postDetail = new PostDetailAdapter(PostDetailActivity.this,commentList);
        detailRecyclerView.setAdapter(postDetail);
        //.child(firebaseUser.getUid())

        firebaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data1 : dataSnapshot.getChildren()) {

                    Comment comment = data1.getValue(Comment.class);
                    /*Comment cmt = new Comment();
                    cmt.setUid(data1.getValue(Comment.class).getUid());
                    cmt.setContent(data1.getValue(Comment.class).getContent());
                    cmt.setUimg(data1.getValue(Comment.class).getUimg());*/
                    commentList.add(comment);
                    postDetail.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PostDetailActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        //add comment button listener

        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnAddComment.setVisibility(View.INVISIBLE);
                DatabaseReference commentReference = firebaseDatabase.getReference("Comment").child(PostKey).push();
                String comment_content = editTextComment.getText().toString();
                String uid = firebaseUser.getUid();
                String uname = firebaseUser.getDisplayName();
                String uimg = firebaseUser.getPhotoUrl().toString();
                Comment comment = new Comment(comment_content,uid,uimg,uname);

                commentReference.setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        showMessage("comment added");
                        editTextComment.setText("");
                        btnAddComment.setVisibility(View.VISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showMessage("fail to add comment : "+e.getMessage());
                    }
                });


            }
        });


        // now we can get post data

        String postImage = getIntent().getExtras().getString("postImage");
        Glide.with(this).load(postImage).into(imgPost);

        String postTitle = getIntent().getExtras().getString("title");
        txtPostTitle.setText(postTitle);

        String userpostImage = getIntent().getExtras().getString("userPhoto");
        Glide.with(this).load(userpostImage).into(imgUserPost);

        String postDescription = getIntent().getExtras().getString("description");
        txtPostDesc.setText(postDescription);

        String postRating = getIntent().getExtras().getString("rating");
        txtPostDateName.setText(postRating);

        /*String postContent = getIntent().getExtras().getString("content");
        contentDetail.setText(postContent);*/

        // setcomment user image

        Glide.with(this).load(firebaseUser.getPhotoUrl()).into(imgCurrentUser);

        // get post id
        PostKey = getIntent().getExtras().getString("postKey");


        //fix timestamp in 'post' child

        /*String date = timestampToString(getIntent().getExtras().getLong("postDate"));
        txtPostDateName.setText(date);*/


    }


   /* private void iniViews() {

        *//*firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDetail = firebaseDatabase.getReference("Comment");

        detailRecyclerView = findViewById(R.id.post_detail_rv);
        detailRecyclerView.setHasFixedSize(true);
        detailRecyclerView.setLayoutManager(new LinearLayoutManager(this));*//*

       // firebaseDetail = FirebaseDatabase.getInstance().getReference("Comment").child(PostKey);
        String uid = firebaseUser.getUid();

        DatabaseReference commentRef = firebaseDatabase.getReference("Comment");
        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                CommentList = new ArrayList<>();
                  for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    Comment comment = dataSnapshot1.getValue(Comment.class);
                    CommentList.add(comment);
                }

                postDetail = new PostDetailAdapter(PostDetailActivity.this,CommentList);
                detailRecyclerView.setAdapter(postDetail);
                postDetail.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }*/

    private void showMessage(String message) {

        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }


    private String timestampToString(long time) {

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy", calendar).toString();
        return date;
    }

}