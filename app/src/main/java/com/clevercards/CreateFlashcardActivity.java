package com.clevercards;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.clevercards.entities.Flashcard;

public class CreateFlashcardActivity extends AppCompatActivity {

    private EditText frontTextEdit;
    private EditText backTextEdit;

    //private DataRepository repository;

    private int courseId; // passed from CreateCourseActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flashcard);

        // Repo
        //repository = new DataRepository(this);

        // Get courseId from the intent
        courseId = getIntent().getIntExtra("courseId", -1);

        if (courseId == -1) {
            Toast.makeText(this, "Error: Course ID not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // UI elements
        frontTextEdit = findViewById(R.id.frontTextEdit);
        backTextEdit = findViewById(R.id.backTextEdit);
        Button nextButton = findViewById(R.id.nextFlashcardButton);
        Button dashboardButton = findViewById(R.id.dashboardButton);
        Button signOutButton = findViewById(R.id.signOutButton);

        // Handle the Next button
        nextButton.setOnClickListener(v -> saveFlashcard(false));

        // Handle Dashboard button
        dashboardButton.setOnClickListener(v -> saveFlashcard(true));

        // Handle Sign Out button
        signOutButton.setOnClickListener(v -> {
            Intent i = new Intent(CreateFlashcardActivity.this, SignInActivity.class);
            startActivity(i);
            finish();
        });
    }

    private void saveFlashcard(boolean returnToDashboard) {
        String front = frontTextEdit.getText().toString().trim();
        String back = backTextEdit.getText().toString().trim();

        if (front.isEmpty() || back.isEmpty()) {
            Toast.makeText(this, "Both sides must be filled in", Toast.LENGTH_SHORT).show();
            return;
        }

        Flashcard flashcard = new Flashcard(courseId, front, back);
        //repository.insertFlashcard(flashcard);

        Toast.makeText(this, "Flashcard saved!", Toast.LENGTH_SHORT).show();

        if (returnToDashboard) {
            Intent i = new Intent(CreateFlashcardActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        } else {
            // Reset fields for new card
            frontTextEdit.setText("");
            backTextEdit.setText("");
            frontTextEdit.requestFocus();
        }
    }
}
