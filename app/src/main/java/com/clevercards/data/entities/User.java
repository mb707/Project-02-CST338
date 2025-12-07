package com.clevercards.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Name: Morgan Beebe
 * Date: 2025-12-06
 * Explanation: user entity for the room database
 */

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private int userId;

    private String username;
    private String password;
    private boolean isAdmin;


    //constructor

    public User (String username, String password, boolean isAdmin){
        this.username=username;
        this.password=password;
        this.isAdmin=isAdmin;

    }

    //getters and setters


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
