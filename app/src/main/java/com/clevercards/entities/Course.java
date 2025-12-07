package com.clevercards.entities;

import androidx.annotation.NonNull;

import java.util.Objects;

//TODO: Add this: @Entity(tableName = CleverCardsDatabase.COURSE_TABLE)
public class Course {

    /** Fields */
    //TODO: Add @PrimaryKey(autoGenerate = true)
    private int id;
    private String courseName;
    private int numberOfCards;
    private int userId;

    /** Constructors */
    public Course(String courseName, int numberOfCards, int userId){
        this.courseName = courseName;
        this.numberOfCards = numberOfCards;
        this.userId = userId;
    }

    public Course(String courseName, int numberOfCards){
        this.courseName = courseName;
        this.numberOfCards = numberOfCards;
    }

    /** Getters and Setters */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return id == course.id && numberOfCards == course.numberOfCards && userId == course.userId && Objects.equals(courseName, course.courseName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseName, numberOfCards, userId);
    }

    /** ToString */
    @NonNull
    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", numberOfCards=" + numberOfCards +
                ", userId=" + userId +
                '}';
    }
}
