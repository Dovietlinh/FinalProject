package com.example.finalproject.Entity;

import java.io.Serializable;

public class Menu implements Serializable {
    private int menuID;
    private int customerID;
    private String mName;

    public Menu() {
    }

    public Menu(int menuID, int customerID, String mName) {
        this.menuID = menuID;
        this.customerID = customerID;
        this.mName = mName;
    }

    public int getMenuID() {
        return menuID;
    }

    public void setMenuID(int menuID) {
        this.menuID = menuID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}
