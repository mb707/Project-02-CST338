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
/**
 * Name: Morgan Beebe & Ashley Wozow
 * Date: 2025-12-16
 * Explanation: activity responsible for displaying all flashcards
 *          associated with a specific course
 * presents flashcards in a scrollable list using recycler view.
 * allows admin to add new flashcards,
 *          navigate back, return to dashboard, or sign out
 */
public class ViewFlashcardsActivity extends AppCompatActivity {

    //view binding instance for accessing layout views
    private ActivityViewFlashcardsBinding binding;
    //repo used to retrieve flashcards from the database
    private CleverCardsRepository repository;

    //ID of the currently logged in user
    private int userId;     // logged-in user
    //ID of the course whose flashcards are being displayed
    private int courseId;   // course flashcards we are viewing


     // initializes the activity, validates intent data,
    //      sets up navigation controls,
     // and loads flashcards for the selected course.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewFlashcardsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = CleverCardsRepository.getRepository(getApplication());

        // Retrieve intent data
        userId = getIntent().getIntExtra("userId", -1);
        courseId = getIntent().getIntExtra("courseId", -1);

        // Validate intent data
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

        //goes to  add flashcard screen
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

        //Sign out (clear activity)
        signOutButton.setOnClickListener(v -> {
            startActivity(SignInActivity.signInIntentFactory(this));
            finishAffinity();
        });

        loadFlashcards();
    }

    //Loads flashcards for the current course from the database
     // database access is performed on a background thread,
    // the Recycler View is updated on the main UI thread
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

    // Intent factory method for launching view flashcards activity
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

