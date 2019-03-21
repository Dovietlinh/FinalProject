package com.example.finalproject.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.finalproject.Entity.Product;
import com.example.finalproject.R;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        View listEating = convertView;
        if(listEating == null){
            listEating = context.getLayoutInflater().inflate(layout, null);
        }
        TextView txtName = listEating.findViewById(R.id.txtName);
        txtName.setText(productList.get(position).getpName());
        if(position % 2 == 0){
            listEating.setBackgroundColor(Color.LTGRAY);
        }
        return listEating;
    }
}
