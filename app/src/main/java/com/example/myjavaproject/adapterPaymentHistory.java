package com.example.myjavaproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapterPaymentHistory extends RecyclerView.Adapter<adapterPaymentHistory.myviewholder>{
    ArrayList<PaymentHistoryModel> dataholder;
    private Context context;
    Database database;

    public adapterPaymentHistory( ArrayList<PaymentHistoryModel> dataholder,Database database,Context context) {
        this.dataholder = dataholder;
        this.database=database;
        this.context=context;

    }



    @NonNull
    @Override
    public adapterPaymentHistory.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_history_row,parent,false);
        return new adapterPaymentHistory.myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterPaymentHistory.myviewholder holder, int position) {
    holder.date.setText(String.valueOf(dataholder.get(position).getPaidDate()));
    holder.month.setText(String.valueOf(dataholder.get(position).getMonth()));
    //holder.year.setText(String.valueOf(dataholder.get(position).getYear()));
    holder.status.setText(dataholder.get(position).getStatus());
    holder.salary.setText(String.valueOf(dataholder.get(position).getSalary()));

    }

    @Override
    public int getItemCount() {
        return  dataholder.size();
    }
    class myviewholder extends RecyclerView.ViewHolder {
        TextView year,month,status,date,salary;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            salary = itemView.findViewById(R.id.salaryph);
            date=itemView.findViewById(R.id.dateph);
            month =itemView.findViewById(R.id.monthph);
            status=itemView.findViewById(R.id.statusph);
           // year=itemView.findViewById(R.id.yearph);

        }
    }
}
