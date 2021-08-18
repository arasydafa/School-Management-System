package com.undev.schoolmanagementsystem;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    public TextView textView;

    public ViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.namaProfil);
    }
}
