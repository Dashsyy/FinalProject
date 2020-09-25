package com.example.finalproject_invoicerecorder;

public class UserHelperClass {
    String fname,lname,email,phoneNo,password;

    public UserHelperClass() {
    }

    public UserHelperClass(String fname, String lname, String email, String phoneNo, String password) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.phoneNo = phoneNo;
        this.password = password;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
