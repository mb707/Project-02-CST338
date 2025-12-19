package com.clevercards.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.clevercards.database.CleverCardsDatabase;

import java.util.Objects;

/**
 * @author Ashley Wozow and Morgan Beebe
 * created: 12/6/25
 * In this Course class, the logic for Course is handled.
 */

@Entity(tableName = CleverCardsDatabase.COURSE_TABLE)
public class Course {

    /** Fields */
    @PrimaryKey(autoGenerate = true)
    private int courseId;
    private String courseName;
    private int numberOfCards;
    private int userId;

    /** Constructor */
    public Course(String courseName, int numberOfCards){
        this.courseName = courseName;
        this.numberOfCards = numberOfCards;
    }

    /** Getters and Setters */
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
        this.courseName = courseName;
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }

    public void setNumberOfCards(int numberOfCards) {
        this.numberOfCards = numberOfCards;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    /** Equals and HashCode */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return courseId == course.courseId && numberOfCards == course.numberOfCards && userId == course.userId && Objects.equals(courseName, course.courseName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, courseName, numberOfCards, userId);
    }

    /** ToString */
    @NonNull
    @Override
    public String toString() {
        return "Course{" +
                "id=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", numberOfCards=" + numberOfCards +
                ", userId=" + userId +
                '}';
    }
}
