package com.example.myjavaproject;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentHistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PaymentHistoryFragment() {
        // Required empty public constructor
    }
    public static PaymentHistoryFragment newInstance(int employeeId) {
        PaymentHistoryFragment fragment = new PaymentHistoryFragment();
        Bundle args = new Bundle();
        args.putInt("employeeId", employeeId);
        fragment.setArguments(args);
        return fragment;
    }
    int employeeId;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PaymentHistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PaymentHistoryFragment newInstance(String param1, String param2) {
        PaymentHistoryFragment fragment = new PaymentHistoryFragment();
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
        if (getArguments() != null) {
            employeeId = getArguments().getInt("employeeId");
            // You can use the employeeId here to fetch the attendance history data for that specific employee
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_history, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.recyclerph);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Database db=new Database(getActivity());
        Cursor cursor=db.paymenthistorydata(employeeId);
        ArrayList<PaymentHistoryModel>dataholder=new ArrayList<>();

        try {
            while (cursor.moveToNext()) {
                int Emp_id = cursor.getInt(0);
                String month = cursor.getString(1);
                int year = cursor.getInt(2);
                int Salary = cursor.getInt(3);
                String status=cursor.getString(4);
                String Date =cursor.getString(5);
                PaymentHistoryModel obj = new  PaymentHistoryModel(Emp_id, month,year, Salary,status,Date);
                dataholder.add(obj);
            }
        }
        catch(Exception e) {
            // Handle the exception here
            Log.e("PaymentFragment", "Error occurred while retrieving data from database", e);
            e.printStackTrace();
        }
        if (!dataholder.isEmpty()) {
            adapterPaymentHistory adapter = new adapterPaymentHistory(dataholder,db,getContext());
            recyclerView.setAdapter(adapter);
        }
        // Inflate the layout for this fragment

        return view;
    }
}