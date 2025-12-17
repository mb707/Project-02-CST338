package com.clevercards.viewHolders.flashcard;

import com.clevercards.database.repository.CleverCardsRepository;
import com.clevercards.entities.Flashcard;

public class FlashcardViewModel {
    private final CleverCardsRepository repository;
    private final LiveData<List<Flashcard>> flashcards;


}
