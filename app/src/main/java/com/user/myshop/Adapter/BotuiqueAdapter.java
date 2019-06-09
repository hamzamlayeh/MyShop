package com.user.myshop.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.user.myshop.Models.Boutiques;
import com.user.myshop.R;
import java.util.List;

public class BotuiqueAdapter extends BaseAdapter {

    private List<Boutiques> list;
    private Activity activity;

    public BotuiqueAdapter(Activity context, List<Boutiques> list) {
        super();
        this.activity = context;
        this.list = list;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
//        convertView = layoutInflater.inflate(R.layout.item_porduit, parent,false);
        LayoutInflater inflator = activity.getLayoutInflater();
        if (convertView == null) {
            convertView = inflator.inflate(R.layout.item_boutiques, null);

            TextView ID = convertView.findViewById(R.id.Id);
            TextView nomP = convertView.findViewById(R.id.NomProduit);
            TextView categorie = convertView.findViewById(R.id.categorie);
            TextView prix = convertView.findViewById(R.id.Prix);
            SimpleDraweeView imgProduit = convertView.findViewById(R.id.imgBoutique);

            ID.setText(String.valueOf(list.get(position).getId()));
            nomP.setText(list.get(position).getNomProd());
            categorie.setText(list.get(position).getCategorie());
            prix.setText(list.get(position).getPrix());

            if (list.get(position).getListimage().size() != 0)
                imgProduit.setImageURI(list.get(position).getListimage().get(0));

        }
        return convertView;
    }
}
