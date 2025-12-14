package com.clevercards;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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

    static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.clevercards.SAVED_INSTANCE_STATE_USERID_KEY";


    public static final String TAG = "DAC_CLEVERCARDS";
    private ActivityMainBinding binding;
    private TextView noCoursesTextView;
    private TextView userName;
    private CourseAdapter courseAdapter;
    private RecyclerView courseRecyclerView;
    private CleverCardsRepository repository;
    private CourseViewModel courseViewModel;
    private final List<Course> courseList = new ArrayList<>();

    private User user;
    private int signedInUserID = -1;
    private static final int SIGNED_OUT = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = CleverCardsRepository.getRepository(getApplication());
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        noCoursesTextView = findViewById(R.id.noCoursesTextView);
        courseRecyclerView = findViewById(R.id.courseRecyclerView);
        userName = findViewById(R.id.role_TextView);

        signInUser(savedInstanceState);
        if (signedInUserID == SIGNED_OUT){
            Intent intent = SignInActivity.signInIntentFactory(this);
            startActivity(intent);
            finish();
            return;
        }

        // 2 columns grid
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        courseRecyclerView.setLayoutManager(layoutManager);
        // Create adapter with empty list initially
        courseAdapter = new CourseAdapter(new CourseAdapter.CourseDiff(), course -> {
            // Handle click on a course tile
            courseToFlashcards(course);
        });
        courseRecyclerView.setAdapter(courseAdapter);

        courseViewModel.getAllCoursesByUserId(signedInUserID)
                .observe(this, courses -> {
                    courseAdapter.submitList(courses);
                    if (courses == null || courses.isEmpty()) {
                        noCoursesTextView.setVisibility(View.VISIBLE);
                        courseRecyclerView.setVisibility(View.GONE);
                    } else {
                        noCoursesTextView.setVisibility(View.GONE);
                        courseRecyclerView.setVisibility(View.VISIBLE);
                    }
                });

        //TODO: Create buttons for create course

        //Temporarily use the username
        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCourse();
            }
        });

        binding.btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignOutDialog();
            }
        });
    }

    private void showSignOutDialog(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setMessage("Are you sure you want to sign out?");
        alertBuilder.setPositiveButton("Sign Out", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                signOut();
                Toast.makeText(MainActivity.this, "Signed out!", Toast.LENGTH_SHORT).show();
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

    //Sign in the user logic below
    private void signInUser(Bundle savedInstanceState) {
        // check shared preference for logged in user
        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.preference_file_key),
                Context.MODE_PRIVATE
        );
        signedInUserID = sharedPreferences.getInt(getString(R.string.preference_userId_key), SIGNED_OUT);
        if (signedInUserID == SIGNED_OUT &&
                savedInstanceState != null &&
                savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)) {

            signedInUserID = savedInstanceState.getInt(
                    SAVED_INSTANCE_STATE_USERID_KEY,
                    SIGNED_OUT
            );
        }
        if (signedInUserID == SIGNED_OUT) {
            signedInUserID = getIntent().getIntExtra(
                    MAIN_ACTIVITY_USER_ID,
                    SIGNED_OUT
            );
        }
        if (signedInUserID == SIGNED_OUT) {
            return;
        }
        updatedSharedPreference();
        LiveData<User> userObserver =
                repository.getUserById(signedInUserID);
        userObserver.observe(this, user -> {
            this.user = user;
            if (this.user != null) {
                showUsername();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_STATE_USERID_KEY, signedInUserID);
        updatedSharedPreference();
    }

    private void updatedSharedPreference(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.preference_userId_key), signedInUserID);
        sharedPrefEditor.apply();
    }

    private void signOut() {
        signedInUserID = SIGNED_OUT;
        updatedSharedPreference();
        getIntent().putExtra(MAIN_ACTIVITY_USER_ID, SIGNED_OUT);
        startActivity(SignInActivity.signInIntentFactory(getApplicationContext()));
    }

    // TODO: implement this when create course logic is complete
    private void createCourse() {
        Intent intent = CreateCourseActivity.createCourseIntentFactory(
                this,
                signedInUserID
        );
        startActivity(intent);
    }

    // TODO: wire this to the flashcard activity
    private void courseToFlashcards(Course course) {
        Toast.makeText(this, course.getCourseName(), Toast.LENGTH_SHORT).show();
    }

    private void showUsername(){
        if (user != null){
            userName.setText(user.getUsername());
        }
    }


    // Intent factory to be used by other views
    static Intent mainActivityIntentFactory(Context context, int userID){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userID);
        return intent;
    }


}