package com.example.multipletablesqlite.features.prodi_crud.show_prodi_list;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.multipletablesqlite.R;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    TextView fullNameTextView;
    TextView nameTextView;
    ImageView crossButtonImageView;
    ImageView editButtonImageView;

    public CustomViewHolder(View itemView) {
        super(itemView);
        fullNameTextView = itemView.findViewById(R.id.fullNameTextView);
        nameTextView = itemView.findViewById(R.id.nameTextView);
        crossButtonImageView = itemView.findViewById(R.id.crossImageView);
        editButtonImageView = itemView.findViewById(R.id.editImageView);
    }
}
