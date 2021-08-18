package com.undev.schoolmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;

import com.undev.schoolmanagementsystem.databinding.ActivityMainBinding;

import java.util.Calendar;
import java.util.Date;

public class ActivityMain extends AppCompatActivity {

    ActivityMainBinding binding;
    FunctionCaller functionCaller;
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        functionCaller = new FunctionCaller(this);
        FunctionCaller.setDate(binding.date);
        start(binding.guru, "guru");
        start(binding.karyawan, "karyawan");
        start(binding.murid, "murid");

        thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!thread.isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Date noteTS = Calendar.getInstance().getTime();
                                String time = "hh:mm:ss";
                                binding.time.setText(DateFormat.format(time, noteTS));
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        thread.start();

        binding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityMain.this, ActivityAbout.class));
            }
        });
    }

    private void start(CardView cardView, final String type) {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityMain.this, ActivityInput.class).putExtra("type", type));
            }
        });
    }
}
