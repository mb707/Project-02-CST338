package com.clevercards;

import static org.junit.Assert.assertEquals;

import com.clevercards.entities.Course;

import org.junit.Test;

/**
 * Name: Morgan Beebe
 * Date: 2025-12-13
 * Explanation: unit test for course class
 */
public class CourseEntityTest {

    @Test
    public void constructor_setsFieldsCorrectly() {
        Course course = new Course("Math", 10);

        assertEquals("Math", course.getCourseName());
        assertEquals(10, course.getNumberOfCards());
    }

    @Test
    public void setters_updateFieldsCorrectly() {
        Course course = new Course("Science", 5);

        course.setCourseId(1);
        course.setCourseName("History");
        course.setNumberOfCards(20);
        course.setUserId(42);

        assertEquals(1, course.getCourseId());
        assertEquals("History", course.getCourseName());
        assertEquals(20, course.getNumberOfCards());
        assertEquals(42, course.getUserId());
    }
}
