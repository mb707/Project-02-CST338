package com.clevercards;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.clevercards.databinding.ActivityMainBinding;
import com.clevercards.entities.Course;
import com.clevercards.viewHolders.CourseAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER_ID = "com.clevercards.MAIN_ACTIVITY_USER_ID";
    private ActivityMainBinding binding;
    private TextView noCoursesTextView;
    private CourseAdapter courseAdapter;
    private RecyclerView courseRecyclerView;
    private final List<Course> courseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        noCoursesTextView = findViewById(R.id.noCoursesTextView);
        courseRecyclerView = findViewById(R.id.courseRecyclerView);


        // 2 columns grid
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        courseRecyclerView.setLayoutManager(layoutManager);

        // Create adapter with empty list initially
        courseAdapter = new CourseAdapter(new CourseAdapter.CourseDiff(), course -> {
            // Handle click on a course tile
            courseToFlashcards(course);
        });

        courseRecyclerView.setAdapter(courseAdapter);

        // For now: load some fake data so you can see it working
        loadFakeCourses();
        refreshCoursesUI();

        //TODO: Wired up sign out functionality
        // This should route the user back to the login page
        binding.btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Signed Out!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    // TODO: implement this when create course logic is complete
    private void createCourse(Course course) {
        courseList.add(course);
        refreshCoursesUI();
    }

    // For testing purposes
    private void loadFakeCourses() {
        // TODO: remove when real data is ready and replace with a call from the DB
        courseList.add(new Course("CST 338", 10));
        courseList.add(new Course("CST 300", 20));
        courseList.add(new Course("CST 349", 15));
//        courseList.add(new Course("CST 364", 25));
//        courseList.add(new Course("CST 499", 50));
//        courseList.add(new Course("CST 493", 50));
//        courseList.add(new Course("CST 492", 50));
//        courseList.add(new Course("CST 491", 50));
//        courseList.add(new Course("CST 490", 50));
//        courseList.add(new Course("CST 495", 50));
    }

    private void refreshCoursesUI() {
        if (courseList.isEmpty()) {
            noCoursesTextView.setVisibility(View.VISIBLE);
            courseRecyclerView.setVisibility(View.GONE);
        } else {
            noCoursesTextView.setVisibility(View.GONE);
            courseRecyclerView.setVisibility(View.VISIBLE);
        }

        // ListAdapter wants a new list instance when updating
        List<Course> copy = new ArrayList<>(courseList);
        courseAdapter.submitList(copy);
    }

    // TODO: wire this to the flashcard activity
    private void courseToFlashcards(Course course) {
        Toast.makeText(this, course.getCourseName(), Toast.LENGTH_SHORT).show();
    }



    // Intent factory to be used by other views
    static Intent mainActivityIntentFactory(Context context, int userID){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userID);
        return intent;
    }


}