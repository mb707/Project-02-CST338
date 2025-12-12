package com.clevercards.viewHolders.course;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.clevercards.entities.Course;

public class CourseAdapter extends ListAdapter<Course, CourseViewHolder> {

    public interface OnCourseClickListener {
        void onCourseClick(Course course);
    }

    private final OnCourseClickListener listener;

    public CourseAdapter(@NonNull DiffUtil.ItemCallback<Course> diffCallback,
                         OnCourseClickListener listener) {
        super(diffCallback);
        this.listener = listener;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return CourseViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course current = getItem(position);
        holder.bind(current, listener);
    }

    public static class CourseDiff extends DiffUtil.ItemCallback<Course> {

        @Override
        public boolean areItemsTheSame(@NonNull Course oldItem, @NonNull Course newItem) {
            // If you have a unique ID field, compare that instead
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Course oldItem, @NonNull Course newItem) {
            return oldItem.equals(newItem);
        }
    }
}

