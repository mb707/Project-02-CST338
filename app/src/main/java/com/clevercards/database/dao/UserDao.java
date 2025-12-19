package com.clevercards.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.OnConflictStrategy;
import androidx.room.Insert;
import androidx.room.Query;

import com.clevercards.database.CleverCardsDatabase;
import com.clevercards.entities.User;

import java.util.List;

/**
 * Name: Morgan Beebe and Ashley Wozow
 * Date: 2025-12-06
 * Explanation: interface for the user dao
 */

@Dao
public interface UserDao {
    //add a new user
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User... user);

    // deletes a specific user
    @Delete
    void delete(User user);

    // deletes all users
    @Query("DELETE from " + CleverCardsDatabase.USER_TABLE)
    void deleteAll();

    //gets a list of all users for admin
    @Query("SELECT * from "+ CleverCardsDatabase.USER_TABLE + " WHERE username == :username")
    LiveData<User> getUsersByUserName(String username);

    // gets a live data list of all users
    @Query("SELECT * FROM " + CleverCardsDatabase.USER_TABLE + " ORDER BY username")
    LiveData<List<User>> getAllUsers();

    //get a user based on their ID
    @Query("SELECT * from "+ CleverCardsDatabase.USER_TABLE + " WHERE userId == :userId")
    LiveData<User>  getUserById(int userId);
}
