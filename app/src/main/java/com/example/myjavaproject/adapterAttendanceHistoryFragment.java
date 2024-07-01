package com.example.myjavaproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.time.LocalDate;
import java.util.ArrayList;

public class adapterAttendanceHistoryFragment extends RecyclerView.Adapter<adapterAttendanceHistoryFragment.myviewholder>{
    ArrayList<attendancehistorymodel> dataholder;
    private Context context;
    Database database;

    public adapterAttendanceHistoryFragment( ArrayList<attendancehistorymodel> dataholder,Database database,Context context) {
        this.dataholder = dataholder;
        this.database=database;
        this.context=context;

    }


    @NonNull
    @Override
    public adapterAttendanceHistoryFragment.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.attendancehistory_row,parent,false);
        return new adapterAttendanceHistoryFragment.myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterAttendanceHistoryFragment.myviewholder holder, int position) {

        holder.date.setText(dataholder.get(position).getDate());
        LocalDate date= LocalDate.parse(dataholder.get(position).getDate());
        String presentday=String.valueOf(date.getDayOfWeek()).toLowerCase();
        String capitalizedDay = presentday.substring(0, 1).toUpperCase() + presentday.substring(1, 3);
        String paddedDay = String.format("%-4s", capitalizedDay);
        holder.day.setText(paddedDay);
        holder.salary.setText(dataholder.get(position).getSalary());
        String status=dataholder.get(position).getStatus();
        if(status.equals("P")){
            holder.status.setText("Present");
        }
        else if(status.equals("O")){
            holder.status.setText("Off Day");
        }
        else if(status.equals("A")){
            holder.status.setText("Absent ");
        }

    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }
    class myviewholder extends RecyclerView.ViewHolder {
        TextView date, day, salary,status;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            salary = itemView.findViewById(R.id.salaryah);
            date=itemView.findViewById(R.id.dateah);
            day=itemView.findViewById(R.id.dayah);
            status=itemView.findViewById(R.id.statusah);

        }
    }
}
