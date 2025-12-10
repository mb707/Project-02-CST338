package com.clevercards;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.clevercards.database.repository.CleverCardsRepository;
import com.clevercards.databinding.ActivityCreateCourseBinding;
import com.clevercards.entities.User;
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

    private static final String EXTRA_USER_ID =
            "com.clevercards.CREATE_COURSE_USER_ID";

    private ActivityCreateCourseBinding binding;
    private CourseViewModel courseViewModel;
    private int userId = -1;

    private CleverCardsRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = CleverCardsRepository.getRepository(getApplication());

        courseName = findViewById(R.id.courseNameEditText);
        numberOfCards = findViewById(R.id.numberOfCardsEditText);
        signOutbutton = findViewById(R.id.btnSignOut);



    }

    private void backButtonAction(){
        //TODO: wire this to dashboard activity
    }

    private void nextButton(){
        //TODO: once flashcard activity is made, wire this there
    }

    private void signOut() {
       //TODO: wire this to sign in page
    }


    // Intent factory to be used by other views
    static Intent createCourseIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, CreateCourseActivity.class);
        intent.putExtra(EXTRA_USER_ID, userId);
        return intent;
    }
}