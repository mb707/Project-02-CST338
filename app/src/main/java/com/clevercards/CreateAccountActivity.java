package com.clevercards;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.clevercards.database.repository.CleverCardsRepository;
import com.clevercards.databinding.ActivityCreateAccountBinding;
import com.clevercards.entities.User;

/**
 * @author Ashley Wozow
 * created: 12/14/25
 * In this Create Users Activity, the logic for adding a new user is handled.
 */
public class CreateAccountActivity extends AppCompatActivity {
    private static final String CREATE_USERS_USER_ID =
            "com.clevercards.CREATE_USERS_USER_ID ";

    private ActivityCreateAccountBinding binding;
    private User selectedUser = null;
    private CleverCardsRepository repository;
    private int currentUserId = -1;
    private int newUserId = -1;

    private boolean isAdmin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = CleverCardsRepository.getRepository(getApplication());
        currentUserId = getIntent().getIntExtra(CREATE_USERS_USER_ID, -1);
        createdByAdmin();



        if (createdByAdmin()){
            binding.adminSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    // The switch is enabled
                    Toast.makeText(this, "Granted Admin Access", Toast.LENGTH_SHORT).show();
                    isAdmin = true;
                } else {
                    // The switch is disabled
                    Toast.makeText(this, "Not Granted Admin Access", Toast.LENGTH_SHORT).show();
                    isAdmin = false;
                }
            });
            }

        binding.signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(isAdmin);
            }
        });

    }

    private boolean createdByAdmin(){
        if (currentUserId == -1){ // created by new user
            binding.adminSwitch.setVisibility(View.GONE);
            return false;
        }
        else { // created by an Admin - granted the ability to make the new user an admin
            binding.adminSwitch.setVisibility(View.VISIBLE);
            binding.welcomeTextView.setText(R.string.create_new_user);
            binding.signInButton.setText(R.string.submit);
            return true;
        }
    }

    private void signIn(boolean isAdmin){
        String username, password;
        username = binding.createUsernameTextView.getText().toString();
        password = binding.createPasswordEditText.getText().toString();
        if (username.isEmpty()) {
            toastMaker("Username must not be blank");
            return;
        }
        if (password.isEmpty()){
            toastMaker("Password must not be blank");
            return;
        }
        User newUser = new User(username, password, isAdmin);
        repository.insertUser(newUser);
        routeUserToMainUnlessAdminCreated(newUser);
    }

    private void routeUserToMainUnlessAdminCreated(User newUser){
        if (currentUserId != -1){
            finish();
        }
        else{
            LiveData<User> userObserver = repository.getUsersByUserName(newUser.getUsername());
            userObserver.observe(this, user -> {
                if (user != null) {
                        newUserId = user.getUserId();
                        // Save the logged-in userId so it persists
                        getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE)
                                .edit()
                                .putInt(getString(R.string.preference_userId_key), newUserId)
                                .apply();
                        // Start MainActivity with this userId as well
                        startActivity(
                                MainActivity.mainActivityIntentFactory(
                                        this,
                                        newUserId
                                )
                        );
                        finish();
                }
            });
        }
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    static Intent createUsersIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, CreateAccountActivity.class);
        intent.putExtra(CREATE_USERS_USER_ID, userId);
        return intent;
    }
}