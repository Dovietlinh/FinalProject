package com.example.finalproject.Entity;

import java.io.Serializable;

public class Recipes implements Serializable {
    private int recipesID;
    private String rawMaterial;
    private String processing;
    private String attention;

    public Recipes() {
    }

    public Recipes(int recipesID, String rawMaterial, String processing, String attention) {
        this.recipesID = recipesID;
        this.rawMaterial = rawMaterial;
        this.processing = processing;
        this.attention = attention;
    }

    public int getRecipesID() {
        return recipesID;
    }

    public void setRecipesID(int recipesID) {
        this.recipesID = recipesID;
    }

    public String getRawMaterial() {
        return rawMaterial;
    }

    public void setRawMaterial(String rawMaterial) {
        this.rawMaterial = rawMaterial;
    }

    public String getProcessing() {
        return processing;
    }

    public void setProcessing(String processing) {
        this.processing = processing;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }
}

