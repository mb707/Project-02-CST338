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
 * Description: Flashcard View Holder hold references to the front and back text
 */
public class FlashcardViewHolder extends RecyclerView.ViewHolder {
    // TextView for the front side of the flashcard
    private final TextView frontTextView;
    // TextView for the back side of the flashcard
    private final TextView backTextView;
    // Root card view used to detect clicks
    private final View cardView;
    // Tracks which side of the card is currently visible
    private boolean showingFront = true;
    // Constructor binds layout views
    public FlashcardViewHolder(@NonNull View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.flashcardCard);
        frontTextView = itemView.findViewById(R.id.frontTextView);
        backTextView = itemView.findViewById(R.id.backTextView);
    }
    // Binds flashcard data and sets up flip behavior
    public void bind(Flashcard flashcard) {
        // RESET STATE (CRITICAL)
        showingFront = true;
        frontTextView.setText(flashcard.getFrontText());
        backTextView.setText(flashcard.getBackText());
        frontTextView.setVisibility(View.VISIBLE);
        backTextView.setVisibility(View.GONE);
        // Flip card on click
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
