package com.clevercards.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.clevercards.database.CleverCardsDatabase;

import java.util.Objects;

/**
 * Name: Morgan Beebe
 * Date: 2025-12-06
 * Explanation: flashcard entity for room database.
 * stores all my front and back text and course it belongs to.
 */

@Entity(tableName = CleverCardsDatabase.FLASHCARD_TABLE)
public class Flashcard {
    @PrimaryKey(autoGenerate = true)
    private int flashcardId;

    private int courseId; //fk to course
    private String frontText;
    private String backText;

    //constructor

    public Flashcard(int courseId, String frontText, String backText){
        this.courseId=courseId;
        this.frontText=frontText;
        this.backText=backText;

    }

    //getters and setters


    public int getFlashcardId() {
        return flashcardId;
    }

    public void setFlashcardId(int flashcardId) {
        this.flashcardId = flashcardId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getFrontText() {
        return frontText;
    }

    public void setFrontText(String frontText) {
        this.frontText = frontText;
    }

    public String getBackText() {
        return backText;
    }

    public void setBackText(String backText) {
        this.backText = backText;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Flashcard)) return false;
        Flashcard flashcard = (Flashcard) o;
        return flashcardId == flashcard.flashcardId && courseId == flashcard.courseId && Objects.equals(frontText, flashcard.frontText) && Objects.equals(backText, flashcard.backText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flashcardId, courseId, frontText, backText);
    }
}
