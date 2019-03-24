package com.example.finalproject.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.Entity.Category;
import com.example.finalproject.ListEating;
import com.example.finalproject.R;

import java.util.List;

public class AdapterCategory extends BaseAdapter {

    private Activity context;
    private int layout;
    private List<Category> categoryList;

    public AdapterCategory(Activity context, int layout, List<Category> categoryList) {
        this.context = context;
        this.layout = layout;
        this.categoryList = categoryList;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View categoryView = convertView;
        if(categoryView == null){
            categoryView = context.getLayoutInflater().inflate(layout, null);
        }

        ImageView imageCategory = categoryView.findViewById(R.id.imageCategory);
        TextView txtCategory = categoryView.findViewById(R.id.txtCategory);

        txtCategory.setText(categoryList.get(position).getcName());
        if(categoryList.get(position).getCategoryID() == 1){
            imageCategory.setImageResource(R.drawable.a);
        }else if(categoryList.get(position).getCategoryID() == 2) {
            imageCategory.setImageResource(R.drawable.b);
        } else if(categoryList.get(position).getCategoryID() == 3) {
            imageCategory.setImageResource(R.drawable.c);
        } else if(categoryList.get(position).getCategoryID() == 4) {
            imageCategory.setImageResource(R.drawable.d);
        } else if(categoryList.get(position).getCategoryID() == 5) {
            imageCategory.setImageResource(R.drawable.e);
        }else if(categoryList.get(position).getCategoryID() == 6) {
            imageCategory.setImageResource(R.drawable.f);
        }
        categoryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ListEating.class);
                intent.putExtra("Category", categoryList.get(position));
                context.startActivity(intent);
            }
        });
        return categoryView;
    }
}
