package com.clevercards.viewHolders.flashcard;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.clevercards.R;
import com.clevercards.entities.Flashcard;
/**
 * Author: Morgan and Ashley
 * Created on: 12/6/2025
 * Description: Flashcard View Holder
 */

public class FlashcardViewHolder extends RecyclerView.ViewHolder {

    private final TextView frontTextView;
    private final TextView backTextView;
    private final View cardView;

    private boolean showingFront = true;

    public FlashcardViewHolder(@NonNull View itemView) {
        super(itemView);

        cardView = itemView.findViewById(R.id.flashcardCard);
        frontTextView = itemView.findViewById(R.id.frontTextView);
        backTextView = itemView.findViewById(R.id.backTextView);
    }

    public void bind(Flashcard flashcard) {
        showingFront = true;

        frontTextView.setText(flashcard.getFrontText());
        backTextView.setText(flashcard.getBackText());

        frontTextView.setVisibility(View.VISIBLE);
        backTextView.setVisibility(View.GONE);

        cardView.setOnClickListener(v -> {
            if (showingFront) {
                frontTextView.setVisibility(View.GONE);
                backTextView.setVisibility(View.VISIBLE);
            } else {
                frontTextView.setVisibility(View.VISIBLE);
                backTextView.setVisibility(View.GONE);
            }
            showingFront = !showingFront;
        });
    }
}
