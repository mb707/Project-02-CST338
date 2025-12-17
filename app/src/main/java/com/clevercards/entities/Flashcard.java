package com.clevercards.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.clevercards.database.CleverCardsDatabase;

import java.util.Objects;

/**
 * Name: Morgan Beebe
 * Date: 2025-12-06
 * Explanation: flashcard entity for room database.
 * stores all my front and back text and course it belongs to.
 */

@Entity(
        tableName = CleverCardsDatabase.FLASHCARD_TABLE,
        foreignKeys = @ForeignKey(
                entity = Course.class,
                parentColumns = "courseId",
                childColumns = "courseId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("courseId")}
)
public class Flashcard {

    @PrimaryKey(autoGenerate = true)
    private int flashcardId;

    private final int courseId;
    private final String frontText;
    private final String backText;

    public Flashcard(int courseId, String frontText, String backText) {
        this.courseId = courseId;
        this.frontText = frontText;
        this.backText = backText;
    }

    // getters/setters...

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flashcard)) return false;
        Flashcard that = (Flashcard) o;
        return courseId == that.courseId
                && frontText.equals(that.frontText)
                && backText.equals(that.backText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, frontText, backText);
    }
}
