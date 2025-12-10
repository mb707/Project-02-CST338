package com.clevercards.database.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.clevercards.MainActivity;
import com.clevercards.database.CleverCardsDatabase;
import com.clevercards.database.dao.CourseDao;
import com.clevercards.database.dao.FlashcardDao;
import com.clevercards.database.dao.UserDao;
import com.clevercards.entities.Course;
import com.clevercards.entities.Flashcard;
import com.clevercards.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Name: Morgan Beebe
 * Date: 2025-12-06
 * Explanation: class to control my giant data repository
 */
public class CleverCardsRepository {
    private final UserDao userDao;
    private final CourseDao courseDao;
    private final FlashcardDao flashcardDao;

    private ArrayList<Course> allCourses;
    private ArrayList<Flashcard> allFlashCards;

    private static CleverCardsRepository repository;


    //main constructor for receiving the database instance
    private CleverCardsRepository(Context context){
        CleverCardsDatabase db = CleverCardsDatabase.getInstance(context);
        this.userDao = db.userDao();
        this.courseDao = db.courseDao();
        this.flashcardDao = db.flashcardDao();
        this.allCourses = (ArrayList<Course>) this.courseDao.getAllCourses();
        this.allFlashCards = (ArrayList<Flashcard>) this.flashcardDao.getAllFlashcards();

    }

    public static CleverCardsRepository getRepository(Application application){
        if (repository != null) {
            return repository;
        }
        Future<CleverCardsRepository> future = CleverCardsDatabase.databaseWriteExecutor.submit(
                new Callable<CleverCardsRepository>() {
                    @Override
                    public CleverCardsRepository call() throws Exception {
                        return new CleverCardsRepository(application);
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d(MainActivity.TAG, "Problem getting GymLogRepository, thread error.");
        }
        return null;
    }

    //╰(*°▽°*)╯╰(*°▽°*)╯╰(*°▽°*)╯╰(*°▽°*)╯
    //ALL THE USER METHODS
    //ᓚᘏᗢ  ᓚᘏᗢ  ᓚᘏᗢ  ᓚᘏᗢ

    public void insertUser(User... user){
        userDao.insertUser(user);
    }

//    public User signin(String username, String password){
//        return userDao.signin(username, password);
//    }

    public User getUserById(int userId){
        return userDao.getUserById(userId);
    }

    public LiveData<List<User>> getAllUsers(){
        return userDao.getAllUsers();
    }

    public LiveData<List<User>> getUsersByUserName(String username){
        return userDao.getUsersByUserName(username);
    }

    //╰(*°▽°*)╯╰(*°▽°*)╯╰(*°▽°*)╯╰(*°▽°*)╯
    //ALL THE COURSE METHODS
    //ᓚᘏᗢ  ᓚᘏᗢ  ᓚᘏᗢ  ᓚᘏᗢ

    public void insertCourse(Course... course){
        courseDao.insertCourse(course);
    }

    public List<Course> getAllCourses(){
        return courseDao.getAllCourses();
    }

    public List<Course> getCoursesByUser(int userId){
        return courseDao.getCoursesByUser(userId);
    }

    public List<Course> getCourseById(int courseId){
        return courseDao.getCourseById(courseId);
    }



    //╰(*°▽°*)╯╰(*°▽°*)╯╰(*°▽°*)╯╰(*°▽°*)╯
    //ALL THE FLASHCARD METHODS
    //ᓚᘏᗢ  ᓚᘏᗢ  ᓚᘏᗢ  ᓚᘏᗢ

    public void insertFlashcard(Flashcard... flashcard){
        flashcardDao.insertFlashcard(flashcard);
    }

    public List<Flashcard> getFlashcardsByCourse(int courseId){
        return flashcardDao.getFlashcardsByCourse(courseId);
    }

    public Flashcard getFlashcardsById(int flashcardById){
        return flashcardDao.getFlashcardById(flashcardById);
    }

    public List<Flashcard> getAllFlashcards(){
        return flashcardDao.getAllFlashcards();
    }

    public void deleteFlashcard(Flashcard flashcard){
        flashcardDao.deleteFlashcard(flashcard);
    }


}
