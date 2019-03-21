package com.example.finalproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.Entity.Product;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ProductDetail extends AppCompatActivity {
    private TextView txtNameProduct, txtDescriptionProduct, txtAssessProduct, txtTypeProduct;
    private ImageView imageViewProduct;
    private Button btnBack, btnRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        imageViewProduct = findViewById(R.id.imageViewProduct);
        txtNameProduct = findViewById(R.id.txtNameProduct);
        txtDescriptionProduct = findViewById(R.id.txtDescriptionProduct);
        txtAssessProduct = findViewById(R.id.txtAssessProduct);
        txtTypeProduct = findViewById(R.id.txtTypeProduct);
        btnBack = findViewById(R.id.btnBack);
        btnRecipes = findViewById(R.id.btnRecipes);

        final Intent intent = getIntent();
        final Product product = (Product)intent.getSerializableExtra("ProductDetail");
        imageViewProduct.setImageBitmap(download_Image(product.getpImage()));
        txtNameProduct.setText(product.getpName());
        txtDescriptionProduct.setText(product.getpDescription());
        txtAssessProduct.setText(String.valueOf(product.getpAssess()));
        txtTypeProduct.setText(String.valueOf(product.getpType()));

        btnRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ProductDetail.this, Recipes.class);
                intent1.putExtra("RecipesID", String.valueOf(product.getProductID()));
                startActivity(intent1);
            }
        });
    }
    public static Bitmap download_Image(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e("Hub","Error getting the image from server : " + e.getMessage());
        }
        return bm;
    }
}
