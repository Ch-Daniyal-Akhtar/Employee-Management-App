package com.example.myjavaproject;

public class attendancehistorymodel {
    String date;
    String salary;
    String status;
    int Emp_id;

    public attendancehistorymodel(int Emp_id,String date, String salary, String status) {
        this.date = date;
        this.salary = salary;
        this.status = status;
        this.Emp_id=Emp_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getEmp_id() {
        return Emp_id;
    }

    public void setEmp_id(int emp_id) {
        Emp_id = emp_id;
    }
}
