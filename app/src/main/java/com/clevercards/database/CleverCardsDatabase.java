package com.clevercards.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.clevercards.MainActivity;
import com.clevercards.database.dao.CourseDao;
import com.clevercards.database.dao.FlashcardDao;
import com.clevercards.database.dao.UserDao;
import com.clevercards.entities.Course;
import com.clevercards.entities.Flashcard;
import com.clevercards.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Name: Morgan Beebe
 * Date: 2025-12-06
 * Explanation: This is my class for the room database for CleverCards(TM)
 */
@Database(
        entities = {
                User.class, Course.class, Flashcard.class
        },
        version=2,
        exportSchema=false
)
public abstract class CleverCardsDatabase extends RoomDatabase{

    /** Constant Fields */
    public static final String USER_TABLE = "userTable";
    private static final String DATABASE_NAME = "CleverCardsDatabase";
    public static final String COURSE_TABLE = "courseTable";
    public static final String FLASHCARD_TABLE = "flashcardTable";

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    //DAO Getters
    public abstract UserDao userDao();
    public abstract CourseDao courseDao();
    public abstract FlashcardDao flashcardDao();

    //Singleton Instance

    private static volatile CleverCardsDatabase INSTANCE;
    public static CleverCardsDatabase getInstance(final Context context){
        if(INSTANCE == null){
            synchronized (CleverCardsDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CleverCardsDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration().addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            Log.i(MainActivity.TAG, "DATABASE CREATED!");
            databaseWriteExecutor.execute(() -> {
                UserDao dao = INSTANCE.userDao();
                dao.deleteAll();
                User admin = new User("admin1", "admin1", true);
                admin.setAdmin(true);
                dao.insertUser(admin);
                User testUser1 = new User("user1","user1", false);
                dao.insertUser(testUser1);
            });
        }
    };
}
