package com.clevercards.viewHolders.flashcard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.clevercards.R;
import com.clevercards.entities.Flashcard;

import java.util.List;

public class FlashcardListAdapter extends RecyclerView.Adapter<FlashcardListAdapter.ViewHolder> {
    //TODO: add missing elements for adapter and viewholder
    // For reference look at UserAdapter since it follows similar structure

    private final List<Flashcard> flashcards;

    public FlashcardListAdapter(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.flashcard_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Flashcard card = flashcards.get(position);
        holder.front.setText(card.getFrontText());
        holder.back.setText(card.getBackText());
    }

    @Override
    public int getItemCount() {
        return flashcards.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView front, back;

        ViewHolder(View itemView) {
            super(itemView);
            front = itemView.findViewById(R.id.frontTextView);
            back = itemView.findViewById(R.id.backTextView);
        }
    }
}
