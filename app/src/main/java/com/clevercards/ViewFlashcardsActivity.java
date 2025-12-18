package com.clevercards;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.clevercards.database.CleverCardsDatabase;
import com.clevercards.database.repository.CleverCardsRepository;
import com.clevercards.databinding.ActivityViewFlashcardsBinding;
import com.clevercards.entities.Flashcard;
import com.clevercards.viewHolders.flashcard.FlashcardListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ViewFlashcardsActivity extends AppCompatActivity {

    private ActivityViewFlashcardsBinding binding;
    private CleverCardsRepository repository;

    private int userId;     // logged-in user
    private int courseId;   // course flashcards we are viewing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewFlashcardsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = CleverCardsRepository.getRepository(getApplication());

        userId = getIntent().getIntExtra("userId", -1);
        courseId = getIntent().getIntExtra("courseId", -1);

        if (userId == -1 || courseId == -1) {
            Toast.makeText(this, "Unable to load flashcards", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        //Top navigation buttons
        Button backButton = findViewById(R.id.backButton);
        Button dashboardButton = findViewById(R.id.dashboardButton);
        Button signOutButton = findViewById(R.id.signOutButton);

        //Floating Action Button, the FAB man!
        FloatingActionButton addFab = findViewById(R.id.addFlashcardFab);

        //Add flashcard
        addFab.setOnClickListener(v -> {
            Intent intent = CreateFlashcardActivity.createFlashcardIntentFactory(
                    this,
                    courseId,
                    userId
            );
            startActivity(intent);
        });

        //back to previous screen
        backButton.setOnClickListener(v -> finish());

        //Dashboard (courses list)
        dashboardButton.setOnClickListener(v -> {
            Intent intent = MainActivity.mainActivityIntentFactory(this, userId);
            startActivity(intent);
            finish();
        });

        //Sign out (clear back stack)
        signOutButton.setOnClickListener(v -> {
            startActivity(SignInActivity.signInIntentFactory(this));
            finishAffinity();
        });

        loadFlashcards();
    }

    private void loadFlashcards() {
        CleverCardsDatabase.databaseWriteExecutor.execute(() -> {
            List<Flashcard> flashcards =
                    repository.getFlashcardsByCourse(courseId);

            runOnUiThread(() -> {
                FlashcardListAdapter adapter = new FlashcardListAdapter(flashcards);
                binding.flashcardsRecyclerView.setLayoutManager(
                        new LinearLayoutManager(this)
                );
                binding.flashcardsRecyclerView.setAdapter(adapter);
            });
        });
    }

    // Intent factory
    public static Intent intentFactory(
            Context context,
            int userId,
            int courseId
    ) {
        Intent intent = new Intent(context, ViewFlashcardsActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("courseId", courseId);
        return intent;
    }
}

