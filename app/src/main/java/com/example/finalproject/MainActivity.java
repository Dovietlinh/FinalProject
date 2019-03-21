package com.example.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button btnMenu;
    private Button btnRandom;
    private Button btnAdd;
    private Button btnExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnMenu=findViewById(R.id.btnMenu);
        btnAdd=findViewById(R.id.btnAdd);
        btnExit=findViewById(R.id.btnExit);
        btnRandom=findViewById(R.id.btnRandom);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ListEating.class);
                startActivity(intent);
            }
        });
    }
}
