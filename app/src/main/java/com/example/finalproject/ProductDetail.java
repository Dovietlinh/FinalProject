package com.example.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.finalproject.Entity.Product;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ProductDetail extends AppCompatActivity {
    private TextView txtNameProduct, txtDescriptionProduct, txtTypeProduct;
    private ImageView imageViewProduct;
    private Button btnBack, btnRecipes;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        imageViewProduct = findViewById(R.id.imageViewProduct);
        txtNameProduct = findViewById(R.id.txtNameProduct);
        txtDescriptionProduct = findViewById(R.id.txtDescriptionProduct);
        txtTypeProduct = findViewById(R.id.txtTypeProduct);
        ratingBar = findViewById(R.id.ratingBar);
        btnBack = findViewById(R.id.btnBack);
        btnRecipes = findViewById(R.id.btnRecipes);

        final Intent intent = getIntent();
        final Product product = (Product)intent.getSerializableExtra("ProductDetail");
        imageViewProduct.setImageBitmap(download_Image(product.getpImage()));
        txtNameProduct.setText(product.getpName());
        txtDescriptionProduct.setText(product.getpDescription());
        ratingBar.setRating(product.getpAssess());
        if(product.getpType() == 1){
            txtTypeProduct.setText("Món mặn");
        }else if(product.getpType() == 2){
            txtTypeProduct.setText("Món canh");
        }else if(product.getpType() == 3){
            txtTypeProduct.setText("Món tráng miệng");
        }else {
            txtTypeProduct.setText("Món khác");
        }

        btnRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ProductDetail.this, Recipes.class);
                intent1.putExtra("RecipesID", product.getProductID());
                startActivity(intent1);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Chi tiết món ăn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menuHome:
                Intent intent = new Intent(ProductDetail.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.menuList:
                Intent intent1 = new Intent(ProductDetail.this, ListEating.class);
                startActivity(intent1);
                finish();
                return true;
            case R.id.menuCategory:
                Intent intent4 = new Intent(ProductDetail.this, ListCategory.class);
                startActivity(intent4);
                finish();
                return true;
            case R.id.menuRandom:
                Intent intent2 = new Intent(ProductDetail.this, RandomMenu.class);
                startActivity(intent2);
                finish();
                return true;
            case R.id.menuShare:
                Intent intent3 = new Intent(ProductDetail.this, AddProduct.class);
                startActivity(intent3);
                finish();
                return true;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}
