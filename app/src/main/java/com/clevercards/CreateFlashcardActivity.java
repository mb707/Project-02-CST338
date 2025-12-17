package com.clevercards;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.clevercards.database.CleverCardsDatabase;
import com.clevercards.database.repository.CleverCardsRepository;
import com.clevercards.databinding.ActivityCreateFlashcardBinding;
import com.clevercards.entities.Flashcard;
import com.clevercards.viewHolders.course.CourseViewModel;
import com.clevercards.viewHolders.flashcard.FlashcardViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Name: Morgan Beebe
 * Date: 2025-12-13
 * Explanation: class to create the flashcards
 */

public class CreateFlashcardActivity extends AppCompatActivity {

    private static final String CREATE_FLASHCARD_COURSE_ID = "com.clevercards.CREATE_FLASHCARD_COURSE_ID";
    private static final String CREATE_FLASHCARD_USER_ID = "com.clevercards.CREATE_FLASHCARD_USER_ID";
    private EditText frontTextEdit;
    private EditText backTextEdit;

    private int numOfCards;

    private List<Flashcard> flashcardList = new ArrayList<>();
    private ActivityCreateFlashcardBinding binding;

    private FlashcardViewModel flashcardViewModel;

    private CleverCardsRepository repository;

    private int courseId;
    private int signedInUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateFlashcardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = CleverCardsRepository.getRepository(getApplication());
        courseId = getIntent().getIntExtra(CREATE_FLASHCARD_COURSE_ID, -1);
        signedInUserId = 1;
        flashcardViewModel = new ViewModelProvider(this).get(FlashcardViewModel.class);

        if (courseId == -1) {
            Toast.makeText(this, "Error loading flashcard creator", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        // UI binding
        frontTextEdit = findViewById(R.id.frontText_EditText);
        backTextEdit = findViewById(R.id.backText_EditText);
        numOfCards = CreateCourseActivity.numOfCardsFromCC;

        // Save + new card
        binding.nextFlashcardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String front = frontTextEdit.getText().toString().trim();
                String back = backTextEdit.getText().toString().trim();
                if (flashcardList.size() + 1 < numOfCards){
                    if (!front.isEmpty() && !back.isEmpty()){
                        flashcardList.add(saveFlashcard(front,back));
                    }
                    else {
                        Toast.makeText(CreateFlashcardActivity.this, "Both sides must be filled", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else {
                    returnToDashboard(false);
                }
            }
        });

        // Returns to dashboard without saving flashcard progress - forces admin to click next as opposed to dashboard
        binding.dashboardButton.setOnClickListener(v -> returnToDashboard(true));

        // Sign out
        binding.signOutButton.setOnClickListener(v -> {
            signedInUserId = -1;
            SignOutManager.showSignOutDialog(this, CREATE_FLASHCARD_COURSE_ID);
        });
    }

    private Flashcard saveFlashcard(String front, String back) {
        Flashcard flashcard = new Flashcard(courseId, front, back);
        flashcardViewModel.insert(flashcard);
        Toast.makeText(this, "Flashcard saved", Toast.LENGTH_SHORT).show();
        frontTextEdit.setText("");
        backTextEdit.setText("");
        return flashcard;
    }



    private void returnToDashboard(boolean fromDashboardButton){
        if (!fromDashboardButton){
            startActivity(MainActivity.mainActivityIntentFactory(this, signedInUserId));
            return;
        }
        new AlertDialog.Builder(this)
                .setTitle("Return To Dashboard")
                .setMessage("Are you sure you want to return to the dashboard? \n\nCourse will be saved without any flashcards.")
                .setPositiveButton("Yes", (dialog, which) -> {
                    startActivity(MainActivity.mainActivityIntentFactory(this, signedInUserId));
                })
                .setNegativeButton("No", null)
                .show();
    }

    // Intent Factory
    public static Intent createFlashcardIntentFactory(Context context, int courseId) {
        Intent intent = new Intent(context, CreateFlashcardActivity.class);
        intent.putExtra(CREATE_FLASHCARD_COURSE_ID, courseId);
        return intent;
    }
}