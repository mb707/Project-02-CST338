package com.clevercards.viewHolders.flashcard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.clevercards.database.repository.CleverCardsRepository;
import com.clevercards.entities.Course;

import java.util.List;

public class FlashcardViewModel {
    //TODO: adapt for flashcard

    private CleverCardsRepository repository;

    public FlashcardViewModel(@NonNull Application application) {
        super();
        repository = CleverCardsRepository.getRepository(application);
    }

    public LiveData<List<Course>> getAllCoursesByUserId(int userId) {
        return repository.getAllCoursesByUserIdLiveData(userId);
    }

    public LiveData<List<Course>> getAllCoursesLD() {
        return repository.getAllCoursesLD();
    }

    public void update(Course course) {
        repository.updateCourse(course);
    }

    public void insert(Course course) {
        repository.insertCourse(course);
    }
}
