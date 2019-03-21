package com.example.finalproject;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddProduct extends Activity {
    private Spinner spnCategory;
    int categoryID;
    private EditText txtName;

    private Uri filePath;
    //Firebase
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private final int PICK_IMAGE_REQUEST = 71;

    private ImageView imageView;
    private Button btnImage;
    private Spinner spnType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        spnCategory=findViewById(R.id.spnCategory);
        txtName=findViewById(R.id.txtName);
        imageView=findViewById(R.id.imageProduct);
        btnImage=findViewById(R.id.btnChoose);
        spnType=findViewById(R.id.spnType);
        List<String> listCategory=new ArrayList<>();
        listCategory.add("Hằng ngày");
        listCategory.add("Ngày Lễ");
        listCategory.add("Đặc sản miền Bắc");
        listCategory.add("Đặc sản miền Trung");
        listCategory.add("Đặc sản miền Nam");
        listCategory.add("Dã Ngoại");
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listCategory);
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
        ArrayAdapter<String> adapterTyoe = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listType);
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
    int type;
    public void addProduct(View view){
        categoryID=spnCategory.getSelectedItemPosition()+1;
        type=spnType.getSelectedItemPosition()+1;
        Intent intent=new Intent(AddProduct.this,AddRecipes.class);
        startActivity(intent);
    }
    private Uri url;
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
                                }
                            });
                            progressDialog.dismiss();
                            Toast.makeText(AddProduct.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddProduct.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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
