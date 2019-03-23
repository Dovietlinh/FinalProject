package com.example.finalproject.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class AdapterListRandom extends BaseAdapter {
    private Activity context;
    private int layout;
    private List<Product> productList;

    public AdapterListRandom(Activity context, int layout, List<Product> productList) {
        this.context = context;
        this.layout = layout;
        this.productList = productList;
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
        TextView txtHashTag = listEating.findViewById(R.id.txtHashTag);
        TextView txtAssess = listEating.findViewById(R.id.txtAssess);
        ImageView imageView = listEating.findViewById(R.id.imageView);

        txtName.setText(productList.get(position).getpName());
        txtAssess.setText(productList.get(position).getpAssess() +"/10");
        if(productList.get(position).getpType() == 1){
            txtHashTag.setText("Món chính");
        }else if(productList.get(position).getpType() == 2){
            txtHashTag.setText("Món canh");
        }else if(productList.get(position).getpType() == 3){
            txtHashTag.setText("Món tráng miệng");
        }else {
            txtHashTag.setText("Món khác");
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
