package com.clevercards;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.clevercards.database.repository.CleverCardsRepository;
import com.clevercards.databinding.ActivityMainBinding;
import com.clevercards.entities.Course;
import com.clevercards.entities.User;
import com.clevercards.viewHolders.CourseAdapter;
import com.clevercards.viewHolders.CourseViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ashley Wozow
 * created: 12/6/25
 * In this MainActivity class, the Dashboard logic is handled.
 */
public class MainActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER_ID = "com.clevercards.MAIN_ACTIVITY_USER_ID";
    private static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.clevercards.SAVED_INSTANCE_STATE_USERID_KEY";
    private static final int SIGNED_OUT = -1;

    private ActivityMainBinding binding;
    private CleverCardsRepository repository;
    private CourseAdapter courseAdapter;

    private User user;
    private int signedInUserID = SIGNED_OUT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = CleverCardsRepository.getRepository(getApplication());
        CourseViewModel courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        signInUser(savedInstanceState);

        if (signedInUserID == SIGNED_OUT) {
            startActivity(SignInActivity.signInIntentFactory(this));
            finish();
            return;
        }

        // Setup RecyclerView
        binding.courseRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        courseAdapter = new CourseAdapter(new CourseAdapter.CourseDiff(), this::courseToFlashcards);
        binding.courseRecyclerView.setAdapter(courseAdapter);

        courseViewModel.getAllCoursesByUserId(signedInUserID)
                .observe(this, courses -> {
                    courseAdapter.submitList(courses);
                    if (courses == null || courses.isEmpty()) {
                        binding.noCoursesTextView.setVisibility(android.view.View.VISIBLE);
                        binding.courseRecyclerView.setVisibility(android.view.View.GONE);
                    } else {
                        binding.noCoursesTextView.setVisibility(android.view.View.GONE);
                        binding.courseRecyclerView.setVisibility(android.view.View.VISIBLE);
                    }
                });

        // Create Course button
        binding.createCourseButton.setOnClickListener(v -> {
            Intent intent = CreateCourseActivity.createCourseIntentFactory(this, signedInUserID);
            startActivity(intent);
        });

        // View Flashcards button
        binding.viewFlashcardsButton.setOnClickListener(v -> {
            Intent intent = ViewFlashcardsActivity.intentFactory(this, signedInUserID);
            startActivity(intent);
        });

        // Sign out
        binding.btnSignOut.setOnClickListener(v -> showSignOutDialog());
    }

    private void signInUser(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        signedInUserID = prefs.getInt(getString(R.string.preference_userId_key), SIGNED_OUT);

        if (signedInUserID == SIGNED_OUT && savedInstanceState != null) {
            signedInUserID = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY, SIGNED_OUT);
        }

        if (signedInUserID == SIGNED_OUT) {
            signedInUserID = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, SIGNED_OUT);
        }

        if (signedInUserID == SIGNED_OUT) return;

        updateSharedPreference();

        repository.getUserById(signedInUserID).observe(this, user -> {
            this.user = user;

            if (user != null) {
                binding.roleTextView.setText(user.getUsername());

                // Admin-only visibility
                if (user.isAdmin()) {
                    binding.createCourseButton.setVisibility(android.view.View.VISIBLE);
                    binding.viewFlashcardsButton.setVisibility(android.view.View.VISIBLE);
                } else {
                    binding.createCourseButton.setVisibility(android.view.View.GONE);
                    binding.viewFlashcardsButton.setVisibility(android.view.View.GONE);
                }
            }
        });
    }

    private void updateSharedPreference() {
        SharedPreferences prefs = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        prefs.edit().putInt(getString(R.string.preference_userId_key), signedInUserID).apply();
    }

    private void showSignOutDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.confirm_sign_out)
                .setMessage("Are you sure you want to sign out?")
                .setPositiveButton("Sign Out", (dialog, which) -> {
                    signOut();
                    Toast.makeText(this, "Signed out!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void signOut() {
        signedInUserID = SIGNED_OUT;
        updateSharedPreference();
        startActivity(SignInActivity.signInIntentFactory(this));
        finish();
    }

    private void courseToFlashcards(Course course) {
        Intent intent = CreateFlashcardActivity.createFlashcardIntentFactory(
                this,
                course.getCourseId(),
                signedInUserID
        );
        startActivity(intent);
    }

    static Intent mainActivityIntentFactory(Context context, int userID) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userID);
        return intent;
    }
}