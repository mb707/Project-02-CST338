package com.clevercards.database.dao;

import androidx.room.Dao;
import androidx.room.OnConflictStrategy;
import androidx.room.Insert;
import androidx.room.Query;

import com.clevercards.entities.User;

import java.util.List;

/**
 * Name: Morgan Beebe
 * Date: 2025-12-06
 * Explanation: interface for the user dao
 */

@Dao
public interface UserDao {
    //add a new user
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);


    //sign in to match username and password
    @Query("SELECT * FROM User WHERE username = :username AND password = :password LIMIT 1")
    User signin(String username, String password);

    //gets a list of all users for admin (maybe include, or save for future)
    @Query("SELECT * FROM User")
    List<User> getAllUsers();

    //get a user based on their ID
    @Query ("SELECT * FROM User WHERE userId = :userId LIMIT 1")
    User getUserById(int userId);
}
