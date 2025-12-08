package com.clevercards.viewHolders;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.clevercards.entities.Course;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {

    // TODO: wire this
    //private final CourseRepository repository;

    public CourseViewModel(@NonNull Application application) {
        super(application);
        //repository = CourseRepository.getRepository(application);
    }

//    public LiveData<List<Course>> getAllCoursesByUserId(int userId) {
//        return repository.getAllCoursesByUserIdLiveData(userId);
//    }

//    public void insert(Course course) {
//        repository.insertCourse(course);
//    }
}

