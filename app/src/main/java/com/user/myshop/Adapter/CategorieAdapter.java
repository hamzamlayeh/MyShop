package com.user.myshop.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.user.myshop.Models.Categories;
import com.user.myshop.R;

import java.util.List;

public class CategorieAdapter extends BaseAdapter {
    private List<Categories> list;
    private Activity activity;

    public CategorieAdapter(Activity activity, List<Categories> list) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_categorie, null);
        TextView id = view.findViewById(R.id.id);
        TextView nomC = view.findViewById(R.id.nomC);

        id.setText(String.valueOf(list.get(position).getId_cat()));
        nomC.setText(list.get(position).getNomCat());

        return view;
    }
}
