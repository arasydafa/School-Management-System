package com.undev.schoolmanagementsystem;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FunctionCaller {

    private static Context context;

    public FunctionCaller(Context context) {
        this.context = context;
    }

    public static void topSnackBar(RelativeLayout relativeLayout, String alert, String colorCode) {
        TSnackbar tSnackbar = TSnackbar.make(relativeLayout, alert, TSnackbar.LENGTH_SHORT);
        View snackbarView = tSnackbar.getView();
        if (colorCode == null)
            colorCode = "#E5EC2926";
        else
            colorCode = "#00AAFF";
        snackbarView.setBackgroundColor(Color.parseColor(colorCode));
        TextView textView = snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        tSnackbar.show();
    }

    public static void setDate(MaterialButton materialButton) {
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        materialButton.setText(sdf.format(date));
    }

    public static void viewData(FirebaseRecyclerOptions firebaseRecyclerOptions, RecyclerView recyclerView) {
        FirebaseRecyclerAdapter<ModelInput, ViewHolderData> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ModelInput, ViewHolderData>(firebaseRecyclerOptions) {
                    @Override
                    protected void onBindViewHolder(@NonNull final ViewHolderData holder, final int position, @NonNull final ModelInput model) {
                        final String type = ((Activity) context).getIntent().getStringExtra("type");
                        if (type.equals("murid")) {
                            holder.roll.setText(model.getClassrollnumber());
                            holder.name.setText(model.getFname() + " " + model.getLname());
                            holder.cardView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    context.startActivity(new Intent(context, ActivityInput.class)
                                            .putExtra("roll", model.getClassrollnumber()).putExtra("type", type));
                                    ((Activity) context).finish();
                                }
                            });
                        } else if (type.equals("karyawan")) {
                            holder.typex.setText("Employee ID:");
                            holder.roll.setText(model.getEmployeeid());
                            holder.name.setText(model.getFname() + " " + model.getLname());
                            holder.cardView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    context.startActivity(new Intent(context, ActivityInput.class)
                                            .putExtra("roll", model.getEmployeeid()).putExtra("type", type));
                                    ((Activity) context).finish();
                                }
                            });
                        } else {
                            holder.typex.setText("Teacher ID:");
                            holder.roll.setText(model.getTeacherid());
                            holder.name.setText(model.getFname() + " " + model.getLname());
                            holder.cardView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    context.startActivity(new Intent(context, ActivityInput.class)
                                            .putExtra("roll", model.getTeacherid()).putExtra("type", type));
                                    ((Activity) context).finish();
                                }
                            });
                        }
                    }

                    @NonNull
                    @Override
                    public ViewHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_view, parent, false);
                        return new ViewHolderData(view);
                    }
                };
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(layoutManager);
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static void searchIndex(String s, DatabaseReference databaseReference, RecyclerView recyclerView) {
        if (!TextUtils.isEmpty(s))
            s = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
        FirebaseRecyclerOptions recyclerOptions = new FirebaseRecyclerOptions.Builder<ModelInput>().setQuery(databaseReference.
                orderByChild("fname").startAt(s).endAt(s + "\uf8ff"), ModelInput.class).build();
        viewData(recyclerOptions, recyclerView);
    }

    public static void updateProfil(DatabaseReference reference, TextInputEditText bday, TextInputEditText classname,
                                    TextInputEditText classrollnumber, TextInputEditText curraddress, TextInputEditText fname,
                                    TextInputEditText lname, TextInputEditText permaddress, TextInputEditText phone,
                                    TextInputEditText year, TextInputEditText position, Spinner sex, String type) {
        Map<String, Object> profilUpdate = new HashMap<>();
        profilUpdate.put("bday", bday.getText().toString());
        if (type.equals("classrollnumber")) {
            profilUpdate.put("classname", classname.getText().toString());
            profilUpdate.put("year", year.getText().toString());
        } else {
            profilUpdate.put("position", position.getText().toString());
        }
        profilUpdate.put(type, classrollnumber.getText().toString());
        profilUpdate.put("curraddress", curraddress.getText().toString());
        profilUpdate.put("fname", fname.getText().toString());
        profilUpdate.put("lname", lname.getText().toString());
        profilUpdate.put("permaddress", permaddress.getText().toString());
        profilUpdate.put("phone", phone.getText().toString());
        profilUpdate.put("sex", sex.getSelectedItem().toString());
        reference.updateChildren(profilUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            Toast.makeText(context, "Profile has successfully updated!", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(context, "Profile has not successfully updated!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static void ref(DatabaseReference databaseReference, String path, final TextInputEditText textInputEditText,
                           final Spinner spinner) {
        databaseReference.child(path).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                if (textInputEditText != null)
                    textInputEditText.setText(s);
                if (spinner != null) {
                    if(s==null)
                        spinner.setSelection(0);
                    else if (s.equals("Male"))
                        spinner.setSelection(0);
                    else
                        spinner.setSelection(1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public static void datePicker(final TextInputEditText tanggal) {
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd MMMM yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                tanggal.setText(sdf.format(myCalendar.getTime()));
            }
        };
        new DatePickerDialog(context, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
