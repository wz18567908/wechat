package com.example.demo.entity;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class User implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private int id;
    private String userName;
    private String password;
    private long tokenStartTime;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public long getTokenStartTime() {
        return tokenStartTime;
    }
    public void setTokenStartTime(long tokenStartTime) {
        this.tokenStartTime = tokenStartTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

