package com.clevercards.viewHolders.flashcard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.clevercards.R;
import com.clevercards.entities.Flashcard;

import java.util.List;
/**
 * Author: Morgan and Ashley
 * Created on: 12/6/2025
 * Description: Flashcard List Adapter
 */
public class FlashcardListAdapter
        extends RecyclerView.Adapter<FlashcardViewHolder> {

    private final List<Flashcard> flashcards;

    public FlashcardListAdapter(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }

    @NonNull
    @Override
    public FlashcardViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.flashcard_row_item, parent, false);

        return new FlashcardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull FlashcardViewHolder holder, int position) {

        holder.bind(flashcards.get(position));
    }

    @Override
    public int getItemCount() {
        return flashcards == null ? 0 : flashcards.size();
    }
}
