package com.clevercards.viewHolders.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.clevercards.R;
import com.clevercards.entities.User;

public class UserAdapter extends ListAdapter<User, UserAdapter.UserViewHolder> {

    public interface OnUserClickListener {
        void onUserClick(User user);
    }

    private final OnUserClickListener listener;
    private int selectedUserId = -1;

    public void setSelectedUserId(int userId) {
        selectedUserId = userId;
        notifyDataSetChanged();
    }

    public UserAdapter(@NonNull DiffUtil.ItemCallback<User> diffCallback,
                       OnUserClickListener listener) {
        super(diffCallback);
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_recycler_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = getItem(position);
        holder.bind(user, listener, selectedUserId);
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        private final TextView username_TextView;
        private final TextView userRole_TextView;

        private final CheckBox cbSelectedUser;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            username_TextView = itemView.findViewById(R.id.username_TextView);
            userRole_TextView = itemView.findViewById(R.id.userRole_TextView);
            cbSelectedUser = itemView.findViewById(R.id.cbSelectedUser);
        }

        public void bind(User user, OnUserClickListener listener, int selectedUserId) {
            username_TextView.setText(user.getUsername());
            userRole_TextView.setText(user.isAdmin() ? "Role: admin" : "Role: user");
            cbSelectedUser.setOnCheckedChangeListener(null);

            boolean isSelected = (user.getUserId() == selectedUserId);
            cbSelectedUser.setChecked(isSelected);

            View.OnClickListener clickListener = v -> {
                if (listener != null) {
                    listener.onUserClick(user);
                }
            };
            itemView.setOnClickListener(clickListener);
            cbSelectedUser.setOnClickListener(clickListener);
        }
    }

    public static class UserDiff extends DiffUtil.ItemCallback<User> {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getUserId() == newItem.getUserId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            // If you don't override equals in User, compare fields manually:
            return oldItem.equals(newItem);
        }
    }
}

