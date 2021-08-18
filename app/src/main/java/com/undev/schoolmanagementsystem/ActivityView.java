package com.undev.schoolmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.undev.schoolmanagementsystem.databinding.ActivityViewBinding;

public class ActivityView extends AppCompatActivity {

    ActivityViewBinding binding;

    FirebaseRecyclerOptions<ModelInput> options;
    DatabaseReference reference;
    FunctionCaller functionCaller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        functionCaller = new FunctionCaller(this);
        if (getIntent().getStringExtra("type").equals("murid"))
            reference = FirebaseDatabase.getInstance().getReference().child("Student");
        else if (getIntent().getStringExtra("type").equals("karyawan"))
            reference = FirebaseDatabase.getInstance().getReference().child("Employee");
        else
            reference = FirebaseDatabase.getInstance().getReference().child("Teacher");
        options = new FirebaseRecyclerOptions.Builder<ModelInput>().setQuery(reference, ModelInput.class).build();
        FunctionCaller.viewData(options, binding.recView);

        binding.seachView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                FunctionCaller.searchIndex(query, reference, binding.recView);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                FunctionCaller.searchIndex(newText, reference, binding.recView);
                return false;
            }
        });
    }
}
