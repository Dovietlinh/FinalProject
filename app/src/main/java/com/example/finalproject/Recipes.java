package com.example.finalproject;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.finalproject.Entity.Product;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class Recipes extends AppCompatActivity {

    private TextView txtAttention, txtProcessing, txtRawMaterial;
    final String URL="http://linhdv106.somee.com/WebService.asmx?WSDL";
    int recipesID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        txtAttention = findViewById(R.id.txtAttention);
        txtProcessing = findViewById(R.id.txtProcessing);
        txtRawMaterial = findViewById(R.id.txtRawMaterial);

        Intent intent = getIntent();
        recipesID = intent.getIntExtra("RecipesID", 0);
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(Recipes.this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Recipes.this,
                    Manifest.permission.INTERNET)) {
            } else {
                ActivityCompat.requestPermissions(Recipes.this,
                        new String[]{Manifest.permission.INTERNET},
                        1);
            }
        } else {
            getRecipes();
        }
    }
    public void getRecipes()
    {
        try{
            final String NAMESPACE="http://tempuri.org/";
            final String METHOD_NAME="getRecipesByProductID";
            final String SOAP_ACTION=NAMESPACE+METHOD_NAME;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("id", recipesID);
            SoapSerializationEnvelope envelope =
                    new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(request);
            MarshalFloat marshal = new MarshalFloat();
            marshal.register(envelope);
            HttpTransportSE androidHttpTransport =
                    new HttpTransportSE(URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapObject soapItem = (SoapObject) envelope.getResponse();
            txtRawMaterial.setText(soapItem.getProperty("RawMaterial").toString());
            txtProcessing.setText(soapItem.getProperty("Processing").toString());
            txtAttention.setText(soapItem.getProperty("Attention").toString());
        }
        catch(Exception e) {}
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Cách chế biến");
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
                Intent intent = new Intent(Recipes.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.menuList:
                Intent intent1 = new Intent(Recipes.this, ListEating.class);
                startActivity(intent1);
                finish();
                return true;
            case R.id.menuRandom:
                Intent intent2 = new Intent(Recipes.this, RandomMenu.class);
                startActivity(intent2);
                finish();
                return true;
            case R.id.menuShare:
                Intent intent3 = new Intent(Recipes.this, AddProduct.class);
                startActivity(intent3);
                finish();
                return true;
            default:break;
        }

        return super.onOptionsItemSelected(item);
    }
}
