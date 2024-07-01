package com.example.myjavaproject;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Attendance#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Attendance extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Attendance() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Attendance.
     */
    // TODO: Rename and change types and number of parameters


    public static Attendance newInstance(String param1, String param2) {
        Attendance fragment = new Attendance();
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

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance, container, false);

        RecyclerView recyclerView=view.findViewById(R.id.recyclerattendance);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setTitle("Attendance");
            }
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Database db=new Database(getActivity());
        Cursor cursor=db.readattendancedata();
        ArrayList<attendancemodel> dataholder =new ArrayList<>();

        try{
        while (cursor.moveToNext()){
            int Sr_no=cursor.getInt(0);
            byte[] image = cursor.getBlob(1); // Assuming image is stored as blob in column 0
            String name = cursor.getString(2); // Assuming name is stored as string in column 1
            int salary = cursor.getInt(3); // Assuming salary is stored as integer in column 2
            String job = cursor.getString(4); // Assuming job is stored as string in column 3
            String date = cursor.getString(5); // Assuming date is stored as string in column 4
            // Retrieve boolean values from the cursor
            boolean monday = cursor.getInt(6) != 0; // Assuming monday is stored as boolean in column 5
            boolean tuesday = cursor.getInt(7) != 0; // Assuming tuesday is stored as boolean in column 6
            boolean wednesday = cursor.getInt(8) != 0; // Assuming wednesday is stored as boolean in column 7
            boolean thursday = cursor.getInt(9) != 0; // Assuming thursday is stored as boolean in column 8
            boolean friday = cursor.getInt(10) != 0; // Assuming friday is stored as boolean in column 9
            boolean saturday = cursor.getInt(11) != 0; // Assuming saturday is stored as boolean in column 10
            boolean sunday = cursor.getInt(12) != 0; // Assuming sunday is stored as boolean in column 11
            String gender=cursor.getString(13);
            // Create an instance of your attendancemodel object
            attendancemodel obj = new attendancemodel(image, name, salary, job, date, monday, tuesday, wednesday, thursday, friday, saturday, sunday,Sr_no,gender);
            dataholder.add(obj);
        }
            cursor.close(); // Close the cursor after use
        } catch (Exception e) {
            // Handle the exception here
            Log.e("AttendanceFragment", "Error occurred while retrieving data from database", e);
            e.printStackTrace();
        }

        if (!dataholder.isEmpty()) {

            attendanceadapter adapter = new attendanceadapter(dataholder,db,getContext());
            recyclerView.setAdapter(adapter);
        }
        // Inflate the layout for this fragment
        return view;

    }
}