package com.example.myjavaproject;

import java.time.LocalDate;

public class PaymentHistoryModel {
    int EmpId;
    String month;
    int year;
    int salary;
    String Status;
     String paidDate;

    public PaymentHistoryModel(int empId, String month, int year, int salary, String status, String paidDate) {
        EmpId = empId;
        this.month = month;
        this.year = year;
        this.salary = salary;
        Status = status;
        this.paidDate = paidDate;
    }

    public int getEmpId() {
        return EmpId;
    }

    public void setEmpId(int empId) {
        EmpId = empId;
    }

    public String getMonth() {
        return month.substring(0,3);
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(String paidDate) {
        this.paidDate = paidDate;
    }
}
