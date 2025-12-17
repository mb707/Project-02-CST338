package com.clevercards.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.clevercards.database.CleverCardsDatabase;
import com.clevercards.entities.Flashcard;

import java.util.List;

/**
 * Name: Morgan Beebe
 * Date: 2025-12-06
 * Explanation: interface for flashcard dao
 */
@Dao
public interface FlashcardDao {
    //insert a new flashcard into a course
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFlashcard(Flashcard... flashcard);

    //get all the flashcards for a specific course
    @Query("SELECT * FROM " + CleverCardsDatabase.FLASHCARD_TABLE + " WHERE courseId = :courseId")
    List<Flashcard> getFlashcardsByCourse(int courseId);

    //get a specific flashcard that was created
    @Query("SELECT * FROM " + CleverCardsDatabase.FLASHCARD_TABLE + " WHERE flashcardId = :flashcardId LIMIT 1")
    Flashcard getFlashcardById(int flashcardId);

    @Query("SELECT * FROM " + CleverCardsDatabase.FLASHCARD_TABLE + " ORDER BY frontText")
    List<Flashcard> getAllFlashcards();

    //delete a flashcard
    @Delete
    void deleteFlashcard(Flashcard flashcard);

    @Query("DELETE from " + CleverCardsDatabase.FLASHCARD_TABLE)
    void deleteAll();


    @Query("SELECT * FROM " + CleverCardsDatabase.FLASHCARD_TABLE)
    LiveData<List<Flashcard>> getAllFlashcardsLive();


}
