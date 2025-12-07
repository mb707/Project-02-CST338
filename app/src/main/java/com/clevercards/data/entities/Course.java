package com.clevercards.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Name: Morgan Beebe
 * Date: 2025-12-06
 * Explanation: course entity for room database
 *
 *
 */

@Entity
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int courseId;

    private String courseName;
    private String courseDescription;

    //fk to user table (the admin that created this course)

    private int createdByUserId;

    //constructor

    public Course(String courseName, String courseDescription, int createdByUserId){
        this.courseName=courseName;
        this.courseDescription=courseDescription;
        this.createdByUserId=createdByUserId;

    }

    //getters and setters


    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName=courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public int getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(int createdByUserId) {
        this.createdByUserId = createdByUserId;
    }
}
