package com.example.finalproject.Entity;

import java.io.Serializable;

public class Category implements Serializable {
    private int categoryID;
    private String cName;
    private boolean cStatus;

    public Category() {
    }

    public Category(int categoryID, String cName, boolean cStatus) {
        this.categoryID = categoryID;
        this.cName = cName;
        this.cStatus = cStatus;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public boolean iscStatus() {
        return cStatus;
    }

    public void setcStatus(boolean cStatus) {
        this.cStatus = cStatus;
    }
}
