package com.clevercards.viewHolders.course;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.clevercards.database.dao.CourseDao;
import com.clevercards.database.repository.CleverCardsRepository;
import com.clevercards.entities.Course;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {

    private final CleverCardsRepository repository;
    private LiveData<List<Course>> userCourses;

    public CourseViewModel(@NonNull Application application) {
        super(application);
        repository = CleverCardsRepository.getRepository(application);
    }

    public LiveData<List<Course>> getAllCoursesByUserId(int userId) {
        if (userCourses == null) {
            userCourses = repository.getAllCoursesByUserIdLiveData(userId);
        }
        return userCourses;
    }

    public void insert(Course course) {
        repository.insertCourse(course);
    }

    public void update(Course course) {
        repository.updateCourse(course);
    }

    public void delete(Course course) {
        repository.deleteCourse(course);
    }
}

