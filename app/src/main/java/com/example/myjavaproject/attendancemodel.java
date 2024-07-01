package com.example.myjavaproject;

import java.time.LocalDate;

public class attendancemodel {
    String name;
    String job;
    String Gender;

    LocalDate presentdate=LocalDate.now();
    int salary;
int Sr_no;
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

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public attendancemodel(byte[] image, String name, int salary, String job, String date, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday, Boolean sunday, int Sr_no, String Gender) {
        this.name = name;
        this.salary=salary;
        this.job = job;
        this.date = dbdate(date);
        this.image = image;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.Sr_no=Sr_no;
        this.Gender=Gender;
    }

    public LocalDate dbdate(String date){
     int findex=date.indexOf("/");
     int lindex=date.lastIndexOf("/");
     int year=Integer.parseInt(date.substring(0,findex));
     int month=Integer.parseInt(date.substring(findex+1,lindex));
     int day=Integer.parseInt(date.substring(lindex+1,date.length()));
     return LocalDate.of(year, month, day);
   }

   LocalDate date;
   byte[]image;
   Boolean monday;
   Boolean tuesday;
   Boolean wednesday;
   Boolean thursday;
   Boolean friday;
   Boolean saturday;
   Boolean sunday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }





    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = LocalDate.parse(date);
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Boolean getMonday() {
        return monday;
    }

    public void setMonday(Boolean monday) {
        this.monday = monday;
    }

    public Boolean getTuesday() {
        return tuesday;
    }

    public void setTuesday(Boolean tuesday) {
        this.tuesday = tuesday;
    }

    public Boolean getWednesday() {
        return wednesday;
    }

    public void setWednesday(Boolean wednesday) {
        this.wednesday = wednesday;
    }

    public Boolean getThursday() {
        return thursday;
    }

    public void setThursday(Boolean thursday) {
        this.thursday = thursday;
    }

    public Boolean getFriday() {
        return friday;
    }

    public void setFriday(Boolean friday) {
        this.friday = friday;
    }

    public Boolean getSaturday() {
        return saturday;
    }

    public void setSaturday(Boolean saturday) {
        this.saturday = saturday;
    }

    public Boolean getSunday() {
        return sunday;
    }

    public void setSunday(Boolean sunday) {
        this.sunday = sunday;
    }
}
