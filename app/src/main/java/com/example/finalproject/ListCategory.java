package com.example.finalproject;

import android.Manifest;
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
import android.widget.ListView;
import android.widget.SearchView;

import com.example.finalproject.Adapter.AdapterCategory;
import com.example.finalproject.Adapter.AdapterListEating;
import com.example.finalproject.Entity.Category;
import com.example.finalproject.Entity.Product;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class ListCategory extends AppCompatActivity {

    private List<Category> categoryList;
    private ListView listViewCategory;
    final String URL="http://linhdv106.somee.com/WebService.asmx?WSDL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_category);
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        listViewCategory = findViewById(R.id.listViewCategory);
        categoryList = new ArrayList<>();

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(ListCategory.this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(ListCategory.this,
                    Manifest.permission.INTERNET)) {
            } else {
                ActivityCompat.requestPermissions(ListCategory.this,
                        new String[]{Manifest.permission.INTERNET},
                        1);
            }
        } else {
            LoadListCategory();
        }
    }
    private void LoadListCategory() {
        try{
            final String NAMESPACE="http://tempuri.org/";
            final String METHOD_NAME="getListCategory";
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
            categoryList.clear();
            for(int i = 0; i < soapArray.getPropertyCount(); i++)
            {
                SoapObject soapItem =(SoapObject) soapArray.getProperty(i);
                Category category = new Category();
                category.setCategoryID(Integer.parseInt(soapItem.getProperty("CategoryID").toString()));
                category.setcName(soapItem.getProperty("CategoryName").toString());
                if(Boolean.parseBoolean(soapItem.getProperty("Status").toString()) == true) {
                    category.setcStatus(true);
                }else {
                    category.setcStatus(false);
                }
                categoryList.add(category);
            }
            AdapterCategory adapterCategory = new AdapterCategory(this, R.layout.layout_category, categoryList);
            listViewCategory.setAdapter(adapterCategory);
        }
        catch(Exception e){}
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Nhóm các món ăn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_category, menu);
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
                Intent intent = new Intent(ListCategory.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.menuList:
                Intent intent1 = new Intent(ListCategory.this, ListEating.class);
                startActivity(intent1);
                finish();
                return true;
            case R.id.menuRandom:
                Intent intent2 = new Intent(ListCategory.this, RandomMenu.class);
                startActivity(intent2);
                finish();
                return true;
            case R.id.menuShare:
                Intent intent3 = new Intent(ListCategory.this, AddProduct.class);
                startActivity(intent3);
                finish();
                return true;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}
