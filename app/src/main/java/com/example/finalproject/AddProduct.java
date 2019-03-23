package com.example.finalproject;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.finalproject.Entity.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class AddProduct extends Activity {
    private Spinner spnCategory;
    private EditText txtName;

    private Uri url=null;
    private Uri filePath;
    //Firebase
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private final int PICK_IMAGE_REQUEST = 71;

    private ImageView imageView;
    private Button btnImage;
    private Spinner spnType;
    private EditText txtDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        spnCategory=findViewById(R.id.spnCategory);
        txtName=findViewById(R.id.txtName);
        imageView=findViewById(R.id.imageProduct);
        btnImage=findViewById(R.id.btnChoose);
        spnType=findViewById(R.id.spnType);
        txtDescription=findViewById(R.id.txtDescription);
        List<String> listCategory=new ArrayList<>();
        listCategory.add("Hằng ngày");
        listCategory.add("Ngày Lễ");
        listCategory.add("Đặc sản miền Bắc");
        listCategory.add("Đặc sản miền Trung");
        listCategory.add("Đặc sản miền Nam");
        listCategory.add("Dã Ngoại");
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,listCategory);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnCategory.setAdapter(adapter);

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(AddProduct.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(AddProduct.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    } else {
                        ActivityCompat.requestPermissions(AddProduct.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                1);
                    }
                } else {
                    chooseImage();
                }
            }
        });

        List<String> listType=new ArrayList<>();
        listType.add("Món Mặn");
        listType.add("Món Canh");
        listType.add("Món Tráng Miệng");
        ArrayAdapter<String> adapterTyoe = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,listType);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnType.setAdapter(adapterTyoe);


    }
    private void chooseImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    final String URL="http://linhdv106.somee.com/WebService.asmx?WSDL";
    public void addProduct(View view){
        if (ContextCompat.checkSelfPermission(AddProduct.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(AddProduct.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(AddProduct.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        } else {
            uploadImage();
            Intent intent=new Intent(AddProduct.this,AddRecipes.class);
            startActivity(intent);
        }


    }
    public void uploadProduct(Product product){
        try
        {
            final String NAMESPACE="http://tempuri.org/";
            final String METHOD_NAME="insertProduct";
            final String SOAP_ACTION=NAMESPACE+METHOD_NAME;

            SoapObject request=new SoapObject
                    (NAMESPACE, METHOD_NAME);
            //tạo đối tượng SoapObject với tên cate như parameter trong service description
            SoapObject newProduct=new
                    SoapObject(NAMESPACE, "product");
            //truyền giá trị cho các đối số (properties) như service desctiption
            newProduct.addProperty("CategoryID",product.getCategoryID());
            newProduct.addProperty("Name",product.getpName());
            newProduct.addProperty("Image",product.getpImage());
            newProduct.addProperty("Description",product.getpDescription());
            newProduct.addProperty("Type",product.getpType());
            newProduct.addProperty("Status",product.getpStatus());
            newProduct.addProperty("Assess",product.getpAssess());
            request.addSoapObject(newProduct);

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
//            int ret=1;
//            String msg="Insert Cate Successful";
//            if(ret<=0)
//                msg="Insert Cate Failed";
//            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }
        catch(Exception e)
        {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
        }
    }
    private void uploadImage() {

        if(filePath != null)
        {
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri downloadUrl = uri;
                                    //Do what you want with the url
                                    url=downloadUrl;
                                    int categoryID=spnCategory.getSelectedItemPosition()+1;
                                    int type=spnType.getSelectedItemPosition()+1;
                                    String urlImage="";
                                    if(url!=null){
                                        urlImage=url.toString();
                                    }else{
                                        urlImage="https://firebasestorage.googleapis.com/v0/b/projectandroid-3cf66.appspot.com/o/653b6320aa80ab406cf5420f19f7a554.jpg?alt=media&token=bd46b0b1-f0dd-4024-9dff-44fd666906a8";
                                    }

                                    String description=txtDescription.getText().toString();
                                    Product product=new Product();
                                    product.setCategoryID(categoryID);
                                    product.setpDescription(description);
                                    product.setpImage(urlImage);
                                    product.setpName(txtName.getText().toString());
                                    product.setpType(type);
                                    product.setpStatus(true);
                                    product.setpAssess(5);

                                    // Here, thisActivity is the current activity
                                    if (ContextCompat.checkSelfPermission(AddProduct.this,
                                            Manifest.permission.INTERNET)
                                            != PackageManager.PERMISSION_GRANTED) {
                                        if (ActivityCompat.shouldShowRequestPermissionRationale(AddProduct.this,
                                                Manifest.permission.INTERNET)) {
                                        } else {
                                            ActivityCompat.requestPermissions(AddProduct.this,
                                                    new String[]{Manifest.permission.INTERNET},
                                                    1);
                                        }
                                    } else {
                                        if(product.getpName().isEmpty()){
                                            Toast.makeText(AddProduct.this, "Sorry", Toast.LENGTH_SHORT).show();
                                        }
                                        uploadProduct(product);
                                    }

                                }
                            });
                            progressDialog.dismiss();
                            //Toast.makeText(AddProduct.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            //Toast.makeText(AddProduct.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
}
