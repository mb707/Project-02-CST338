package com.clevercards;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.clevercards.database.repository.CleverCardsRepository;
import com.clevercards.databinding.ActivityCreateCourseBinding;
import com.clevercards.entities.Course;
import com.clevercards.viewHolders.CourseViewModel;

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

    private static final String COURSE_USER_ID =
            "com.clevercards.CREATE_COURSE_USER_ID";

    private ActivityCreateCourseBinding binding;
    private CourseViewModel courseViewModel;

    private Course course;
    private int userId = -1;

    private CleverCardsRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = CleverCardsRepository.getRepository(getApplication());
        userId = getIntent().getIntExtra(COURSE_USER_ID, -1);
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
                signOut();
            }
        });
    }

    private void backButtonAction(){
        Intent intent = MainActivity.mainActivityIntentFactory(this, userId);
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
        course.setUserId(userId);

        courseViewModel.insert(course);

        toastMaker("Course Created!");
        finish(); //TODO: change this to an intent to flashcard activity
    }

    private void signOut() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(CreateCourseActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setMessage("Are you sure you want to sign out?");
        alertBuilder.setPositiveButton("Sign Out", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                signOut();
                toastMaker("Signed Out!");
            }
        });
        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertBuilder.setTitle(R.string.confirm_sign_out);
        alertBuilder.setIcon(R.drawable.baseline_exit_to_app_24);
        alertBuilder.create().show();
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    // Intent factory to be used by other views
    static Intent createCourseIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, CreateCourseActivity.class);
        intent.putExtra(COURSE_USER_ID, userId);
        return intent;
    }
}