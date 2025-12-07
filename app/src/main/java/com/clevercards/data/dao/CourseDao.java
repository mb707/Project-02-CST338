package com.clevercards.data.dao;

import com.clevercards.data.entities.Course;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

/**
 * Name: Morgan Beebe
 * Date: 2025-12-06
 * Explanation: Interface for the course dao
 */

public interface CourseDao {
    //add new course (only created by awesome admins)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(Course course);

    //get all the courses into the system (for students to use)
    @Query("SELECT * FROM Course")
    List<Course> getAllCourses();

    //get courses from a specific admin (professor)
    @Query("SELECT * FROM Course WHERE createdByUser = :userId")
    List<Course> getCoursesByUser(int userId);

    //get a specific course based on its ID
    @Query("SELECT * FROM Course WHERE courseId = :courseId LIMIT 1")
    Course getCourseById(int courseId);
}
