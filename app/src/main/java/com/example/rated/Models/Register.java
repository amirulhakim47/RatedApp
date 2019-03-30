package com.example.rated.Models;

public class Register {

    private String userName;
    private String userMail;

    public Register() {

    }

    public Register(String userName, String userMail) {
        this.userName = userName;
        this.userMail = userMail;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserMail() {
        return userMail;
    }
}
