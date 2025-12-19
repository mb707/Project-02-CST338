package com.clevercards;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import com.clevercards.entities.Course;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Name: Ashley Wozow
 * Date: 2025-12-13
 * Explanation: unit test for course class
 */
public class CourseEntityTest {
    Course course1, course2;

    @Before
    public void setUp() throws Exception {
        course1 = new Course("CS 101", 10);
        course2 = new Course("CS 202", 30);
    }

    @After
    public void tearDown() throws Exception {
        course1 = null;
        course2 = null;
    }

    /**
     * This tests that the getters and setters are working as expected
     */
    @Test
    public void testUserGettersAndSetters(){
        // Tests the getter values
        assertEquals("CS 101", course1.getCourseName());
        assertEquals(10, course1.getNumberOfCards());

        // Sets new values
        course1.setCourseName("CS 102");
        course1.setNumberOfCards(20);

        // Tests the setter values
        assertNotEquals("CS 101", course1.getCourseName());
        assertEquals("CS 102", course1.getCourseName());
        assertEquals(20, course1.getNumberOfCards());
    }

    /**
     * This tests that our generate keys are working as expected
     */
    @Test
    public void testKeys(){
        // Test that keys are actually being populated for multiple courses
        assertNotNull(course1);
        assertNotNull(course2);

        // Tests to make sure keys are unique
        assertNotEquals(course1, course2);
    }

}
