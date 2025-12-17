package com.clevercards.viewHolders.user;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.clevercards.database.repository.CleverCardsRepository;
import com.clevercards.entities.User;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private final CleverCardsRepository repository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = CleverCardsRepository.getRepository(application);
    }

    public LiveData<List<User>> getAllUsers() {
        return repository.getAllUsers();
    }

    public void insert(User... users) {
        repository.insertUser(users);
    }
}