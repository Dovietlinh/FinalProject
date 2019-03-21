package com.example.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
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
    String recipesID = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        txtAttention = findViewById(R.id.txtAttention);
        txtProcessing = findViewById(R.id.txtProcessing);
        txtRawMaterial = findViewById(R.id.txtRawMaterial);

        Intent intent = getIntent();
        recipesID = intent.getStringExtra("RecipesID");
        getRecipes();

    }
    public void getRecipes()
    {
        try{
            final String NAMESPACE="http://tempuri.org/";
            final String METHOD_NAME="getRecipesByProductID";
            final String SOAP_ACTION=NAMESPACE+METHOD_NAME;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("id", recipesID);
            SoapSerializationEnvelope envelope=
                    new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(request);
            MarshalFloat marshal = new MarshalFloat();
            marshal.register(envelope);
            HttpTransportSE androidHttpTransport=
                    new HttpTransportSE(URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapObject soapItem = (SoapObject) envelope.getResponse();
            txtRawMaterial.setText(soapItem.getProperty("RawMaterial").toString());
            txtProcessing.setText(soapItem.getProperty("Processing").toString());
            txtAttention.setText(soapItem.getProperty("Attention").toString());
        }
        catch(Exception e) {}
    }
}
