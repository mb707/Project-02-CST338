package com.clevercards;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.clevercards.database.CleverCardsDatabase;

import org.junit.Test;

/**
 * Name: Morgan Beebe
 * Date: 2025-12-07
 * Explanation: class for testing my database
 */
public class AppDatabaseTest {
    @Test
    public void database_tableNames_areDefined() {
        assertEquals("userTable", CleverCardsDatabase.USER_TABLE);
        assertEquals("courseTable", CleverCardsDatabase.COURSE_TABLE);
        assertEquals("flashcardTable", CleverCardsDatabase.FLASHCARD_TABLE);
    }

    @Test
    public void database_executor_isInitialized() {
        assertNotNull(CleverCardsDatabase.databaseWriteExecutor);
    }
}