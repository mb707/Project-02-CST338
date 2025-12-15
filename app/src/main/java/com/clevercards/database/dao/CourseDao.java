package com.clevercards.database.dao;

import com.clevercards.database.CleverCardsDatabase;
import com.clevercards.entities.Course;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

/**
 * Name: Morgan Beebe
 * Date: 2025-12-06
 * Explanation: Interface for the course dao
 */
@Dao
public interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCourse(Course course);

    @Query("DELETE FROM " + CleverCardsDatabase.COURSE_TABLE)
    void deleteAll();

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM " + CleverCardsDatabase.COURSE_TABLE + " ORDER BY courseName")
    List<Course> getAllCourses();

    @Query("SELECT * FROM " + CleverCardsDatabase.COURSE_TABLE + " WHERE userId = :userId")
    List<Course> getCoursesByUser(int userId);

    @Query("SELECT * FROM " + CleverCardsDatabase.COURSE_TABLE + " WHERE courseId = :courseId LIMIT 1")
    Course getCourseById(int courseId);


    @Query("SELECT * FROM " + CleverCardsDatabase.COURSE_TABLE +
            " WHERE userId = :signedInId ORDER BY courseName DESC")
    LiveData<List<Course>> getCourseByUserIdLiveData(int signedInId);
}