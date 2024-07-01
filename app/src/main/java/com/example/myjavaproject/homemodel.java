package com.example.myjavaproject;

import java.time.LocalDate;

public class homemodel {
    byte[]image;
    String date;
    int salary;
    int Sr_no;
    String name;
    String job;
    String Gender;
    String contact;

    public homemodel(byte[] image, String date, int salary, int sr_no,
                     String name, String job, String gender, String contact) {
        this.image = image;
        this.date = date;
        this.salary = salary;
        Sr_no = sr_no;
        this.name = name;
        this.job = job;
        Gender = gender;
        this.contact = contact;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getSr_no() {
        return Sr_no;
    }

    public void setSr_no(int sr_no) {
        Sr_no = sr_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
