package com.example.finalproject.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.Entity.Product;
import com.example.finalproject.ProductDetail;
import com.example.finalproject.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class AdapterListEating extends BaseAdapter {
    private Activity context;
    private int layout;
    private List<Product> productList;

    public AdapterListEating(Activity context, int layout, List<Product> productList) {
        this.context = context;
        this.layout = layout;
        this.productList = productList;
    }

    public AdapterListEating() {
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View listEating = convertView;
        if(listEating == null){
            listEating = context.getLayoutInflater().inflate(layout, null);
        }
        TextView txtName = listEating.findViewById(R.id.txtName);
        TextView txtType = listEating.findViewById(R.id.txtType);
        ImageView imageView = listEating.findViewById(R.id.imageView);

        txtName.setText(productList.get(position).getpName());
        if(productList.get(position).getpType() == 1){
            txtType.setText("Món mặn");
        }else if(productList.get(position).getpType() == 2){
            txtType.setText("Món canh");
        }else if(productList.get(position).getpType() == 3){
            txtType.setText("Món tráng miệng");
        }else {
            txtType.setText("Món khác");
        }
        imageView.setImageBitmap(download_Image(productList.get(position).getpImage()));

        listEating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetail.class);
                intent.putExtra("ProductDetail", productList.get(position));
                context.startActivity(intent);
            }
        });
        return listEating;
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
}
