package com.example.myapplication3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TramAnh {

    private Integer id;
    private String username;
    private String fullname;
    private String email;
    private long phone;
    private String adress;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    private  String avatar;

    public TramAnh(Integer id, String username, String fullname,String avatar ,String email, long phone, String adress) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.adress = adress;
        this.avatar = avatar;
    }

    public TramAnh(Integer id, String username, String fullname, String email, long phone, String adress) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.adress = adress;
    }

    public TramAnh(Integer id, String username) {
        this.id = id;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
