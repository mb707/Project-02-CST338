package com.clevercards;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.clevercards.database.repository.CleverCardsRepository;
import com.clevercards.databinding.ActivityCreateCourseBinding;
import com.clevercards.entities.Course;
import com.clevercards.viewHolders.course.CourseViewModel;

/**
 * @author Ashley Wozow
 * created: 12/9/25
 * In this Create Course Activity, the logic for where creating a new Course is handled.
 */

public class CreateCourseActivity extends AppCompatActivity {
    private EditText courseName;
    private EditText numberOfCards;
    private Button signOutbutton;
    private Button backbutton;
    private Button nextbutton;

    private static final String CREATE_COURSE_USER_ID =
            "com.clevercards.CREATE_COURSE_USER_ID";

    private ActivityCreateCourseBinding binding;
    private CourseViewModel courseViewModel;

    private Course course;
    private int signedInUserId = -1;

    private CleverCardsRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = CleverCardsRepository.getRepository(getApplication());
        signedInUserId = getIntent().getIntExtra(CREATE_COURSE_USER_ID, -1);
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        courseName = findViewById(R.id.courseNameEditText);
        numberOfCards = findViewById(R.id.numberOfCardsEditText);
        signOutbutton = findViewById(R.id.signOutButton);
        backbutton = findViewById(R.id.back_Button);
        nextbutton = findViewById(R.id.next_Button);


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButtonAction();
            }
        });

        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextButtonAction();
            }
        });

        signOutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCourseSignOut();
            }
        });
    }

    private void backButtonAction(){
        Intent intent = MainActivity.mainActivityIntentFactory(this, signedInUserId);
        startActivity(intent);
    }

    private void nextButtonAction() {
        String strCourseName = binding.courseNameEditText.getText().toString().trim();
        int intNumOfCards = 0;
        try {
            intNumOfCards = Integer.parseInt(binding.numberOfCardsEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(MainActivity.TAG,"Unable to get the number of cards");
        }
        if (strCourseName.isEmpty()) {
            toastMaker("Course name can not be empty");
            return;
        }
        course = new Course(strCourseName, intNumOfCards);
        course.setUserId(signedInUserId);

        courseViewModel.insert(course);

        toastMaker("Course Created!");
        finish(); //TODO: change this to an intent to flashcard activity
    }

    private void createCourseSignOut() {
        signedInUserId = -1;
        SignOutManager.showSignOutDialog(this,CREATE_COURSE_USER_ID);
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    // Intent factory to be used by other views
    static Intent createCourseIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, CreateCourseActivity.class);
        intent.putExtra(CREATE_COURSE_USER_ID, userId);
        return intent;
    }
}