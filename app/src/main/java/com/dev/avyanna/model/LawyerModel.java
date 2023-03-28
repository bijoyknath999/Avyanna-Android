package com.dev.avyanna.model;

public class LawyerModel {
    private String address = "";
    private String bio = "";
    private String fullname = "";
    private String gender = "";
    private String number = "";
    private String uid = "";
    private String username = "";
    private String usertype = "";

    public LawyerModel()
    {}
    public LawyerModel(String address, String bio, String fullname, String gender, String number, String uid, String username, String usertype) {
        this.address = address;
        this.bio = bio;
        this.fullname = fullname;
        this.gender = gender;
        this.number = number;
        this.uid = uid;
        this.username = username;
        this.usertype = usertype;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
}
