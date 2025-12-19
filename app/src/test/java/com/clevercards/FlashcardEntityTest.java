package com.clevercards;

import static org.junit.Assert.assertEquals;

import com.clevercards.entities.Flashcard;

import org.junit.Test;

/**
 * Name: Morgan Beebe
 * Date: 2025-12-13
 * Explanation: unit test for course class
 */
public class FlashcardEntityTest {

    @Test
    public void constructor_setsFieldsCorrectly() {
        Flashcard flashcard = new Flashcard(1, "Front", "Back");

        assertEquals(1, flashcard.getCourseId());
        assertEquals("Front", flashcard.getFrontText());
        assertEquals("Back", flashcard.getBackText());
    }

    @Test
    public void setters_updateFieldsCorrectly() {
        Flashcard flashcard = new Flashcard(1, "Front", "Back");

        flashcard.setFlashcardId(10);
        flashcard.setCourseId(2);
        flashcard.setFrontText("New Front");
        flashcard.setBackText("New Back");

        assertEquals(10, flashcard.getFlashcardId());
        assertEquals(2, flashcard.getCourseId());
        assertEquals("New Front", flashcard.getFrontText());
        assertEquals("New Back", flashcard.getBackText());
    }
}