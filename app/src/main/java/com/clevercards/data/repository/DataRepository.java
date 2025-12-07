package com.clevercards.data.repository;

import android.content.Context;

import com.clevercards.data.AppDatabase;
import com.clevercards.data.dao.CourseDao;
import com.clevercards.data.dao.FlashcardDao;
import com.clevercards.data.dao.UserDao;
import com.clevercards.data.entities.Course;
import com.clevercards.data.entities.Flashcard;
import com.clevercards.data.entities.User;

import java.util.List;

/**
 * Name: Morgan Beebe
 * Date: 2025-12-06
 * Explanation: class to control my giant data repository
 */
public class DataRepository {
    private final UserDao userDao;
    private final CourseDao courseDao;
    private final FlashcardDao flashcardDao;

    //main constructor for receiving the database instance
    public DataRepository(Context context){
        AppDatabase db = AppDatabase.getInstance(context);
        this.userDao = db.userDao();
        this.courseDao = db.courseDao();
        this.flashcardDao = db.flashcardDao();

    }

    //╰(*°▽°*)╯╰(*°▽°*)╯╰(*°▽°*)╯╰(*°▽°*)╯
    //ALL THE USER METHODS
    //ᓚᘏᗢ  ᓚᘏᗢ  ᓚᘏᗢ  ᓚᘏᗢ

    public void insertUser(User user){
        userDao.insertUser(user);
    }

    public User login(String username, String password){
        return userDao.login(username, password);
    }

    public User getUserById(int userId){
        return userDao.getUserById();
    }

    public List<User> getAllUsers(){
        return userDao.getAllUsers();
    }

    //╰(*°▽°*)╯╰(*°▽°*)╯╰(*°▽°*)╯╰(*°▽°*)╯
    //ALL THE COURSE METHODS
    //ᓚᘏᗢ  ᓚᘏᗢ  ᓚᘏᗢ  ᓚᘏᗢ

    public void insertCourse(Course course){
        courseDao.insertCourse(course);
    }

    public List<Course> getAllCourses(){
        return courseDao.getAllCourses();
    }

    public List<Course> getCourseByUser(int userId){
        return courseDao.getCoursesByUser(userId);
    }

    public Course getCourseById(int courseId){
        return courseDao.getCourseById(courseId);
    }



    //╰(*°▽°*)╯╰(*°▽°*)╯╰(*°▽°*)╯╰(*°▽°*)╯
    //ALL THE FLASHCARD METHODS
    //ᓚᘏᗢ  ᓚᘏᗢ  ᓚᘏᗢ  ᓚᘏᗢ

    public void insertFlashcard(Flashcard flashcard){
        flashcardDao.insertFlashcard(flashcard);
    }

    public List<Flashcard> getFlashcardsByCourse(int courseId){
        return flashcardDao.getFlashcardByCourse(courseId);
    }

    public Flashcard getFlashcardById(int flashcard){
        return flashcardDao.getFlashcardById(flashcardById);
    }

    public void deleteFlashcard(Flashcard flashcard){
        flashcardDao.deleteFlashcard(flashcard);
    }


}
