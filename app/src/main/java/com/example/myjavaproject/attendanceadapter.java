package com.example.myjavaproject;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

public class attendanceadapter extends RecyclerView.Adapter<attendanceadapter.myviewholder> {
ArrayList<attendancemodel> dataholder;
    private Context context;
    Database database;
    public attendanceadapter( ArrayList<attendancemodel> dataholder,Database database,Context context) {
        this.dataholder = dataholder;
        this.database=database;
        this.context=context;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_row,parent,false);
         return new myviewholder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
      holder.ename.setText(dataholder.get(position).getName());
      holder.ejob.setText(dataholder.get(position).getJob());
      String presentdate=String.valueOf(LocalDate.now());
      LocalDate dbdate=dataholder.get(position).getDate();
      String presentday=String.valueOf(LocalDate.now().getDayOfWeek()).toLowerCase();
      String capitalizedDay = presentday.substring(0, 1).toUpperCase() + presentday.substring(1);
      holder.date.setText(presentdate);

      holder.day.setText(capitalizedDay+":");
      int number_of_days=YearMonth.now().lengthOfMonth();
      int salaryPerDay =dataholder.get(position).getSalary()/number_of_days;
        boolean tuesday=dataholder.get(position).getTuesday();
        boolean monday=dataholder.get(position).getMonday();
        boolean wednesday=dataholder.get(position).getWednesday();
        boolean thursday=dataholder.get(position).getThursday();
        boolean friday=dataholder.get(position).getFriday();
        boolean saturday=dataholder.get(position).getSaturday();
        boolean sunday=dataholder.get(position).getSunday();
        String gender=dataholder.get(position).getGender();
        holder.eattendance.setText("Unmarked");
//        if((LocalDate.now().getMonthValue())%2!=0){
//        salaryPerDay = dataholder.get(position).getSalary()/31;}
//        else if ((LocalDate.now().getMonthValue())%2==0) {
//            if((LocalDate.now().getMonthValue())/2==1){
//                if(LocalDate.now().isLeapYear()){
//                     salaryPerDay =dataholder.get(position).getSalary()/ 29;
//                }
//                else {
//                     salaryPerDay =dataholder.get(position).getSalary()/ 28;
//                }
//            }
//            else{
//             salaryPerDay = dataholder.get(position).getSalary()/30;}
//        }
        int employeeId=dataholder.get(position).getSr_no();
        LocalDate setdate=dataholder.get(position).getDate();

        boolean isWorkday = false;
        switch (LocalDate.now().getDayOfWeek()) {
            case MONDAY:
                isWorkday = !dataholder.get(position).getMonday();
                break;
            case TUESDAY:
                isWorkday = !dataholder.get(position).getTuesday();
                break;
            case WEDNESDAY:
                isWorkday = !dataholder.get(position).getWednesday();
                break;
            case THURSDAY:
                isWorkday = !dataholder.get(position).getThursday();
                break;
            case FRIDAY:
                isWorkday = !dataholder.get(position).getFriday();
                break;
            case SATURDAY:
                isWorkday = !dataholder.get(position).getSaturday();
                break;
            case SUNDAY:
                isWorkday = !dataholder.get(position).getSunday();
                break;
        }

