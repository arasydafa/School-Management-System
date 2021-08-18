package com.undev.schoolmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.undev.schoolmanagementsystem.databinding.ActivityInputBinding;

import java.util.Calendar;
import java.util.Date;

public class ActivityInput extends AppCompatActivity {

    ActivityInputBinding binding;
    FunctionCaller functionCaller;
    Thread thread;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInputBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        functionCaller = new FunctionCaller(this);
        FunctionCaller.setDate(binding.date);

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

        exit(binding.close);
        exit(binding.btexit);
        exit(binding.home);
        binding.btdelete.setEnabled(false);
        binding.btupdate.setEnabled(false);

        if (getIntent().getStringExtra("type").equals("murid")) {
            binding.posisix.setVisibility(View.GONE);
            users = FirebaseDatabase.getInstance().getReference("Student");
            getSupportActionBar().setTitle("Student's Information System");
            binding.btsave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(binding.classrollnumber.getText().toString()))
                        FunctionCaller.topSnackBar(binding.rootLayout, "Class Roll Number tidak boleh kosong!", null);
                    else {
                        users.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                ModelInput input = new ModelInput();
                                input.setClassrollnumber(binding.classrollnumber.getText().toString());
                                input.setFname(binding.fname.getText().toString());
                                input.setLname(binding.lname.getText().toString());
                                input.setClassname(binding.classname.getText().toString());
                                input.setYear(binding.year.getText().toString());
                                input.setBday(binding.birthday.getText().toString());
                                input.setPhone(binding.phone.getText().toString());
                                input.setSex(binding.spinjk.getSelectedItem().toString());
                                input.setPermaddress(binding.permanentaddress.getText().toString());
                                input.setCurraddress(binding.curaddress.getText().toString());
                                if (dataSnapshot.child(input.getClassrollnumber()).exists())
                                    FunctionCaller.topSnackBar(binding.rootLayout, "Class Roll Number is already used!", null);
                                else {
                                    users.child(input.getClassrollnumber()).setValue(input);
                                    Toast.makeText(ActivityInput.this, "Data Saved Successfully!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            });

            binding.btview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ActivityInput.this, ActivityView.class).putExtra("type", "murid"));
                    finish();
                }
            });
            load("classrollnumber");
        } else if (getIntent().getStringExtra("type").equals("karyawan")) {
            binding.yearx.setVisibility(View.GONE);
            binding.classnamex.setVisibility(View.GONE);
            binding.classrollnumberx.setHint("Employee ID");
            getSupportActionBar().setTitle("Employee's Information System");
            users = FirebaseDatabase.getInstance().getReference("Employee");
            binding.btsave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(binding.classrollnumber.getText().toString()))
                        FunctionCaller.topSnackBar(binding.rootLayout, "Employee ID cannot be empty!", null);
                    else {
                        users.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                ModelInput input = new ModelInput();
                                input.setEmployeeid(binding.classrollnumber.getText().toString());
                                input.setFname(binding.fname.getText().toString());
                                input.setLname(binding.lname.getText().toString());
                                input.setPosition(binding.posisi.getText().toString());
                                input.setBday(binding.birthday.getText().toString());
                                input.setPhone(binding.phone.getText().toString());
                                input.setSex(binding.spinjk.getSelectedItem().toString());
                                input.setPermaddress(binding.permanentaddress.getText().toString());
                                input.setCurraddress(binding.curaddress.getText().toString());
                                if (dataSnapshot.child(input.getEmployeeid()).exists())
                                    FunctionCaller.topSnackBar(binding.rootLayout, "Employee ID is already used!", null);
                                else {
                                    users.child(input.getEmployeeid()).setValue(input);
                                    Toast.makeText(ActivityInput.this, "Data Saved Successfully!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            });
            binding.btview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ActivityInput.this, ActivityView.class).putExtra("type", "karyawan"));
                }
            });
            load("employeeid");
        } else {
            binding.yearx.setVisibility(View.GONE);
            binding.classnamex.setVisibility(View.GONE);
            binding.classrollnumberx.setHint("Teacher ID");
            getSupportActionBar().setTitle("Teacher's Information System");
            users = FirebaseDatabase.getInstance().getReference("Teacher");

            binding.btsave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(binding.classrollnumber.getText().toString()))
                        FunctionCaller.topSnackBar(binding.rootLayout, "Teacher ID cannot be empty!", null);
                    else {
                        users.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                ModelInput input = new ModelInput();
                                input.setTeacherid(binding.classrollnumber.getText().toString());
                                input.setFname(binding.fname.getText().toString());
                                input.setLname(binding.lname.getText().toString());
                                input.setPosition(binding.posisi.getText().toString());
                                input.setBday(binding.birthday.getText().toString());
                                input.setPhone(binding.phone.getText().toString());
                                input.setSex(binding.spinjk.getSelectedItem().toString());
                                input.setPermaddress(binding.permanentaddress.getText().toString());
                                input.setCurraddress(binding.curaddress.getText().toString());
                                if (dataSnapshot.child(input.getTeacherid()).exists())
                                    FunctionCaller.topSnackBar(binding.rootLayout, "Teacher ID is already used!", null);
                                else {
                                    users.child(input.getTeacherid()).setValue(input);
                                    Toast.makeText(ActivityInput.this, "Data Saved Successfully!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            });

            binding.btview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ActivityInput.this, ActivityView.class).putExtra("type", "guru"));
                }
            });

            load("teacherid");
        }

        binding.btdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                users.child(binding.classrollnumber.getText().toString()).removeValue();
                binding.btdelete.setEnabled(false);
                binding.btupdate.setEnabled(false);
                Toast.makeText(ActivityInput.this, "Data Deleted Successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.classname.setText("");
                binding.fname.setText("");
                binding.lname.setText("");
                binding.classrollnumber.setText("");
                binding.year.setText("");
                binding.birthday.setText("");
                binding.posisi.setText("");
                binding.phone.setText("");
                binding.permanentaddress.setText("");
                binding.curaddress.setText("");
                binding.spinjk.setSelection(0);
            }
        });

        binding.birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FunctionCaller.datePicker(binding.birthday);
            }
        });
    }

    private void load(final String typeID) {
        if (getIntent().getStringExtra("roll") != null) {
            FunctionCaller.ref(users.child(getIntent().getStringExtra("roll")),
                    "bday", binding.birthday, null);
            FunctionCaller.ref(users.child(getIntent().getStringExtra("roll")),
                    "classname", binding.classname, null);
            FunctionCaller.ref(users.child(getIntent().getStringExtra("roll")),
                    "position", binding.posisi, null);
            FunctionCaller.ref(users.child(getIntent().getStringExtra("roll")),
                    typeID, binding.classrollnumber, null);
            FunctionCaller.ref(users.child(getIntent().getStringExtra("roll")),
                    "curraddress", binding.curaddress, null);
            FunctionCaller.ref(users.child(getIntent().getStringExtra("roll")),
                    "fname", binding.fname, null);
            FunctionCaller.ref(users.child(getIntent().getStringExtra("roll")),
                    "lname", binding.lname, null);
            FunctionCaller.ref(users.child(getIntent().getStringExtra("roll")),
                    "permaddress", binding.permanentaddress, null);
            FunctionCaller.ref(users.child(getIntent().getStringExtra("roll")),
                    "phone", binding.phone, null);
            FunctionCaller.ref(users.child(getIntent().getStringExtra("roll")),
                    "year", binding.year, null);
            FunctionCaller.ref(users.child(getIntent().getStringExtra("roll")),
                    "sex", null, binding.spinjk);
            binding.btsave.setEnabled(false);
            binding.btclear.setEnabled(false);
            binding.btdelete.setEnabled(true);
            binding.btupdate.setEnabled(true);
            binding.classrollnumber.setEnabled(false);
            binding.btupdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FunctionCaller.updateProfil(users.child(getIntent().getStringExtra("roll")),
                            binding.birthday, binding.classname, binding.classrollnumber, binding.curaddress,
                            binding.fname, binding.lname, binding.permanentaddress, binding.phone, binding.year,
                            binding.posisi, binding.spinjk, typeID);
                }
            });
        }
    }

    private void exit(MaterialButton materialButton) {
        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
