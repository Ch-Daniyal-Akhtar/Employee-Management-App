package com.example.myjavaproject;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class deleteadapter extends RecyclerView.Adapter<deleteadapter.myviewholder>{
    private Context context;
    Database database;
    ArrayList<deletemodel> dataholder;

    public deleteadapter(ArrayList<deletemodel> dataholder,Database database,Context context) {
        this.dataholder = dataholder;
        this.database=database;
        this.context=context;

    }

    @NonNull
    @Override
    public deleteadapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.delete_row,parent,false);
        return new deleteadapter.myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull deleteadapter.myviewholder holder, int position) {
        holder.name.setText(dataholder.get(position).getName());
        holder.job.setText(dataholder.get(position).getJob());
        holder.salary.setText(String.valueOf(dataholder.get(position).getSalary()));
        int employeeId=dataholder.get(position).getSr_no();
        String gender=dataholder.get(position).getGender();
        holder.contact.setText(dataholder.get(position).getContact());
        holder.date.setText(dataholder.get(position).getDate());

        byte[] imageBytes = dataholder.get(position).getImage();
        if (imageBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.profilepicture.setImageBitmap(bitmap);
        } else if(gender.equals("F")){
            holder.profilepicture.setImageResource(R.drawable.woman_pic);
        }
        holder.DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.removeAttendance(dataholder.get(position).getSr_no());
                database.removeEmployee(dataholder.get(position).getSr_no());
                Toast.makeText(context, "Employee Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
       return dataholder.size();
    }
    class myviewholder extends RecyclerView.ViewHolder {
        ShapeableImageView profilepicture;
        TextView name, job, salary, contact, date;
        Button DeleteButton;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            // Find the CardView

            DeleteButton = (Button) itemView.findViewById(R.id.DeleteBtn);
            //DeleteButton = (Button) itemView.findViewById(R.id.DeleteBtn);
            profilepicture = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.Nameh);
            job = itemView.findViewById(R.id.Jobh);
            salary = itemView.findViewById(R.id.MonthlyPay);
            contact = itemView.findViewById(R.id.Contact);
            date=itemView.findViewById(R.id.doj);


        }
    }
}
