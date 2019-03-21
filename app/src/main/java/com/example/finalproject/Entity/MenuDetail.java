package com.example.finalproject.Entity;

import java.io.Serializable;

public class MenuDetail implements Serializable {
    private int productID;
    private int menuID;

    public MenuDetail() {
    }

    public MenuDetail(int productID, int menuID) {
        this.productID = productID;
        this.menuID = menuID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getMenuID() {
        return menuID;
    }

    public void setMenuID(int menuID) {
        this.menuID = menuID;
    }
}
