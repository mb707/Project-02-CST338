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
 * Name: Morgan Beebe
 * Date: 2025-12-06
 * Explanation: interface for the user dao
 */

@Dao
public interface UserDao {
    //add a new user
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User... user);

    @Delete
    void delete(User user);

    @Query("DELETE from " + CleverCardsDatabase.USER_TABLE)
    void deleteAll();

    //sign in to match username and password
//    @Query("SELECT * FROM User WHERE username = :username AND password = :password LIMIT 1")
//    User signin(String username, String password); THIS MAY NOT BE NEEDED

    //gets a list of all users for admin (maybe include, or save for future)
    @Query("SELECT * from "+ CleverCardsDatabase.USER_TABLE + " WHERE username == :username")
    LiveData<List<User>> getUsersByUserName(String username);

    @Query("SELECT * FROM " + CleverCardsDatabase.USER_TABLE + " ORDER BY username")
    LiveData<List<User>> getAllUsers();

    //get a user based on their ID
    @Query("SELECT * from "+ CleverCardsDatabase.USER_TABLE + " WHERE userId == :userId")
    User getUserById(int userId);
}
