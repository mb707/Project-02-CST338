package com.clevercards;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.clevercards.database.CleverCardsDatabase;
import com.clevercards.database.repository.CleverCardsRepository;

/**
 * Author: France Zhang
 * Created on: 12/6/2025
 * Description: LoginActivity class
 */


public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    private CleverCardsRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        repository = CleverCardsRepository.getRepository(getApplication());

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        // For Part 2: simple placeholder behavior
        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            Toast.makeText(
                    this,
                    "Login clicked: " + username,
                    Toast.LENGTH_SHORT
            ).show();
        });
    }
}
