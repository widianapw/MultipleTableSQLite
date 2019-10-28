package com.example.multipletablesqlite.features.student_crud.show_student_list;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multipletablesqlite.R;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    TextView studentNameTextView;
    TextView studentNimTextView;
    TextView studentPhoneTextView;
    TextView studentEmailTextView;
    ImageView crossButtonImageView;
    ImageView editButtonImageView;

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);
        studentNimTextView = itemView.findViewById(R.id.studentNimTextView);
        studentNameTextView = itemView.findViewById(R.id.studentNameTextView);
        studentPhoneTextView = itemView.findViewById(R.id.studentPhoneTextView);
        studentEmailTextView = itemView.findViewById(R.id.studentEmailTextView);
        crossButtonImageView = itemView.findViewById(R.id.crossImageView);
        editButtonImageView = itemView.findViewById(R.id.editImageView);

    }
}
