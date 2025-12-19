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
 * Name: Morgan Beebe and Ashley Wozow
 * Date: 2025-12-06
 * Explanation: Interface for the course dao
 */
@Dao
public interface CourseDao {
    //add new course (only created by awesome admins/professors)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(Course... course);

    // deletes all courses
    @Query("DELETE from " + CleverCardsDatabase.COURSE_TABLE)
    void deleteAll();

    // deletes the specific course
    @Delete
    void delete(Course course);

    // updates the provided course
    @Update
    void updateCourse(Course course);

    //get all the courses into the system (for students to use)
    @Query("SELECT * FROM " + CleverCardsDatabase.COURSE_TABLE + " ORDER BY courseName")
    List<Course> getAllCourses();

    // retrieves all courses as LiveData, sorted by name
    @Query("SELECT * FROM " + CleverCardsDatabase.COURSE_TABLE + " ORDER BY courseName")
    LiveData<List<Course>> getAllCoursesLD();

    //get courses from a specific admin (professor)
    @Query("SELECT * FROM " + CleverCardsDatabase.COURSE_TABLE + " WHERE userId = :userId")
    List<Course> getCoursesByUser(int userId);

    //get a specific course based on its ID
    @Query("SELECT * FROM " + CleverCardsDatabase.COURSE_TABLE + " WHERE courseId = :courseId LIMIT 1")
    List<Course> getCourseById(int courseId);

    // retrieves a user's courses as LiveData, sorted by name
    @Query("SELECT * FROM " + CleverCardsDatabase.COURSE_TABLE +" WHERE userId = :signedInId ORDER BY courseName ASC")
    LiveData<List<Course>> getCourseByUserIdLiveData(int signedInId);

    // inserts a single course and returns its generated row ID
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertSingleCourse(Course course);

    // retrieves a course by its ID synchronously
    @Query("SELECT * FROM " + CleverCardsDatabase.COURSE_TABLE + " WHERE courseId = :courseId LIMIT 1")
    Course getCourseByIdSync(int courseId);
}
