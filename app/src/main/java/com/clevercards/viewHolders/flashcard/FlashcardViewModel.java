package com.clevercards.viewHolders.flashcard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.clevercards.database.repository.CleverCardsRepository;
import com.clevercards.entities.Flashcard;

import java.util.List;

public class FlashcardViewModel extends AndroidViewModel {
    private CleverCardsRepository repository;

    public FlashcardViewModel(@NonNull Application application) {
        super(application);
        repository = CleverCardsRepository.getRepository(application);
    }

    public LiveData<List<Flashcard>> getAllFlashcardsByCourseId(int courseID) {
        return repository.getALLFlashcardsByCourseID(courseID);
    }

    public void insert(Flashcard... flashcard) {
        repository.insertFlashcard(flashcard);
    }
}
