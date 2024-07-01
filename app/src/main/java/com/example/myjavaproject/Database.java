package com.example.myjavaproject;


import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.util.Log;
import android.view.Display;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

public class Database  extends SQLiteOpenHelper {
private static final String DATABASE_NAME="Payroll";
private static final int DATABASE_VERSION=8;
private static final String TABLE_NAME="Employee";
private static final String SR_NO="Sr_no";
private static final String E_NAME="Name";
private static final String E_JOB="Job";
private static final String E_CONTACT="Contact";
private static final String E_SALARY="Salary";
private static final String E_DOJ="Date";
private static final String MONDAY="Monday";
private static final String SATURDAY="Saturday";
private static final String TUESDAY="Tuesday";
private static final String WEDNESDAY="Wednesday";
private static final String THURSDAY="Thursday";
private static final String FRIDAY="Friday";
private static final String SUNDAY="Sunday";
private static final String ProfileImage="Image";
private static final String GENDER="Gender";
private static final String ATTENDANCE_ID = "Attendance_id";
private static final String ATTENDANCE_DATE = "Attendance_date";
private static final String EMPLOYEE_ID = "Employee_id";
private static final String ATTENDANCE_STATUS = "Attendance_status";

private static final String ATTENDANCE_TABLE_NAME="Attendance";
private static final String Salary_Per_Day="Salary";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                    SR_NO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    E_NAME + " VARCHAR(30)," +
                    E_JOB + " VARCHAR(30)," +
                    E_CONTACT + " VARCHAR(16)," +
                    E_SALARY + " INTEGER," +
                    E_DOJ + " VARCHAR(14)," +
                    ProfileImage + " Blob," +
                    MONDAY + " BOOLEAN," +
                    TUESDAY + " BOOLEAN," +
                    WEDNESDAY + " BOOLEAN," +
                    THURSDAY + " BOOLEAN," +
                    FRIDAY + " BOOLEAN," +
                    SATURDAY + " BOOLEAN," +
                    SUNDAY + " BOOLEAN," +
                    GENDER+" VARCHAR(2),"+

