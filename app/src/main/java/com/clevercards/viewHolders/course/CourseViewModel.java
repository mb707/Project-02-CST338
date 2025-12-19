package com.clevercards.viewHolders.course;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.clevercards.database.dao.CourseDao;
import com.clevercards.database.repository.CleverCardsRepository;
import com.clevercards.entities.Course;

import java.util.List;


/**
 * @author Ashley Wozow
 * created: 12/6/25
 * CourseViewModel handles the logic necessary to utilze the recycler view on the back end side
 */
public class CourseViewModel extends AndroidViewModel {

    private CleverCardsRepository repository;

    public CourseViewModel(@NonNull Application application) {
        super(application);
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

