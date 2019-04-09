package com.user.myshop.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.user.myshop.Models.Boutiques;
import com.user.myshop.R;

import java.util.List;

public class BotuiqueAdapter extends BaseAdapter {

     private List<Boutiques> list;
    private Activity activity;

    public BotuiqueAdapter(Activity  context, List<Boutiques> list) {
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

    boolean isFavo=false;
    @Override
        public View getView(int position, View convertView, ViewGroup parent) {
//        convertView = layoutInflater.inflate(R.layout.item_porduit, parent,false);
        LayoutInflater inflator = activity.getLayoutInflater();
        if(convertView==null)
        {
            convertView = inflator.inflate(R.layout.item_boutique, null);

            TextView ID = convertView.findViewById(R.id.Id);
            TextView nomP = convertView.findViewById(R.id.NomProduit);
            TextView marque = convertView.findViewById(R.id.Marque);
            TextView prix = convertView.findViewById(R.id.Prix);
            ImageView imgProduit = convertView.findViewById(R.id.imgProduit);
            ImageView fav = convertView.findViewById(R.id.fav);
            final ImageView ClicFav = convertView.findViewById(R.id.ClicFav);

            ID.setText(String.valueOf(list.get(position).getId()));
            nomP.setText(list.get(position).getNomProd());
            marque.setText(list.get(position).getMarque());
            prix.setText(list.get(position).getPrix());

            fav.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ClicFav.setVisibility(View.VISIBLE);
                }
            });

            ClicFav.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ClicFav.setVisibility(View.INVISIBLE);
            }
        });
//        Picasso.get()
//                .load(list.get(position).getProduit().getImage())
//                .resize(400,500)
//                .into(imgProduit);
        }
        return convertView;
    }


}
