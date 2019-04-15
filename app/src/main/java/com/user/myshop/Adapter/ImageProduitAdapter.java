package com.user.myshop.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.user.myshop.R;

import java.util.ArrayList;
import java.util.List;

public class ImageProduitAdapter extends BaseAdapter {

    private ArrayList<String> list;
    private Activity activity;

    public ImageProduitAdapter(ArrayList<String> list, Activity activity) {
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
        LayoutInflater inflator = activity.getLayoutInflater();
        if (convertView == null) {
            convertView = inflator.inflate(R.layout.item_image, null);

            SimpleDraweeView imgProduit = convertView.findViewById(R.id.image);
            if (list.size()!=0)
                imgProduit.setImageURI(list.get(position));

        }
        return convertView;
    }
}