                    "CONSTRAINT UniqueCombination UNIQUE (" + E_NAME + ", " + E_JOB + ", " + E_CONTACT + ")" +
                    ")");

            db.execSQL("CREATE TABLE " + ATTENDANCE_TABLE_NAME + "(" +
                    ATTENDANCE_DATE + " DATE  , " +
                    EMPLOYEE_ID + " INTEGER," +
                    ATTENDANCE_STATUS + " char(1)," +
                    Salary_Per_Day+" INTEGER, "+
                    "PRIMARY KEY (" + ATTENDANCE_DATE + ", " + EMPLOYEE_ID + "), " +
                    "FOREIGN KEY (" + EMPLOYEE_ID + ") REFERENCES " + TABLE_NAME + "(" + SR_NO + ")" +
                    ")");


            db.execSQL("CREATE TABLE Payment (" +

                    "EmpId INTEGER, " +
                    "Month VARCHAR(10), " +
                    "Year INTEGER, " +
                    "Salary INTEGER, " +
                    "Status VARCHAR(10), " +
                    "PaidDate TEXT, " +
                    "PRIMARY KEY (Month, Year, EmpId), " +
                    "FOREIGN KEY (EmpId) REFERENCES Employee(Sr_no)" +
                    ")");
        } catch (SQLException e) {
            Log.e("Database", "Error creating table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + ATTENDANCE_TABLE_NAME);
            onCreate(db);
        } catch (SQLException e) {
            Log.e("Database", "Error upgrading database: " + e.getMessage());
            // Handle the exception here, e.g., log it or show a message to the user
            e.printStackTrace();
        }
    }

    public void addEmployee(String name, String job, String contact, int salary, String date, byte[] image, boolean monday, boolean tuesday,
                            boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday,String gender){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(E_NAME, name);
        values.put(E_JOB, job);
        values.put(E_CONTACT, contact);
        values.put(E_SALARY, salary);
        values.put(ProfileImage, image);
        values.put(E_DOJ, date);
        values.put(MONDAY, monday);
        values.put(TUESDAY, tuesday);
        values.put(WEDNESDAY, wednesday);
        values.put(THURSDAY, thursday);
        values.put(FRIDAY, friday);
        values.put(SATURDAY, saturday);
        values.put(SUNDAY, sunday);
        values.put(GENDER,gender);
        try {
            db.insert(TABLE_NAME, null, values);

        }

        catch (SQLException e) {
            // Handle the exception here, e.g., log it or show a message to the user
            e.printStackTrace();
        }
    }
    public void addattendance(LocalDate localDate,int employee_id,String attendance,int salaryperday){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ATTENDANCE_DATE, String.valueOf(localDate));
        values.put(EMPLOYEE_ID,employee_id);
        values.put(ATTENDANCE_STATUS, attendance);
        values.put(Salary_Per_Day,salaryperday);

        try{
            db.insert(ATTENDANCE_TABLE_NAME,null,values);
        }
        catch (Exception e){
            Log.d("Database","Error inserting");
            e.printStackTrace();
        }
    }
    public int getSalary(LocalDate start, LocalDate end, int srNo) {
        SQLiteDatabase db = this.getReadableDatabase();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDate = start.format(formatter);
        String endDate = end.format(formatter);

        // SQL query to calculate the sum of Salary
        String query = "SELECT SUM(Salary) AS totalSalary FROM Attendance WHERE Employee_id = ? AND Attendance_date BETWEEN ? AND ?";

        // Execute the query and get the result in a Cursor
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(srNo), startDate, endDate});

        int totalSalary = 0;

        if (cursor.moveToFirst()) {
            try {
                // Use getColumnIndexOrThrow to ensure the column exists
                int columnIndex = cursor.getColumnIndexOrThrow("totalSalary");
                totalSalary = cursor.getInt(columnIndex);
            } catch (IllegalArgumentException e) {
                // Handle the case where the column does not exist
                e.printStackTrace();
            }
        }

        cursor.close();
        return totalSalary;
    }

    public void addPayment(int Srno,String month ,int year,int salary,String Status,String paidDate){
        SQLiteDatabase db = this.getWritableDatabase();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //.String paymentDate = paidDate.format(formatter);
        db.execSQL("Insert into Payment values ("+Srno+" , "+month+" , "+year+" , "+salary+" , "+Status+" , "+paidDate+")");

    }
        public Cursor readattendancedata() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
          try {
            String qry = "SELECT Sr_no,Image, Name, Salary, Job,Date, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday,Gender FROM Employee";
            cursor = db.rawQuery(qry, null);
          }
          catch (SQLException e) {
            // Handle the exception here, e.g., log it or show a message to the user
            e.printStackTrace();
          }
           return cursor;
        }
        public Cursor homepagedata() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
          try {
            String qry = "SELECT Image,Date,Name,Sr_no,Job,Salary,Contact,Gender FROM Employee";
            cursor = db.rawQuery(qry, null);
          }
          catch (SQLException e) {
            // Handle the exception here, e.g., log it or show a message to the user
            e.printStackTrace();
          }
           return cursor;
        }
        public Cursor attendancehistorydata(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
          try {
            String qry = "SELECT Employee_id,Attendance_date,Attendance_status,Salary FROM Attendance Where Employee_id="+id;
            cursor = db.rawQuery(qry, null);
          }
          catch (SQLException e) {
            // Handle the exception here, e.g., log it or show a message to the user
            e.printStackTrace();
          }
           return cursor;
        }

    public boolean checkAttendanceForToday(int employeeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean isAttendanceMarked = false;
        try {
            // Get the current date
            String currentDate = String.valueOf(LocalDate.now());

            // Query to check if attendance is marked for today for the given employee
            String query = "SELECT * FROM " + ATTENDANCE_TABLE_NAME + " WHERE " + ATTENDANCE_DATE + " = ? AND " +
                    EMPLOYEE_ID + " = ?";

            // Arguments for the query
            String[] args = {currentDate, String.valueOf(employeeId)};

            // Execute the query
            Cursor cursor = db.rawQuery(query, args);

            // If the cursor has any rows, it means attendance is already marked
            if (cursor != null && cursor.getCount() > 0) {
                isAttendanceMarked = true;
            }

            // Close the cursor
            if (cursor != null) {
                cursor.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAttendanceMarked;
    }

    public void removeEmployee(int employeeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // Delete the employee with the given ID from the Employee table
            db.delete(TABLE_NAME, SR_NO + " = ?", new String[]{String.valueOf(employeeId)});
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database connection
            db.close();
        }
    }

    // Method to remove attendance entry based on its ID
    public void removeAttendance(int attendanceId) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // Delete the attendance entry with the given ID from the Attendance table
            db.delete(ATTENDANCE_TABLE_NAME, ATTENDANCE_ID + " = ?", new String[]{String.valueOf(attendanceId)});
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database connection
            db.close();
        }
    }
    public void updatestatus(int Id,String date){
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println(date);

            // Properly quote the date string within the SQL query
            String sql = "UPDATE Payment SET status = 'Paid'  WHERE EmpId = " + Id +
                    " AND PaidDate = (SELECT PaidDate FROM Payment WHERE EmpId = " + Id + " ORDER BY PaidDate DESC LIMIT 1)";
        // Log the SQL query for debugging
        Log.d("SQL_DEBUG", sql);
        System.out.println(date);
            db.execSQL(sql);
        }


    public Cursor paymenthistorydata(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String qry = "SELECT * FROM Payment Where EmpId="+id;
            System.out.println(id);
            cursor = db.rawQuery(qry, null);
        }
        catch (SQLException e) {
            // Handle the exception here, e.g., log it or show a message to the user
            e.printStackTrace();
        }
        return cursor;
    }
    public boolean isLastPaymentPaid(int employeeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean isPaid = false;

        try {
            // Query to check if the last payment entry for the given employee is paid
            String query = "SELECT Status FROM Payment WHERE EmpId = ? ORDER BY PaidDate DESC LIMIT 1";

            // Arguments for the query
            String[] args = {String.valueOf(employeeId)};

            // Execute the query
            Cursor cursor = db.rawQuery(query, args);

            // Check if the cursor has any rows
            if (cursor != null && cursor.moveToFirst()) {
                // Get the status of the last payment
                String status = cursor.getString(cursor.getColumnIndexOrThrow("Status"));

                // Check if the status is "Paid"
                isPaid = status.equals("Paid");
            }

            // Close the cursor
            if (cursor != null) {
                cursor.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isPaid;
    }
    public int getLatestSalary(int employeeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        int latestSalary = 0;

        try {
            // Query to retrieve the latest salary for the given employee ID
            String query = "SELECT Salary FROM Payment WHERE EmpId = ? ORDER BY PaidDate DESC LIMIT 1";

            // Arguments for the query
            String[] args = {String.valueOf(employeeId)};

            // Execute the query
            Cursor cursor = db.rawQuery(query, args);

            // Check if the cursor has any rows
            if (cursor != null && cursor.moveToFirst()) {
                // Get the latest salary
                latestSalary = cursor.getInt(cursor.getColumnIndexOrThrow("Salary"));
            }

            // Close the cursor
            if (cursor != null) {
                cursor.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return latestSalary;
    }
    public boolean isEmployeeNotInPayment(int employeeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean isNotPresent = true;

        try {
            // Query to check if the employee is present in the Payment table
            String query = "SELECT COUNT(*) FROM Payment WHERE EmpId = ?";

            // Arguments for the query
            String[] args = {String.valueOf(employeeId)};

            // Execute the query
            Cursor cursor = db.rawQuery(query, args);

            // Check if the cursor has any rows
            if (cursor != null && cursor.moveToFirst()) {
                // Get the count of rows
                int count = cursor.getInt(0);

                // If count is greater than 0, the employee is present
                if (count > 0) {
                    isNotPresent = false;
                }
            }

            // Close the cursor
            if (cursor != null) {
                cursor.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isNotPresent;
    }



}

