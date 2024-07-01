package com.example.myjavaproject;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment implements homeadapter.ButtonClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home() {
        // Required empty public constructor
    }
    ArrayList<homemodel> dataholder;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home.
     */
    // TODO: Rename and change types and number of parameters


    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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

        Window window = requireActivity().getWindow();


        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.your_primary_dark_color));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setTitle("Home");
            }
        }
        RecyclerView recyclerView=view.findViewById(R.id.recyclerhome);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Database db=new Database(getActivity());
        Cursor cursor=db.homepagedata();

         dataholder=new ArrayList<>();
        try{
            while(cursor.moveToNext()) {
                byte[] Image = cursor.getBlob(0);
               String date= cursor.getString(1);
                int Salary = cursor.getInt(5);
                int Sr_no = cursor.getInt(3);
                String Name = cursor.getString(2);
                String Job = cursor.getString(4);
                String Gender = cursor.getString(7);
                String Contact = cursor.getString(6);
                homemodel obj = new homemodel(Image, date, Salary, Sr_no, Name, Job, Gender, Contact);
                dataholder.add(obj);

            }
            cursor.close();

        }
        catch(Exception e) {
            // Handle the exception here
            Log.e("AttendanceFragment", "Error occurred while retrieving data from database", e);
            e.printStackTrace();

        }

        if (!dataholder.isEmpty()) {

            homeadapter adapter = new homeadapter(dataholder,db,getContext(),this);
            recyclerView.setAdapter(adapter);
        }
        // Inflate the layout for this fragment

        return view;
    }


    public void onAttendanceHistoryButtonClick(int position) {
        // Create a new instance of the attendance_history fragment


        int employeeId = dataholder.get(position).getSr_no();
        // Create a new instance of the AttendanceHistoryFragment with the employee ID
        AttendanceHistoryFragment attendanceHistoryFragment = AttendanceHistoryFragment.newInstance(employeeId);

        // Get the FragmentManager
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        // Start a fragment transaction
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Replace the current fragment with the attendance_history fragment
        transaction.replace(getId(), attendanceHistoryFragment);

        // Add the transaction to the back stack
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }
    @Override
    public void onPaidButtonClick(int position) {
        int employeeId = dataholder.get(position).getSr_no();
        Database db=new Database(getActivity());
        String date= String.valueOf(LocalDate.now());
        db.updatestatus(employeeId,date);

    }


    @Override
    public void onPaymentButtonClick(int position) {
        int employeeId = dataholder.get(position).getSr_no();
        // Create a new instance of the AttendanceHistoryFragment with the employee ID
        PaymentHistoryFragment paymentHistoryFragment = PaymentHistoryFragment.newInstance(employeeId);

        // Get the FragmentManager
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        // Start a fragment transaction
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Replace the current fragment with the attendance_history fragment
        transaction.replace(getId(), paymentHistoryFragment);

        // Add the transaction to the back stack
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();


    }
}