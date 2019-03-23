package com.example.finalproject;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.Adapter.AdapterListEating;
import com.example.finalproject.Entity.Product;
import com.example.finalproject.Entity.Recipes;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class AddRecipes extends Activity {
    //private List<Product> productList;
    final String URL="http://linhdv106.somee.com/WebService.asmx?WSDL";
    private Button btnShare;
    private EditText txtRawMaterial;
    private EditText txtProcessing;
    private EditText txtAttention;
    int recipesID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipes);
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        txtRawMaterial=findViewById(R.id.txtRawMaterial);
        txtAttention=findViewById(R.id.txtAttention);
        txtProcessing=findViewById(R.id.txtProcessing);
        btnShare=findViewById(R.id.btnShare);

        //productList=new ArrayList<>();
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(AddRecipes.this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(AddRecipes.this,
                    Manifest.permission.INTERNET)) {
            } else {
                ActivityCompat.requestPermissions(AddRecipes.this,
                        new String[]{Manifest.permission.INTERNET},
                        1);
            }
        } else {
            recipesID=loadProductID();
        }
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recipesID==0){
                    Toast.makeText(AddRecipes.this,"Error",Toast.LENGTH_LONG).show();
                }else {
                    String raw=txtRawMaterial.getText().toString();
                    String attention=txtAttention.getText().toString();
                    String processing=txtProcessing.getText().toString();
                    Recipes recipes=new Recipes();
                    recipes.setRecipesID(recipesID);
                    recipes.setRawMaterial(raw);
                    recipes.setAttention(attention);
                    recipes.setProcessing(processing);
                    // Here, thisActivity is the current activity
                    if (ContextCompat.checkSelfPermission(AddRecipes.this,
                            Manifest.permission.INTERNET)
                            != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(AddRecipes.this,
                                Manifest.permission.INTERNET)) {
                        } else {
                            ActivityCompat.requestPermissions(AddRecipes.this,
                                    new String[]{Manifest.permission.INTERNET},
                                    1);
                        }
                    } else {
                        uploadRecipes(recipes);
                    }
                }
            }
        });
    }
    public void uploadRecipes(Recipes recipes){
        try
        {
            final String NAMESPACE="http://tempuri.org/";
            final String METHOD_NAME="insertRecipes";
            final String SOAP_ACTION=NAMESPACE+METHOD_NAME;

            SoapObject request=new SoapObject
                    (NAMESPACE, METHOD_NAME);
            //tạo đối tượng SoapObject với tên cate như parameter trong service description
            SoapObject newRecipes=new
                    SoapObject(NAMESPACE, "recipe");
            //truyền giá trị cho các đối số (properties) như service desctiption
            newRecipes.addProperty("RecipesID",recipes.getRecipesID());
            newRecipes.addProperty("RawMaterial",recipes.getRawMaterial());
            newRecipes.addProperty("Processing",recipes.getProcessing());
            newRecipes.addProperty("Attention",recipes.getAttention());
            request.addSoapObject(newRecipes);

            SoapSerializationEnvelope envelope=
                    new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport=
                    new HttpTransportSE(URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);
            //vì hàm insertCatalog trả về kiểu int
            SoapPrimitive soapPrimitive= (SoapPrimitive)
                    envelope.getResponse();
            //chuyển về int để kiểm tra insert thành công hay thất bại
            //int ret=Integer.parseInt(soapPrimitive.toString());
            int ret=1;
            String msg="Insert Recipes Successful";
            if(ret<=0)
                msg="Insert Recipes Failed";
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }
        catch(Exception e)
        {
            Toast.makeText(this, "Insert Recipes Failed", Toast.LENGTH_LONG).show();
        }
    }
    private int loadProductID() {
        try{
            final String NAMESPACE="http://tempuri.org/";
            final String METHOD_NAME="getListProduct";
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
//            productList.clear();
            SoapObject soapItem =(SoapObject) soapArray.getProperty(soapArray.getPropertyCount()-1);
            int id=Integer.parseInt(soapItem.getProperty("ProductID").toString());
            return id;
        }
        catch(Exception e){
            return 0;
        }}

}
