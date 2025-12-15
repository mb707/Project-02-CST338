package com.clevercards;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.clevercards.database.repository.CleverCardsRepository;
import com.clevercards.databinding.ActivityViewFlashcardsBinding;
import com.clevercards.entities.Flashcard;

import java.util.List;

public class ViewFlashcardsActivity extends AppCompatActivity {

    private ActivityViewFlashcardsBinding binding;
    private CleverCardsRepository repository;

    private int userId;  // which user to load flashcards for

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewFlashcardsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = CleverCardsRepository.getRepository(getApplication());
        userId = getIntent().getIntExtra("userId", -1);

        if (userId == -1) {
            Toast.makeText(this, "Unable to load flashcards", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadFlashcards();
    }

    private void loadFlashcards() {
        List<Flashcard> flashcards = repository.getAllFlashcards();

        FlashcardListAdapter adapter = new FlashcardListAdapter(flashcards);

        binding.flashcardsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.flashcardsRecyclerView.setAdapter(adapter);
    }

    public static Intent intentFactory(Context context, int userId) {
        Intent intent = new Intent(context, ViewFlashcardsActivity.class);
        intent.putExtra("userId", userId);
        return intent;
    }
}

