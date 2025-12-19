package com.clevercards.viewHolders.course;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.clevercards.R;
import com.clevercards.entities.Course;


/**
 * @author Ashley Wozow
 * created: 12/6/25
 * CourseViewHolder handles the logic necessary to utilze the recycler view on the front end side
 */
public class CourseViewHolder extends RecyclerView.ViewHolder {

    private final Button courseButton;

    private CourseViewHolder(View itemView) {
        super(itemView);
        courseButton = itemView.findViewById(R.id.course_Button);
    }

    public void bind(Course course, CourseAdapter.OnCourseClickListener listener) {
        courseButton.setText(course.getCourseName());

        courseButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCourseClick(course);
            }
        });
    }

    static CourseViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_recycler_item, parent, false);
        return new CourseViewHolder(view);
    }
}
