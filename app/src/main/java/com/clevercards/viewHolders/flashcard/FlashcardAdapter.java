package com.clevercards.viewHolders.flashcard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.clevercards.R;
import com.clevercards.entities.Flashcard;

public class FlashcardAdapter extends ListAdapter<Flashcard, FlashcardAdapter.FlashcardViewHolder> {

    public interface OnFlashcardClickListener {
        void onFlashcardClick(Flashcard flashcard);
    }

    private final OnFlashcardClickListener listener;

    public FlashcardAdapter(@NonNull DiffUtil.ItemCallback<Flashcard> diffCallback,
                            OnFlashcardClickListener listener) {
        super(diffCallback);
        this.listener = listener;
    }

    @NonNull
    @Override
    public FlashcardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.flashcard_recycler_item, parent, false);
        return new FlashcardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlashcardViewHolder holder, int position) {
        Flashcard flashcard = getItem(position);
        holder.bind(flashcard, listener);
    }

    static class FlashcardViewHolder extends RecyclerView.ViewHolder {

        private final TextView front_TextView;
        private final TextView back_TextView;

        public FlashcardViewHolder(@NonNull View itemView) {
            super(itemView);
            front_TextView = itemView.findViewById(R.id.frontTextView);
            back_TextView = itemView.findViewById(R.id.backTextView);
        }

        public void bind(Flashcard flashcard, OnFlashcardClickListener listener) {
            front_TextView.setText(flashcard.getFrontText());
            back_TextView.setText(flashcard.getBackText());

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onFlashcardClick(flashcard);
                }
            });
        }
    }

    public static class FlashcardDiff extends DiffUtil.ItemCallback<Flashcard> {
        @Override
        public boolean areItemsTheSame(@NonNull Flashcard oldItem, @NonNull Flashcard newItem) {
            return oldItem.getFlashcardId() == newItem.getFlashcardId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Flashcard oldItem, @NonNull Flashcard newItem) {
            return oldItem.equals(newItem);
        }
    }
}

