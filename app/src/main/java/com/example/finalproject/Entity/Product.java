package com.example.finalproject.Entity;

import java.io.Serializable;

public class Product implements Serializable {

    private int productID;
    private int categoryID;
    private String pName;
    private String pImage;
    private String pDescription;
    private int pAssess;
    private int pType;
    private boolean pStatus;

    public Product() {
    }

    public Product(int productID, int categoryID, String pName, String pImage,
                   String pDescription, int pAssess, int pType, boolean pStatus) {
        this.productID = productID;
        this.categoryID = categoryID;
        this.pName = pName;
        this.pImage = pImage;
        this.pDescription = pDescription;
        this.pAssess = pAssess;
        this.pType = pType;
        this.pStatus = pStatus;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpImage() {
        return pImage;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
    }

    public String getpDescription() {
        return pDescription;
    }

    public void setpDescription(String pDescription) {
        this.pDescription = pDescription;
    }

    public int getpAssess() {
        return pAssess;
    }

    public void setpAssess(int pAssess) {
        this.pAssess = pAssess;
    }

    public int getpType() {
        return pType;
    }

    public void setpType(int pType) {
        this.pType = pType;
    }

    public boolean getpStatus() {
        return pStatus;
    }

    public void setpStatus(boolean pStatus) {
        this.pStatus = pStatus;
    }
}
