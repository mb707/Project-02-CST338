package com.clevercards;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Button;

import androidx.annotation.NonNull;
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

    private int signedInUserId;     // signed-in user
    private int courseId;   // course flashcards we are viewing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewFlashcardsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = CleverCardsRepository.getRepository(getApplication());

        signedInUserId = getIntent().getIntExtra("userId", -1);
        courseId = getIntent().getIntExtra("courseId", -1);

        if (signedInUserId == -1 || courseId == -1) {
            Toast.makeText(this, "Unable to load flashcards", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        binding.signOutButton.setOnClickListener(v -> {
            signedInUserId = -1;
            SignOutManager.showSignOutDialog(this, "userid");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_flashcard_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem returnToDashboard = menu.findItem(R.id.dashboard);
        MenuItem addFlashcard = menu.findItem(R.id.createFlashcard);
        if (returnToDashboard != null) returnToDashboard.setVisible(true);
        if (addFlashcard != null) addFlashcard.setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.dashboard) {
            finish();
            return true;
        }
        else if (id == R.id.createFlashcard) {
            Intent intent = CreateFlashcardActivity.createFlashcardIntentFactory(
                    this,
                    courseId,
                    signedInUserId
            );
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
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

