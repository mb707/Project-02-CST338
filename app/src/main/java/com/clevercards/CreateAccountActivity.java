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
                    Toast.makeText(this, "Feature Enabled", Toast.LENGTH_SHORT).show();
                    isAdmin = true;
                    // Perform actions when the switch is ON
                } else {
                    // The switch is disabled
                    Toast.makeText(this, "Feature Disabled", Toast.LENGTH_SHORT).show();
                    isAdmin = false;
                    // Perform actions when the switch is OFF
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
        else { // created by an Admin
            binding.adminSwitch.setVisibility(View.VISIBLE);
            binding.welcomeTextView.setText("Create New User");
            binding.signInButton.setText("Submit");
            return true;
        }
    }

    private void signIn(boolean isAdmin){
        String name, password;
        name = binding.createUsernameTextView.getText().toString();
        password = binding.createPasswordEditText.getText().toString();
        User newUser = new User(name, password, isAdmin);
        repository.insertUser(newUser);
        finish();
    }


    static Intent createUsersIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, CreateAccountActivity.class);
        intent.putExtra(CREATE_USERS_USER_ID, userId);
        return intent;
    }
}