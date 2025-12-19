package com.clevercards;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.clevercards.database.CleverCardsDatabase;
import com.clevercards.database.repository.CleverCardsRepository;
import com.clevercards.databinding.ActivityCreateFlashcardBinding;
import com.clevercards.entities.Flashcard;

/**
 * Name: Morgan Beebe
 * Date: 2025-12-13
 * Explanation: class to create the flashcards
 */

public class CreateFlashcardActivity extends AppCompatActivity {

    private EditText frontTextEdit;
    private EditText backTextEdit;

    private CleverCardsRepository repository;

    private ActivityCreateFlashcardBinding binding;

    private int courseId;
    private int userId;   // Passed from MainActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateFlashcardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = CleverCardsRepository.getRepository(getApplication());

        // Retrieve courseId & userId
        courseId = getIntent().getIntExtra("courseId", -1);
        userId = getIntent().getIntExtra("userId", -1);

        if (courseId == -1 || userId == -1) {
            Toast.makeText(this, "Error loading flashcard creator", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // UI binding
        frontTextEdit = findViewById(R.id.frontTextEdit);
        backTextEdit = findViewById(R.id.backTextEdit);

        // save flashcard and add new flashcard
        binding.createFlashcardButton.setOnClickListener(v -> saveFlashcard(false));

        // Sign out
        binding.signOutBtn.setOnClickListener(v -> {
            userId = -1;
            SignOutManager.showSignOutDialog(this, "userid");
        });
    }

    private void saveFlashcard(boolean returnToDashboard) {
        String front = frontTextEdit.getText().toString().trim();
        String back = backTextEdit.getText().toString().trim();

        if (front.isEmpty() || back.isEmpty()) {
            Toast.makeText(this, "Both sides must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        Flashcard flashcard = new Flashcard(courseId, front, back);

        // Write
        CleverCardsDatabase.databaseWriteExecutor.execute(() -> {
            repository.insertFlashcard(flashcard);

            runOnUiThread(() -> {
                Toast.makeText(this, "Flashcard saved!", Toast.LENGTH_SHORT).show();

                if (returnToDashboard) {
                    Intent intent = MainActivity.mainActivityIntentFactory(this, userId);
                    startActivity(intent);
                    finish();
                } else {
                    // Prepare for next flashcard
                    frontTextEdit.setText("");
                    backTextEdit.setText("");
                    frontTextEdit.requestFocus();
                }
            });
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem backItem = menu.findItem(R.id.back);
        if (backItem != null) backItem.setVisible(true);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.back) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Intent Factory
    public static Intent createFlashcardIntentFactory(Context context, int courseId, int userId) {
        Intent intent = new Intent(context, CreateFlashcardActivity.class);
        intent.putExtra("courseId", courseId);
        intent.putExtra("userId", userId);
        return intent;
    }
}