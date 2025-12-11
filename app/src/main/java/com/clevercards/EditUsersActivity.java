package com.clevercards;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.clevercards.database.repository.CleverCardsRepository;
import com.clevercards.databinding.ActivityCreateCourseBinding;
import com.clevercards.databinding.ActivityEditUsersBinding;
import com.clevercards.entities.Course;
import com.clevercards.entities.User;
import com.clevercards.viewHolders.CourseViewModel;

public class EditUsersActivity extends AppCompatActivity {

    private static final String EDIT_USERS_USER_ID =
            "com.clevercards.EDIT_USERS_USER_ID ";

    private CourseViewModel courseViewModel;

    private User user;
    private int userId = -1;

    private CleverCardsRepository repository;

    private ActivityEditUsersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = CleverCardsRepository.getRepository(getApplication());
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        userId = 2;



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