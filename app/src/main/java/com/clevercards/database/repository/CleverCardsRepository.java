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

    private static CleverCardsRepository repository;

    private CleverCardsRepository(Context context){
        CleverCardsDatabase db = CleverCardsDatabase.getInstance(context);
        userDao = db.userDao();
        courseDao = db.courseDao();
        flashcardDao = db.flashcardDao();
    }

    public static synchronized CleverCardsRepository getRepository(Application application){
        if (repository == null) {
            repository = new CleverCardsRepository(application);
        }
        return repository;
    }

    //╰(*°▽°*)╯╰(*°▽°*)╯╰(*°▽°*)╯╰(*°▽°*)╯
    //ALL THE USER METHODS
    //ᓚᘏᗢ  ᓚᘏᗢ  ᓚᘏᗢ  ᓚᘏᗢ

    public void insertUser(User... user){
        CleverCardsDatabase.databaseWriteExecutor.execute(() -> {
            userDao.insertUser(user);
        });
    }

    public void deleteUser(User user) {
        CleverCardsDatabase.databaseWriteExecutor.execute(() -> {
            userDao.delete(user);
        });
    }


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

    public void insertCourse(Course... course){
        CleverCardsDatabase.databaseWriteExecutor.execute(() -> {
            courseDao.insertCourse(course);
        });
    }

    public void updateCourse(Course course) {
        CleverCardsDatabase.databaseWriteExecutor.execute(() -> {
            courseDao.updateCourse(course);
        });
    }

    public LiveData<List<Course>> getAllCoursesLD() {
        return courseDao.getAllCoursesLD();
    }

    public List<Course> getAllCourses(){
        return courseDao.getAllCourses();
    }

    public List<Course> getCoursesByUser(int userId){
        return courseDao.getCoursesByUser(userId);
    }

    public List<Course> getCourseById(int courseId){
        return courseDao.getCourseById(courseId);
        //return Collections.singletonList(courseDao.getCourseById(courseId));
    }

    public LiveData<List<Course>> getAllCoursesByUserIdLiveData(int loginUserId){
        return courseDao.getCourseByUserIdLiveData(loginUserId);
    }

    public void assignCourseWithFlashcardsToUser(int sourceCourseId, int targetUserId) {
        CleverCardsDatabase.databaseWriteExecutor.execute(() -> {
            // 1. Get the original course
            Course source = courseDao.getCourseByIdSync(sourceCourseId);
            if (source == null) {
                return;
            }

            // 2. Clone the course for the target user
            Course newCourse = new Course(
                    source.getCourseName(),
                    source.getNumberOfCards()
            );
            newCourse.setUserId(targetUserId);

            // 3. Insert and get new courseId
            long newCourseIdLong = courseDao.insertSingleCourse(newCourse);
            int newCourseId = (int) newCourseIdLong;
            Log.d("CC_REPO", "Cloned course '" + source.getCourseName()
                    + "' for userId=" + targetUserId + " as courseId=" + newCourseId);


            //TODO: implement when flashcard activity is wired up
//            // 4. Get all flashcards of the source course
//            List<Flashcard> originalCards = flashcardDao.getFlashcardsByCourse(sourceCourseId);
//
//            // 5. Clone each flashcard for the new course
//            for (Flashcard card : originalCards) {
//                Flashcard newCard = new Flashcard(
//                        newCourseId,
//                        card.getFrontText(),
//                        card.getBackText()
//                );
//                flashcardDao.insertFlashcard(newCard);
//            }
        });


    }
    public LiveData<List<Flashcard>> getAllFlashcardsLive() {
        return flashcardDao.getAllFlashcardsLive();
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
