package com.clevercards.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.clevercards.database.dao.CourseDao;
import com.clevercards.database.dao.FlashcardDao;
import com.clevercards.database.dao.UserDao;
import com.clevercards.entities.Course;
import com.clevercards.entities.Flashcard;
import com.clevercards.entities.User;



/**
 * Name: Morgan Beebe
 * Date: 2025-12-06
 * Explanation: This is my class for the room database for CleverCards(TM)
 */
@Database(
        entities = {
                User.class, Course.class, Flashcard.class
        },
        version=1,
        exportSchema=false
)
public abstract class AppDatabase extends RoomDatabase{
    //DAO Getters
    public abstract UserDao userDao();
    public abstract CourseDao courseDao();
    public abstract FlashcardDao flashcardDao();

    //Singleton Instance

    private static volatile AppDatabase INSTANCE;
    public static AppDatabase getInstance(Context context){
        if(INSTANCE == null){
            synchronized (AppDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "clevercards_db")
                            .fallbackToDestructiveMigrationOnDowngrade()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
