package com.undev.schoolmanagementsystem;

import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderData extends RecyclerView.ViewHolder {
    public TextView name, roll,typex;
    public CardView cardView;

    public ViewHolderData(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.viewName);
        roll = itemView.findViewById(R.id.viewRoll);
        typex = itemView.findViewById(R.id.typex);
        cardView = itemView.findViewById(R.id.cardView);
    }
}
