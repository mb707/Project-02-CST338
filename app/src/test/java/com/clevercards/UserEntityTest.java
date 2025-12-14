package com.clevercards;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.clevercards.entities.User;

import org.junit.Test;

/**
 * Name: Morgan Beebe
 * Date: 2025-12-13
 * Explanation: unit test for user class
 */
public class UserEntityTest {

    @Test
    public void testUserGettersAndSetters(){
        User user = new User("testUser", "testPass", true);

        //tests my constructor values
        assertEquals("testUser", user.getUsername());
        assertEquals("testPass", user.getPassword());
        assertTrue(user.isAdmin());

        //tests my setters
        user.setUsername("newName");
        user.setPassword("newPass");
        user.setAdmin(false);

        assertEquals("newName", user.getUsername());
        assertEquals("newPass", user.getPassword());
        assertFalse(user.isAdmin());

    }
}
