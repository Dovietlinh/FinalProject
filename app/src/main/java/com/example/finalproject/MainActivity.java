package com.example.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.finalproject.Entity.Product;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private Button btnMenu;
    private Button btnRandom;
    private Button btnAdd;
    private Button btnExit;
    private Button btnCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btnMenu=findViewById(R.id.btnMenu);
        btnAdd=findViewById(R.id.btnAdd);
        btnExit=findViewById(R.id.btnExit);
        btnRandom=findViewById(R.id.btnRandom);
        btnCategory=findViewById(R.id.btnCategory);

        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ListCategory.class);
                startActivity(intent);
            }
        });
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ListEating.class);
                startActivity(intent);
            }
        });
        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RandomMenu.class);
                startActivity(intent);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddProduct.class);
                startActivity(intent);
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
