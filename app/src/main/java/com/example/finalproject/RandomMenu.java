package com.example.finalproject;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.finalproject.Adapter.AdapterListEating;
import com.example.finalproject.Entity.Product;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class RandomMenu extends AppCompatActivity {

    private List<Product> productList;
    private ListView listRandom;
    final String URL="http://linhdv106.somee.com/WebService.asmx?WSDL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_menu);

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        listRandom = findViewById(R.id.listRandom);
        productList = new ArrayList<>();

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(RandomMenu.this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(RandomMenu.this,
                    Manifest.permission.INTERNET)) {
            } else {
                ActivityCompat.requestPermissions(RandomMenu.this,
                        new String[]{Manifest.permission.INTERNET},
                        1);
            }
        } else {
            RandomMenu();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Ăn gì hôm nay?");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_load, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menuRotate:
                RandomMenu();
                return true;
            case R.id.menuHome:
                Intent intent = new Intent(RandomMenu.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.menuList:
                Intent intent1 = new Intent(RandomMenu.this, ListEating.class);
                startActivity(intent1);
                finish();
                return true;
            case R.id.menuShare:
                Intent intent2 = new Intent(RandomMenu.this, AddProduct.class);
                startActivity(intent2);
                finish();
                return true;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void RandomMenu() {
        try{
            final String NAMESPACE="http://tempuri.org/";
            final String METHOD_NAME="RandomMenu";
            final String SOAP_ACTION=NAMESPACE+METHOD_NAME;
            SoapObject request=new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope=
                    new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(request);
            MarshalFloat marshal=new MarshalFloat();
            marshal.register(envelope);
            HttpTransportSE androidHttpTransport=
                    new HttpTransportSE(URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapObject soapArray = (SoapObject) envelope.getResponse();
            productList.clear();
            for(int i = 0; i < soapArray.getPropertyCount(); i++)
            {
                SoapObject soapItem =(SoapObject) soapArray.getProperty(i);
                Product product = new Product();
                product.setProductID(Integer.parseInt(soapItem.getProperty("ProductID").toString()));
                product.setCategoryID(Integer.parseInt(soapItem.getProperty("CategoryID").toString()));
                product.setpName(soapItem.getProperty("Name").toString());
                product.setpImage(soapItem.getProperty("Image").toString());
                product.setpDescription(soapItem.getProperty("Description").toString());
                product.setpAssess(Integer.parseInt(soapItem.getProperty("Assess").toString()));
                product.setpType(Integer.parseInt(soapItem.getProperty("Type").toString()));
                if(Boolean.parseBoolean(soapItem.getProperty("Status").toString()) == true) {
                    product.setpStatus(true);
                }else {
                    product.setpStatus(false);
                }
                productList.add(product);
            }
            AdapterListEating adapterListEating = new AdapterListEating(this, R.layout.layout_eating, productList);
            listRandom.setAdapter(adapterListEating);
        }
        catch(Exception e){}}
}
