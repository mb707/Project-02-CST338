package com.clevercards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.clevercards.database.repository.CleverCardsRepository;
import com.clevercards.databinding.ActivitySignInBinding;
import com.clevercards.entities.User;

/**
 * Author: France Zhang & Morgan Beebe (12-13-2025)
 * Created on: 12/6/2025
 * Description: SignInActivity class
 */


public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding binding;
    private CleverCardsRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // FIX: load repository safely
        repository = CleverCardsRepository.getRepository(getApplication());

        if (repository == null) {
            Toast.makeText(this, "Database failed to initialize!", Toast.LENGTH_LONG).show();
            return;   // prevents null crash
        }

        binding.signInButton.setOnClickListener(v -> verifyUser());
    }

    private void verifyUser() {
        if (repository == null) {
            toastMaker("Repository unavailable.");

            return;

        }

        String username = binding.usernameEditText.getText().toString().trim();

        if (username.isEmpty()) {
            toastMaker("username should not be blank");
            return;
        }

        LiveData<User> userObserver = repository.getUsersByUserName(username);

        userObserver.observe(this, user -> {
            if (user != null) {
                String password = binding.passwordEditText.getText().toString();

                if (password.equals(user.getPassword())) {

                    int userId = user.getUserId();
                    getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE)
                            .edit()
                            .putInt(getString(R.string.preference_userId_key), userId)
                            .apply();

                    startActivity(MainActivity.mainActivityIntentFactory(this, userId));
                    finish();

                } else {
                    toastMaker("Invalid password");
                    binding.passwordEditText.setText("");
                }

            } else {
                toastMaker(username + " is not a valid username.");
                binding.usernameEditText.setText("");
            }
        });
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent signInIntentFactory(Context context){
        return new Intent(context, SignInActivity.class);
    }
}