package com.clevercards;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.clevercards.database.CleverCardsDatabase;
import com.clevercards.database.repository.CleverCardsRepository;
import com.clevercards.databinding.ActivityCreateCourseBinding;
import com.clevercards.entities.Course;
import com.clevercards.entities.User;
import com.clevercards.viewHolders.CourseViewModel;

/**
 * @author Ashley Wozow
 * created: 12/9/25
 * In this Create Course Activity, the logic for where creating a new Course is handled.
 */

public class CreateCourseActivity extends AppCompatActivity {

    private EditText courseName;
    private EditText numberOfCardsEditText;

    private static final String EXTRA_USER_ID =
            "com.clevercards.CREATE_COURSE_USER_ID";

    private int userId = -1;
    private CleverCardsRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCreateCourseBinding binding = ActivityCreateCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = CleverCardsRepository.getRepository(getApplication());

        int userId = getIntent().getIntExtra("userId", -1);
        if (userId == -1) {
            Toast.makeText(this, "Error: user not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        courseName = findViewById(R.id.courseNameEditText);
        numberOfCardsEditText = findViewById(R.id.numberOfCardsEditText);

        Button nextButton = findViewById(R.id.next_Button);
        Button backButton = findViewById(R.id.back_Button);
        Button signOutbutton = findViewById(R.id.signOutButton);

        nextButton.setOnClickListener(v -> createCourse());
        backButton.setOnClickListener(v -> finish());
        signOutbutton.setOnClickListener(v -> startActivity(new Intent(this, SignInActivity.class)));
    }

    private void createCourse() {
        String name = courseName.getText().toString().trim();
        String numString = numberOfCardsEditText.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Course name required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (numString.isEmpty()) {
            Toast.makeText(this, "Number of flashcards required", Toast.LENGTH_SHORT).show();
            return;
        }

        int numCards;
        try {
            numCards = Integer.parseInt(numString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid card count", Toast.LENGTH_SHORT).show();
            return;
        }

        Course course = new Course(name, numCards, userId);

        CleverCardsDatabase.databaseWriteExecutor.execute(() -> {
            long courseId = repository.insertCourse(course);

            runOnUiThread(() -> {
                if (courseId <= 0) {
                    Toast.makeText(this, "Error inserting course", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Go to flashcard creation next
                Intent intent = new Intent(CreateCourseActivity.this, CreateFlashcardActivity.class);
                intent.putExtra("courseId", (int) courseId);
                intent.putExtra("userId", userId);
                startActivity(intent);
                finish();
            });
        });
    }

    static Intent createCourseIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, CreateCourseActivity.class);
        intent.putExtra(EXTRA_USER_ID, userId); // FIXED
        intent.putExtra("userId", userId);
        return intent;
    }
}
