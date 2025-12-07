package com.clevercards.data;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.clevercards.data.dao.CourseDao;
import com.clevercards.data.dao.FlashcardDao;
import com.clevercards.data.dao.UserDao;

/**
 * Name: Morgan Beebe
 * Date: 2025-12-06
 * Explanation: This is my class for the database
 */

public class AppDatabase {
    //DAO Getters
    public abstract UserDao userDao();
    public abstract CourseDao courseDao();
    public abstract FlashcardDao flashcardDao();

    //Single Instance
    public static AppDatabase getInstance(Context context){
        if(INSTANCE == null){
            synchronized (AppDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(contaxt.getApplicationContext(), AppDatabase.class, "clevercards_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
