package com.example.finalproject_invoicerecorder;

public class record_item {
    private String item;
    private String price;
    private String uID;
    private String Quantity;
    @Override
    public String toString() {
        return "record_item{" +
                "item='" + item + '\'' +
                ", price='" + price + '\'' +
                ", uID='" + uID + '\'' +
                ", Quantity='" + Quantity + '\'' +
                '}';
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getiD() {
        return uID;
    }

    public void setiD(String iD) {
        this.uID = iD;
    }

    public record_item(String uID, String item, String price, String quantity) {
        this.item = item;
        this.price = price;
        this.uID = uID;
        this.Quantity = quantity;
    }

    public record_item(String uID, String item, String price){

        this.uID=uID;
        this.item = item;
        this.price = price;
    }
    public record_item(){

    }

}
