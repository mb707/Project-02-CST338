package com.clevercards;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.clevercards.database.repository.CleverCardsRepository;
import com.clevercards.databinding.ActivityViewFlashcardsBinding;
import com.clevercards.viewHolders.course.CourseViewModel;
import com.clevercards.viewHolders.flashcard.FlashcardAdapter;
import com.clevercards.viewHolders.flashcard.FlashcardViewModel;

public class ViewFlashcardsActivity extends AppCompatActivity {

    private static final String VIEW_FLASHCARD_COURSE_ID =
            "com.clevercards.VIEW_FLASHCARD_COURSE_ID";
    private ActivityViewFlashcardsBinding binding;
    private CleverCardsRepository repository;

    private FlashcardAdapter flashcardAdapter;
    private FlashcardViewModel flashcardViewModel;

    private int courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewFlashcardsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = CleverCardsRepository.getRepository(getApplication());
        courseID = getIntent().getIntExtra(VIEW_FLASHCARD_COURSE_ID, -1);
        flashcardViewModel = new ViewModelProvider(this).get(FlashcardViewModel.class);

        if (courseID == -1) {
            Toast.makeText(this, "Unable to load flashcards", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        flashcardAdapter = new FlashcardAdapter(
                new FlashcardAdapter.FlashcardDiff(),
                flashcard -> {
                    Toast.makeText(this,
                            flashcard.getFrontText(),
                            Toast.LENGTH_SHORT).show();
                }
        );

        binding.flashcardsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.flashcardsRecyclerView.setAdapter(flashcardAdapter);

        flashcardViewModel.getAllFlashcardsByCourseId(courseID)
                .observe(this, flashcards -> flashcardAdapter.submitList(flashcards));
    }


    public static Intent viewFlashcardIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, ViewFlashcardsActivity.class);
        intent.putExtra("userId", userId);
        return intent;
    }
}

