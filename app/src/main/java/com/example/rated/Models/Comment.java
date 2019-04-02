package com.example.rated.Models;

import com.google.firebase.database.ServerValue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Comment {

    private String content;
    private String uid;
    private String uimg;
    private String uname;
    private String date;
    private Long timestamp;

    public Comment() {
    }

    public Comment(String content, String uid, String uimg, String uname ) {
        this.content = content;
        this.uid = uid;
        this.uimg = uimg;
        this.uname = uname;
        Calendar c = Calendar.getInstance();
        // Apr 21, 2017 at 1:17pm
        SimpleDateFormat df = new SimpleDateFormat("dd MMM,yyyy 'at' h:ma", Locale.getDefault());
        date = df.format(c.getTime());
        // negative to allow firebase to order i descending order
        timestamp = -1 * System.currentTimeMillis();
    }
    //TODO: change date format to "days ago"

    public Comment(String content, String uid, String uimg, String uname, Long timestamp) {
        this.content = content;
        this.uid = uid;
        this.uimg = uimg;
        this.uname = uname;
        this.timestamp = timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUimg() {
        return uimg;
    }

    public void setUimg(String uimg) {
        this.uimg = uimg;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

}
