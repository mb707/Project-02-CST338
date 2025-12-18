package com.clevercards;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.clevercards.database.CleverCardsDatabase;
import com.clevercards.database.repository.CleverCardsRepository;
import com.clevercards.entities.Flashcard;

/**
 * Name: Morgan Beebe
 * Date: 2025-12-13
 * Explanation: this is my activity for creating new flashcards
 * it allows the admin to enter front and back text to flashcards
 * it saves the data to the database, in order
 * it allows to either create more flashcards, go to the previous screen, or return to the dashboard
 * it also has the option to sign out
 * a FAB floating action button has been included in the bottom right to add flashcards,
 * i wanted to experiment with FABs and get more experience.
 */

public class CreateFlashcardActivity extends AppCompatActivity {
    //input for front side of flashcard, the question
    private EditText frontTextEdit;

    //input for the back side of the flashcard, the answer
    private EditText backTextEdit;

    //the repo for interacting with flashcard database operations
    private CleverCardsRepository repository;

    //the ID of the course the flashcard will belong to,
    // each course has a unique ID

    private int courseId;

    //ID of currently logged in user
    private int userId;   // Passed from MainActivity


    // this will initialize the activity,
    // binds the UI parts,
    //retrieves the intent data,
    //and sets up button click listeners
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flashcard);

        repository = CleverCardsRepository.getRepository(getApplication());

        // Retrieve courseId & userId
        courseId = getIntent().getIntExtra("courseId", -1);
        userId = getIntent().getIntExtra("userId", -1);

        // Validate intent data
        if (courseId == -1 || userId == -1) {
            Toast.makeText(this, "Error loading flashcard creator", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // UI binding
        frontTextEdit = findViewById(R.id.frontTextEdit);
        backTextEdit = findViewById(R.id.backTextEdit);

        Button nextButton = findViewById(R.id.nextFlashcardButton);
        Button dashboardButton = findViewById(R.id.dashboardButton);
        Button signOutButton = findViewById(R.id.signOutBtn);

        // save flashcard and add new flashcard
        nextButton.setOnClickListener(v -> saveFlashcard(false));

        // go to dashboard WITHOUT saving or validating
        dashboardButton.setOnClickListener(v -> {
            Intent intent = MainActivity.mainActivityIntentFactory(this, userId);
            startActivity(intent);
            finish();
        });


        // Sign out and return to login screen
        signOutButton.setOnClickListener(v -> {
            startActivity(SignInActivity.signInIntentFactory(this));
            finish();
        });
    }

    //validates user input,
    //creates a flashcard entity,
    //writes it to the database
    //UI updates are posted back to the main thread
    private void saveFlashcard(boolean returnToDashboard) {
        String front = frontTextEdit.getText().toString().trim();
        String back = backTextEdit.getText().toString().trim();

        //validates input fields
        if (front.isEmpty() || back.isEmpty()) {
            Toast.makeText(this, "Both sides must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        Flashcard flashcard = new Flashcard(courseId, front, back);

        // Write to database
        CleverCardsDatabase.databaseWriteExecutor.execute(() -> {
            repository.insertFlashcard(flashcard);

            //update UI on main thread after save
            runOnUiThread(() -> {
                Toast.makeText(this, "Flashcard saved!", Toast.LENGTH_SHORT).show();

                if (returnToDashboard) {
                    Intent intent = MainActivity.mainActivityIntentFactory(this, userId);
                    startActivity(intent);
                    finish();
                } else {
                    // reset input fields for next flashcard
                    frontTextEdit.setText("");
                    backTextEdit.setText("");
                    frontTextEdit.requestFocus();
                }
            });
        });
    }

    // Intent Factory
    public static Intent createFlashcardIntentFactory(Context context, int courseId, int userId) {
        Intent intent = new Intent(context, CreateFlashcardActivity.class);
        intent.putExtra("courseId", courseId);
        intent.putExtra("userId", userId);
        return intent;
    }
}