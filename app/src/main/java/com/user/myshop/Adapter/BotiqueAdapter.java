package com.user.myshop.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.user.myshop.Models.Botique;

import com.user.myshop.R;

import java.util.ArrayList;
import java.util.List;

public class BotiqueAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
     private List<Botique> list;
    private Activity activity;

    public BotiqueAdapter(Activity  context, ArrayList<Botique> list) {
        super();
        this.activity=context;
        this.list=list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return  list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
        public View getView(int position, View convertView, ViewGroup parent) {
//        convertView = layoutInflater.inflate(R.layout.item_porduit, parent,false);

        RecyclerView.ViewHolder view;
        LayoutInflater inflator = activity.getLayoutInflater();
        if(convertView==null)
        {
            convertView = inflator.inflate(R.layout.item_botique, null);

            TextView nomP = convertView.findViewById(R.id.NomProduit);
            TextView marque = convertView.findViewById(R.id.Marque);
            TextView prix = convertView.findViewById(R.id.Prix);
            ImageView imgProduit = convertView.findViewById(R.id.imgProduit);

            nomP.setText(list.get(position).getProduit().getNom());
            marque.setText(list.get(position).getProduit().getMarque());
            prix.setText(list.get(position).getProduit().getPrix());

//            prix.setText( list.get(position).getPrix());

//        Picasso.get()
//                .load(list.get(position).getProduit().getImage())
//                .resize(400,500)
//                .into(imgProduit);


        }


        return convertView;
    }


}
