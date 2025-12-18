package com.clevercards.viewHolders.flashcard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.clevercards.database.repository.CleverCardsRepository;
import com.clevercards.entities.Course;

import java.util.List;
/**
 * Author: Morgan and Ashley
 * Created on: 12/6/2025
 * Description: Flashcard View Model provides course related data to the flashcard UI
 * somewhat focused on course for the moment, will only support flashcard data in the future
 */
public class FlashcardViewModel {
    //TODO: adapt for flashcard

    //repo for accessing course and flashcard data from the database
    private CleverCardsRepository repository;

    //constructs view model and initializes the repo instance
    public FlashcardViewModel(@NonNull Application application) {
        super();
        repository = CleverCardsRepository.getRepository(application);
    }

    //retrives all courses associated with a specif user
    public LiveData<List<Course>> getAllCoursesByUserId(int userId) {
        return repository.getAllCoursesByUserIdLiveData(userId);
    }

    //retrieves all courses in the system
    public LiveData<List<Course>> getAllCoursesLD() {
        return repository.getAllCoursesLD();
    }

    //updates an existing course in the database
    public void update(Course course) {
        repository.updateCourse(course);
    }

    //inserts a new course into the database
    public void insert(Course course) {
        repository.insertCourse(course);
    }
}
