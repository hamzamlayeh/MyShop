package com.user.myshop.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.user.myshop.Models.Favorites;
import com.user.myshop.R;

import java.util.List;

public class FavoriteAdapter extends BaseAdapter {

    private List<Favorites> list;
    private Activity activity;

    public FavoriteAdapter(Activity context, List<Favorites> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
//        convertView = layoutInflater.inflate(R.layout.item_porduit, parent,false);
        LayoutInflater inflator = activity.getLayoutInflater();
        if (convertView == null) {
            convertView = inflator.inflate(R.layout.item_favorite, null);

            TextView nomP = convertView.findViewById(R.id.NomProduit);
            TextView marque = convertView.findViewById(R.id.Marque);
            TextView prix = convertView.findViewById(R.id.Prix);
            TextView description = convertView.findViewById(R.id.Description);
            ImageView imgProduit = convertView.findViewById(R.id.imgProduit);

            nomP.setText(list.get(position).getBoutique().getNomProd());
            description.setText(list.get(position).getBoutique().getDescription());
            prix.setText(list.get(position).getBoutique().getPrix());

            if (!list.get(position).getBoutique().getMarque().isEmpty()){
                marque.setVisibility(View.VISIBLE);
                marque.setText(list.get(position).getBoutique().getMarque());
            }

            if (list.get(position).getBoutique().getListimage().size() > 0) {
                Picasso.get()
                        .load(list.get(position).getBoutique().getListimage().get(0))
                        .resize(400, 500)
                        .into(imgProduit);
            }
        }
        return convertView;
    }
}
