package com.example.myjavaproject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Instrumentation;
import android.content.Intent;
import java.io.ByteArrayOutputStream;


import java.io.ByteArrayOutputStream;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

public class NewEmployee extends Fragment {
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Calendar calendar;




    public NewEmployee() {
        // Required empty public constructor
    }
    ImageView profileImageView;
    FloatingActionButton camerabtn;
    ActivityResultLauncher<Intent> imagePickerLauncher;


    public static NewEmployee newInstance(String param1, String param2) {
        NewEmployee fragment = new NewEmployee();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        // Initialize calendar to the current date
        calendar = Calendar.getInstance();



    }
    byte[] imageBytes;
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri=data.getData();
        profileImageView.setImageURI(uri);

        try {
            // Convert the selected image URI to a byte array
            InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
             imageBytes = getBytes(inputStream);
            // Insert the byte array into the database

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_employee, container, false);
        // Find the date EditText in the fragment layout
        // Finding Id of all the atttributes
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setTitle("Create New Employee");
            }
        }
        Database database=new Database(getActivity());
        EditText username=view.findViewById(R.id.username);
        EditText job=view.findViewById(R.id.job);
        EditText  salary=view.findViewById(R.id.salary);
        EditText  contact=view.findViewById(R.id.contact);
        EditText date = view.findViewById(R.id.date);
        profileImageView= view.findViewById(R.id.profileImageView);
        CheckBox monday=view.findViewById(R.id.monday);
        CheckBox tuesday=view.findViewById(R.id.tuesday);
        CheckBox wednesday=view.findViewById(R.id.wednesday);
        CheckBox thursday=view.findViewById(R.id.thursday);
        CheckBox friday=view.findViewById(R.id.friday);
        CheckBox saturday=view.findViewById(R.id.saturday);
        CheckBox sunday=view.findViewById(R.id.sunday);
        Button register=view.findViewById(R.id.register);
        CheckBox male=view.findViewById(R.id.male);
        CheckBox female=view.findViewById(R.id.female);
        // Getting Values for each


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String nametxt = username.getText().toString();
                    String jobtxt = job.getText().toString();
                    String salaryString = salary.getText().toString();
                    int salaryInt = 0; // Default value in case parsing fails or EditText is empty
                    if (!TextUtils.isEmpty(salaryString)) {
                        salaryInt = Integer.parseInt(salaryString);
                    }
                    String contacttxt = contact.getText().toString();
                    String datetxt = date.getText().toString();
                    boolean Monday = monday.isChecked();
                    boolean Tuesday = tuesday.isChecked();
                    boolean Wednesday = wednesday.isChecked();
                    boolean Thursday = thursday.isChecked();
                    boolean Friday = friday.isChecked();
                    boolean Saturday = saturday.isChecked();
                    boolean Sunday = sunday.isChecked();
                    String gender="M";
                    if(male.isChecked()){
                        gender="M";
                    }else if(female.isChecked()){
                        gender="F";
                    }

                    if (TextUtils.isEmpty(nametxt) || TextUtils.isEmpty(contacttxt) || TextUtils.isEmpty(datetxt)) {
                        Toast.makeText(getActivity(), "Complete the Fields Above", Toast.LENGTH_SHORT).show();
                    } else {
                        database.addEmployee(nametxt,jobtxt, contacttxt, salaryInt, datetxt, imageBytes, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday,gender);
                        Toast.makeText(getActivity(), "Employee Registered Successfully", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "An error occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });

     


        camerabtn=view.findViewById(R.id.camerabtn);
        camerabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(NewEmployee.this)
                    .crop()
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .start();

            }
        });
        // Set OnClickListener to show DatePickerDialog
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get current date
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Create DatePickerDialog with minimum date set to today's date
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                                // Increment selectedMonth by 1 because month in DatePicker starts from 0
                                selectedMonth += 1;

                                // Format the date string as YYYY/MM/DD
                                String selectedDate = String.format("%04d/%02d/%02d", selectedYear, selectedMonth, selectedDayOfMonth);
                                date.setText(selectedDate);
                            }
                        }, year, month, day);

                // Set minimum date to today's date
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

                datePickerDialog.show();
            }
        });
        return view;
    }


    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}
