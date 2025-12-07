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

}
