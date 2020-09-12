package com.example.invoicerecorder;

import com.google.firebase.database.DatabaseReference;

public class record_item {
    private String firstName,lastName,iD;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getiD() {
        return iD;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }

    public record_item(String firstName, String lastName){

        this.firstName=firstName;
        this.lastName=lastName;
    }
    public record_item(){

    }

    public String toString(){
        return "firstname "+firstName+"\n"+"lastname "+lastName+"\n";
    }
}
