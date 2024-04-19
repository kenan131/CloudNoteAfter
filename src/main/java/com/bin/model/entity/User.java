package com.bin.model.entity;

import lombok.Data;

@Data
public class User {
    private int id;
    private String account;
    private String password;
    private String email;
    private String salt;
    private int isAdmin;
    private int active;

    public User(String account, String password, String email, String salt, int isAdmin,int active) {
        this.account = account;
        this.password = password;
        this.email = email;
        this.salt = salt;
        this.isAdmin = isAdmin;
        this.active=active;
    }
    public User(){

    }
}
