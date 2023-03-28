package com.dev.avyanna.models;

public class User {
    public String username, fullname, number, address, gender, bio, usertype, uid;

    public User(String username, String fullname, String number, String address, String gender, String bio, String usertype, String uid) {
        this.username = username;
        this.fullname = fullname;
        this.number = number;
        this.address = address;
        this.gender = gender;
        this.bio = bio;
        this.usertype = usertype;
        this.uid = uid;
    }
}
