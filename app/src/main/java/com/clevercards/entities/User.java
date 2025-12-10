package com.clevercards.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.clevercards.database.CleverCardsDatabase;

import java.util.Objects;

/**
 * Name: Morgan Beebe
 * Date: 2025-12-06
 * Explanation: user entity for the room database
 */

@Entity(tableName = CleverCardsDatabase.USER_TABLE)
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return userId == user.userId && isAdmin == user.isAdmin && Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, password, isAdmin);
    }
}
