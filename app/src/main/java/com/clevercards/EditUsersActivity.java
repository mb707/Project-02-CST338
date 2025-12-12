package com.clevercards;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.vo.Warning;

import com.clevercards.database.CleverCardsDatabase;
import com.clevercards.database.dao.CourseDao;
import com.clevercards.database.repository.CleverCardsRepository;
import com.clevercards.databinding.ActivityEditUsersBinding;
import com.clevercards.entities.Course;
import com.clevercards.entities.User;
import com.clevercards.viewHolders.course.CourseViewModel;
import com.clevercards.viewHolders.user.UserAdapter;
import com.clevercards.viewHolders.user.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class EditUsersActivity extends AppCompatActivity {

    private static final String EDIT_USERS_USER_ID =
            "com.clevercards.EDIT_USERS_USER_ID ";

    private User user;
    private User selectedUser = null;
    private int userId = -1;

    private List<Course> allCourses = new ArrayList<>();

    private CleverCardsRepository repository;

    private CourseViewModel courseViewModel;

    private UserViewModel userViewModel;
    private UserAdapter userAdapter;

    private ActivityEditUsersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = CleverCardsRepository.getRepository(getApplication());
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        userId = getIntent().getIntExtra(EDIT_USERS_USER_ID, -1);
        if (userId == -1) {
            userId = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE)
                    .getInt(getString(R.string.preference_userId_key), -1);
        }

        courseViewModel.getAllCoursesLD().observe(this, courses -> {
            allCourses = courses;
        });

        setupRecyclerView();
        setupButtons();

    }

    private void setupRecyclerView() {
        userAdapter = new UserAdapter(new UserAdapter.UserDiff(), this::onUserClicked);
        binding.userRecyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );
        binding.userRecyclerView.setAdapter(userAdapter);

        // Observe LiveData from ViewModel
        userViewModel.getAllUsers().observe(this, users -> {
            userAdapter.submitList(users);
        });
    }

    private void setupButtons() {
        binding.addUserButton.setOnClickListener(v -> {
            addUser();
        });

        binding.deleteUserButton.setOnClickListener(v -> {
            deleteUser();
        });

        binding.assignCourseToUserButton.setOnClickListener(v -> {
            assignCourseToUser();
        });

        binding.signoutButton.setOnClickListener(v -> {
            // You can reuse your MainActivity sign-out logic here later if you want
            startActivity(SignInActivity.signInIntentFactory(this));
            finish();
        });
    }

    private void addUser(){
        selectedUser = null;
        userAdapter.setSelectedUserId(-1);
        Toast.makeText(this, "Add user clicked", Toast.LENGTH_SHORT).show();
    }

    private void deleteUser(){
        if (selectedUser == null) {
            Toast.makeText(this, "Select a user first", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedUser.isAdmin()){
            Toast.makeText(this, "Selected user must not be an admin", Toast.LENGTH_SHORT).show();
            selectedUser = null;
            userAdapter.setSelectedUserId(-1);
            return;
        }
        new AlertDialog.Builder(this)
                .setTitle("Delete user")
                .setMessage("Are you sure you want to delete " + selectedUser.getUsername() + "?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    repository.deleteUser(selectedUser);
                    // Clear selection
                    selectedUser = null;
                    userAdapter.setSelectedUserId(-1);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void assignCourseToUser(){
        if (selectedUser == null) {
            Toast.makeText(this, "Select a user first", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedUser.isAdmin()){
            Toast.makeText(this,
                    "Can't assign courses to other admins, you selected " + selectedUser.getUsername(),
                    Toast.LENGTH_SHORT).show();
            selectedUser = null;
            userAdapter.setSelectedUserId(-1);
            return;
        }
        if (allCourses == null || allCourses.isEmpty()) {
            Toast.makeText(this, "No courses available to assign", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this,
                "Assigning course to " + selectedUser.getUsername(),
                Toast.LENGTH_SHORT).show();

        showAssignCourseDialog();

    }

    private void showAssignCourseDialog() {
        // Convert courses to a simple list of names for the dialog
        String[] courseNames = new String[allCourses.size()];
        for (int i = 0; i < allCourses.size(); i++) {
            courseNames[i] = allCourses.get(i).getCourseName(); // adjust getter if needed
        }

        final int[] selectedIndex = { -1 };

        new AlertDialog.Builder(this)
                .setTitle("Assign course to " + selectedUser.getUsername())
                .setSingleChoiceItems(courseNames, -1, (dialog, which) -> {
                    selectedIndex[0] = which;
                })
                .setPositiveButton("Assign", (dialog, which) -> {
                    if (selectedIndex[0] == -1) {
                        Toast.makeText(this, "A course must be selected!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Course chosenCourse = allCourses.get(selectedIndex[0]);

                    repository.assignCourseWithFlashcardsToUser(
                            chosenCourse.getCourseId(),
                            selectedUser.getUserId()
                    );
                    Toast.makeText(this,
                            "Assigned " + chosenCourse.getCourseName()
                                    + " to " + selectedUser.getUsername(),
                            Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void onUserClicked(User user) {
        // Toggle selection: clicking the same user again unselects it
        if (selectedUser != null && selectedUser.getUserId() == user.getUserId()) {
            selectedUser = null;
            userAdapter.setSelectedUserId(-1);
        } else {
            selectedUser = user;
            userAdapter.setSelectedUserId(user.getUserId());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_user_dashboard_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem returnToDashboard = menu.findItem(R.id.dashboard);
        if (returnToDashboard != null) returnToDashboard.setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.dashboard) {
            startActivity(MainActivity.mainActivityIntentFactory(this,userId));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    // Intent factory to be used by other views
    static Intent editUsersIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, EditUsersActivity.class);
        intent.putExtra(EDIT_USERS_USER_ID, userId);
        return intent;
    }
}