        if (isWorkday) {
            holder.daystatus.setText(" Working Day");

        } else {
            holder.daystatus.setText(" Off Day");

        }
        byte[] imageBytes = dataholder.get(position).getImage();
        if (imageBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.profilepic.setImageBitmap(bitmap);
        } else if(gender.equals("F")){
            holder.profilepic.setImageResource(R.drawable.woman_pic);
        }
        if (setdate.isBefore(LocalDate.now()) || setdate.isEqual(LocalDate.now())){

            holder.AbsentButton.setVisibility(View.VISIBLE);
            holder.PresentButton.setVisibility(View.VISIBLE);
            if(!isWorkday){
                database.addattendance(LocalDate.now(), employeeId, "O", salaryPerDay);
            }
            else if(isWorkday){
            holder.AbsentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    database.addattendance(LocalDate.now(), employeeId, "A", 0);
                    // Notify user that attendance has been marked as absent
                    Toast.makeText(context, "Attendance marked as absent", Toast.LENGTH_SHORT).show();
                holder.AbsentButton.setVisibility(View.GONE);
                holder.PresentButton.setVisibility(View.GONE);
                holder.eattendance.setText("Marked");

            }
          });
            holder.PresentButton.setOnClickListener(new View.OnClickListener() {
            @Override
             public void onClick(View v) {

                    database.addattendance(LocalDate.now(), employeeId, "P", salaryPerDay);
                    // Notify user that attendance has been marked as absent
                    Toast.makeText(context, "Attendance marked as Present", Toast.LENGTH_SHORT).show();
                    holder.AbsentButton.setVisibility(View.GONE);
                holder.PresentButton.setVisibility(View.GONE);
                holder.eattendance.setText("Marked");

            }
          });}
            boolean isAttendanceMarked = database.checkAttendanceForToday(dataholder.get(position).getSr_no());

            // Show/hide buttons based on whether attendance is marked or not
            if (!isAttendanceMarked) {
                holder.AbsentButton.setVisibility(View.VISIBLE);
                holder.PresentButton.setVisibility(View.VISIBLE);
                holder.eattendance.setText("Unmarked");
                // Set onClickListeners for buttons
                //...
            } else {
                holder.AbsentButton.setVisibility(View.GONE);
                holder.PresentButton.setVisibility(View.GONE);
                holder.eattendance.setText("Marked");
                // Optionally, set a message or indicator that attendance is already marked
                //...
            }
        }

        else {
            // Hide the buttons if the condition is not fulfilled
            holder.AbsentButton.setVisibility(View.GONE);
            holder.PresentButton.setVisibility(View.GONE);
            holder.eattendance.setText("Starts from "+dbdate);
        }
        if (setdate.isEqual(LocalDate.now()) &&
                ((monday && LocalDate.now().getDayOfWeek() == DayOfWeek.MONDAY) ||
                        (tuesday && LocalDate.now().getDayOfWeek() == DayOfWeek.TUESDAY) ||
                        (wednesday && LocalDate.now().getDayOfWeek() == DayOfWeek.WEDNESDAY) ||
                        (thursday && LocalDate.now().getDayOfWeek() == DayOfWeek.THURSDAY) ||
                        (friday && LocalDate.now().getDayOfWeek() == DayOfWeek.FRIDAY) ||
                        (saturday && LocalDate.now().getDayOfWeek() == DayOfWeek.SATURDAY) ||
                        (sunday && LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY))) {
            holder.AbsentButton.setVisibility(View.GONE);
            holder.PresentButton.setVisibility(View.GONE);
            holder.eattendance.setText("Rest and Relaxation Day");
            database.addattendance(LocalDate.now(), employeeId, "O", salaryPerDay);
        }

    }
    @Override
    public int getItemCount() {
        return dataholder.size();
    }
    class myviewholder extends RecyclerView.ViewHolder {
        ShapeableImageView profilepic;
        TextView ename, ejob, date, daystatus, eattendance,day;
        Button AbsentButton,PresentButton;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            // Find the CardView
             AbsentButton = (Button) itemView.findViewById(R.id.AbsentBtn);
             PresentButton = (Button) itemView.findViewById(R.id.PresentBtn);
            profilepic = itemView.findViewById(R.id.profileimage);
            ename = itemView.findViewById(R.id.Name);
            ejob = itemView.findViewById(R.id.Job);
            date = itemView.findViewById(R.id.Date);
            daystatus = itemView.findViewById(R.id.DayStatus);
            eattendance = itemView.findViewById(R.id.Attendance);
             day= itemView.findViewById(R.id.Day);

        }

    }

}
