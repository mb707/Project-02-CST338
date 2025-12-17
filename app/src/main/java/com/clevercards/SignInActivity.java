package com.clevercards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.clevercards.database.repository.CleverCardsRepository;
import com.clevercards.databinding.ActivitySignInBinding;
import com.clevercards.entities.User;

/**
 * Author: France Zhang
 * Created on: 12/6/2025
 * Description: SignInActivity class
 */


public class SignInActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button signInButton;
    private ActivitySignInBinding binding;

    private CleverCardsRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = CleverCardsRepository.getRepository(getApplication());

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signInButton = findViewById(R.id.signInButton);

        // For Part 2: simple placeholder behavior
        signInButton.setOnClickListener(v -> {
            verifyUser();
        });

        binding.createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccountActivity();
            }
        });


    }

    private void verifyUser() {
        String username = binding.usernameEditText.getText().toString();

        if (username.isEmpty()) {
            toastMaker("Username must not be blank");
            return;
        }
        LiveData<User> userObserver = repository.getUsersByUserName(username);
        userObserver.observe(this, user -> {
            if (user != null) {
                String password = binding.passwordEditText.getText().toString();
                if (password.equals(user.getPassword())) {
                    int userId = user.getUserId();

                    // Save the logged-in userId so it persists
                    getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE)
                            .edit()
                            .putInt(getString(R.string.preference_userId_key), userId)
                            .apply();

                    // Start MainActivity with this userId as well
                    startActivity(
                            MainActivity.mainActivityIntentFactory(
                                    this,
                                    userId
                            )
                    );
                    finish();
                } else {
                    toastMaker("Invalid password");
                    binding.passwordEditText.setSelection(0);
                }
            } else {
                toastMaker(String.format("%s is not a valid username.", username));
                binding.usernameEditText.setSelection(0);
            }
        });
    }

    private void createAccountActivity(){
        Intent intent = CreateAccountActivity.createUsersIntentFactory(this, -1);
        startActivity(intent);
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Intent factory to be used by other views
    static Intent signInIntentFactory(Context context){
        return new Intent(context, SignInActivity.class);
    }
}
