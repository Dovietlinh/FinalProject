package com.example.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.Entity.Product;

public class ProductDetail extends AppCompatActivity {
    private TextView txtNameProduct, txtDescriptionProduct, txtAssessProduct, txtTypeProduct;
    private ImageView imageViewProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        txtNameProduct = findViewById(R.id.txtNameProduct);
        txtDescriptionProduct = findViewById(R.id.txtDescriptionProduct);
        txtAssessProduct = findViewById(R.id.txtAssessProduct);
        txtTypeProduct = findViewById(R.id.txtTypeProduct);

        Intent intent = getIntent();
        Product product = (Product)intent.getSerializableExtra("ProductDetail");
        txtNameProduct.setText(product.getpName());
        txtDescriptionProduct.setText(product.getpDescription());
        txtAssessProduct.setText(String.valueOf(product.getpAssess()));
        txtTypeProduct.setText(String.valueOf(product.getpType()));
    }
}
