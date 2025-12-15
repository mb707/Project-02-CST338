package com.clevercards.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.clevercards.database.CleverCardsDatabase;
import com.clevercards.database.dao.CourseDao;
import com.clevercards.database.dao.FlashcardDao;
import com.clevercards.database.dao.UserDao;
import com.clevercards.entities.Course;
import com.clevercards.entities.Flashcard;
import com.clevercards.entities.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    private static volatile CleverCardsRepository repository;


    //main constructor for receiving the database instance
    private CleverCardsRepository(Application application) {
        CleverCardsDatabase db = CleverCardsDatabase.getInstance(application);
        this.userDao = db.userDao();
        this.courseDao = db.courseDao();
        this.flashcardDao = db.flashcardDao();
    }

    public static CleverCardsRepository getRepository(Application application) {
        if (repository == null) {
            synchronized (CleverCardsRepository.class) {
                if (repository == null) {
                    repository = new CleverCardsRepository(application);
                }
            }
        }
        return repository;
    }

    //╰(*°▽°*)╯╰(*°▽°*)╯╰(*°▽°*)╯╰(*°▽°*)╯
    //ALL THE USER METHODS
    //ᓚᘏᗢ  ᓚᘏᗢ  ᓚᘏᗢ  ᓚᘏᗢ

    public void insertUser(User... user){
        userDao.insertUser(user);
    }

    //public User signin(String username, String password){
        //return userDao.signin(username, password);
    //}

    public LiveData<User>  getUserById(int userId){
        return userDao.getUserById(userId);
    }

    public LiveData<List<User>> getAllUsers(){
        return userDao.getAllUsers();
    }

    public LiveData<User> getUsersByUserName(String username){
        return userDao.getUsersByUserName(username);
    }

    //╰(*°▽°*)╯╰(*°▽°*)╯╰(*°▽°*)╯╰(*°▽°*)╯
    //ALL THE COURSE METHODS
    //ᓚᘏᗢ  ᓚᘏᗢ  ᓚᘏᗢ  ᓚᘏᗢ

    public long insertCourse(Course course){
        return courseDao.insertCourse(course);
    }

    public List<Course> getAllCourses(){
        return courseDao.getAllCourses();
    }

    public List<Course> getCoursesByUser(int userId){
        return courseDao.getCoursesByUser(userId);
    }

    public List<Course> getCourseById(int courseId){
        return Collections.singletonList(courseDao.getCourseById(courseId));
    }

    public LiveData<List<Course>> getAllCoursesByUserIdLiveData(int loginUserId){
        return courseDao.getCourseByUserIdLiveData(loginUserId);
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
