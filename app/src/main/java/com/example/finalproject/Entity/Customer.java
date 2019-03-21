package com.example.finalproject.Entity;

import java.io.Serializable;

public class Customer implements Serializable {
    private int customerID;
    private String cusName;
    private String cusImage;
    private String cusAddress;
    private String cusPhone;
    private String userName;
    private String password;
    private boolean cusStatus;

    public Customer(int customerID, String cusName, String cusImage, String cusAddress,
                    String cusPhone, String userName, String password, boolean cusStatus) {
        this.customerID = customerID;
        this.cusName = cusName;
        this.cusImage = cusImage;
        this.cusAddress = cusAddress;
        this.cusPhone = cusPhone;
        this.userName = userName;
        this.password = password;
        this.cusStatus = cusStatus;
    }

    public Customer() {
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusImage() {
        return cusImage;
    }

    public void setCusImage(String cusImage) {
        this.cusImage = cusImage;
    }

    public String getCusAddress() {
        return cusAddress;
    }

    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
    }

    public String getCusPhone() {
        return cusPhone;
    }

    public void setCusPhone(String cusPhone) {
        this.cusPhone = cusPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isCusStatus() {
        return cusStatus;
    }

    public void setCusStatus(boolean cusStatus) {
        this.cusStatus = cusStatus;
    }
}
