package com.undev.schoolmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.undev.schoolmanagementsystem.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    FirebaseAuth auth;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        users = FirebaseDatabase.getInstance().getReference("Admin");
        binding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(binding.uname.getText().toString()))
                    FunctionCaller.topSnackBar(binding.rootLayout, "Username tidak boleh kosong!", null);
                else if (TextUtils.isEmpty(binding.katasandi.getText().toString()))
                    FunctionCaller.topSnackBar(binding.rootLayout, "Kata sandi tidak boleh kosong!", null);
                else {
                    users.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(binding.uname.getText().toString()).exists()) {
                                ModelAdmin admin = dataSnapshot.child(binding.uname.getText().toString()).getValue(ModelAdmin.class);
                                if (admin.getPassword().equals(binding.katasandi.getText().toString())) {
                                    startActivity(new Intent(LoginActivity.this, ActivityMain.class));
                                    Toast.makeText(LoginActivity.this, "Login Sukses", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else
                                    FunctionCaller.topSnackBar(binding.rootLayout, "Kata Sandi Salah!", null);
                            } else
                                FunctionCaller.topSnackBar(binding.rootLayout, "Username tidak ditemukan!", null);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            }
        });
    }
}
