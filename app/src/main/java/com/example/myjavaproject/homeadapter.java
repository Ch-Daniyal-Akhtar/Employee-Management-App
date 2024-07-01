package com.example.myjavaproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class homeadapter extends RecyclerView.Adapter<homeadapter.myviewholder> {

    // Declare an interface for click listener
    public interface ButtonClickListener {
        void onAttendanceHistoryButtonClick(int position);
        void onPaymentButtonClick(int position);
        void onPaidButtonClick(int position);
        // Add more methods if needed for other buttons
    }

    ArrayList<homemodel> dataholder;
    private Context context;
    Database database;
    private ButtonClickListener buttonClickListener;

    public homeadapter(ArrayList<homemodel> dataholder, Database database, Context context, ButtonClickListener buttonClickListener) {
        this.dataholder = dataholder;
        this.database = database;
        this.context = context;
        this.buttonClickListener = buttonClickListener;
    }

    @NonNull
    @Override
    public homeadapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_row, parent, false);
        return new homeadapter.myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull homeadapter.myviewholder holder, int position) {

        // You may need to format the date or payment values as per your requirements
        holder.name.setText(dataholder.get(position).getName());
        holder.job.setText(dataholder.get(position).getJob());
        holder.salary.setText(String.valueOf(dataholder.get(position).getSalary()));
        String gender = dataholder.get(position).getGender();
        holder.contact.setText(dataholder.get(position).getContact());
        if(database.isLastPaymentPaid(dataholder.get(position).Sr_no)){
            holder.PaidButton.setVisibility(View.GONE);
            holder.month.setVisibility(View.GONE);
            holder.paymenttext.setVisibility(View.GONE);

        }
        if(database.isEmployeeNotInPayment(dataholder.get(position).Sr_no)){
            holder.PaidButton.setVisibility(View.GONE);
            holder.month.setVisibility(View.GONE);
            holder.paymenttext.setVisibility(View.GONE);
        }
        holder.month.setText(String.valueOf(database.getLatestSalary(dataholder.get(position).Sr_no)));
        LocalDate doj = LocalDate.of(2024, 3, 22);

        try {
            String dateString = dataholder.get(position).getDate(); // Retrieve the date string
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd"); // Specify the correct format
            doj = LocalDate.parse(dateString, formatter); // Parse the date
            //System.out.println("Parsed date: " + doj); // Print or use the parsed date
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            System.out.println("Error parsing date");
        }

        if (doj.getDayOfMonth() == LocalDate.now().getDayOfMonth()) {
            LocalDate presentDate = LocalDate.now();
            LocalDate prevDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue() - 1, LocalDate.now().getDayOfMonth());
            int salary = database.getSalary(presentDate, prevDate, dataholder.get(position).getSr_no());
        }

        holder.AttendanceHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAdapterPosition();
                if (buttonClickListener != null && clickedPosition != RecyclerView.NO_POSITION) {
                    buttonClickListener.onAttendanceHistoryButtonClick(clickedPosition);
                }
            }
        });

        holder.PaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAdapterPosition();
                if (buttonClickListener != null && clickedPosition != RecyclerView.NO_POSITION) {
                    buttonClickListener.onPaymentButtonClick(clickedPosition);
                }
            }
        });

        holder.PaidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAdapterPosition();
                if (buttonClickListener != null && clickedPosition != RecyclerView.NO_POSITION) {
                    buttonClickListener.onPaidButtonClick(clickedPosition);
                }
            }
        });

        byte[] imageBytes = dataholder.get(position).getImage();
        if (imageBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.profilepicture.setImageBitmap(bitmap);
        } else if (gender.equals("F")) {
            holder.profilepicture.setImageResource(R.drawable.woman_pic);
        }
    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }


    class myviewholder extends RecyclerView.ViewHolder {
        ShapeableImageView profilepicture;
        TextView name, job, salary, contact, month,paymenttext, doj,payment;
        Button AttendanceHistoryButton, PaymentButton, PaidButton, DeleteButton;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            // Find the CardView
            AttendanceHistoryButton = itemView.findViewById(R.id.attendancehistoryBtn);
            PaidButton = itemView.findViewById(R.id.paidbtn);
            PaymentButton = itemView.findViewById(R.id.PaymentBtn);
            //DeleteButton = itemView.findViewById(R.id.DeleteBtn);
            profilepicture = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.Nameh);
            job = itemView.findViewById(R.id.Jobh);
            salary = itemView.findViewById(R.id.MonthlyPay);
            contact = itemView.findViewById(R.id.Contact);
            month = itemView.findViewById(R.id.salaryhome);
            paymenttext=itemView.findViewById(R.id.Paymenttxt);

        }
    }
}
