package com.clevercards.database.dao;

import com.clevercards.database.CleverCardsDatabase;
import com.clevercards.entities.Course;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Name: Morgan Beebe
 * Date: 2025-12-06
 * Explanation: Interface for the course dao
 */
@Dao
public interface CourseDao {
    //add new course (only created by awesome admins)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(Course... course);

    @Query("DELETE from " + CleverCardsDatabase.COURSE_TABLE)
    void deleteAll();
    @Delete
    void delete(Course course);

    @Update
    void updateCourse(Course course);

    //get all the courses into the system (for students to use)
    @Query("SELECT * FROM " + CleverCardsDatabase.COURSE_TABLE + " ORDER BY courseName")
    List<Course> getAllCourses();

    @Query("SELECT * FROM " + CleverCardsDatabase.COURSE_TABLE + " ORDER BY courseName")
    LiveData<List<Course>> getAllCoursesLD();

    //get courses from a specific admin (professor)
    @Query("SELECT * FROM " + CleverCardsDatabase.COURSE_TABLE + " WHERE userId = :userId")
    List<Course> getCoursesByUser(int userId);

    //get a specific course based on its ID
    @Query("SELECT * FROM " + CleverCardsDatabase.COURSE_TABLE + " WHERE courseId = :courseId LIMIT 1")
    Course getCourseById(int courseId);

    @Query("SELECT * FROM " + CleverCardsDatabase.COURSE_TABLE +" WHERE userId = :signedInId ORDER BY courseName ASC")
    LiveData<List<Course>> getCourseByUserIdLiveData(int signedInId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertSingleCourse(Course course);

    @Query("SELECT * FROM " + CleverCardsDatabase.COURSE_TABLE + " WHERE courseId = :courseId LIMIT 1")
    Course getCourseByIdSync(int courseId);
}
