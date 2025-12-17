package com.clevercards.viewHolders.flashcard;

import android.app.Application;

import com.clevercards.database.repository.CleverCardsRepository;
import com.clevercards.entities.Flashcard;

import java.util.List;

public class FlashcardViewModel {
    private final CleverCardsRepository repository;
    private final LiveData<List<Flashcard>> flashcards;
    public FlashcardViewModel(@NonNull Application application) {
        super(application);
        repository = CleverCardsRepository.getRepository(application);
        flashcards = repository.getAllFlashcardsLive();
    }

    public LiveData<List<Flashcard>> getFlashcards() {
        return flashcards;
    }

}
