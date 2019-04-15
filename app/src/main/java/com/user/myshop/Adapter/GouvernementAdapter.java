package com.user.myshop.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.user.myshop.Models.Gouvernements;
import com.user.myshop.R;

import java.util.List;

public class GouvernementAdapter extends BaseAdapter {
    private List<Gouvernements> list;
    private Activity activity;

    public GouvernementAdapter(Activity activity, List<Gouvernements> list) {
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
        View view = inflater.inflate(R.layout.item_gouvernerat, null);
        TextView id = view.findViewById(R.id.id);
        TextView nomG = view.findViewById(R.id.nomG);

        id.setText(String.valueOf(list.get(position).getId()));
        nomG.setText(list.get(position).getNom());

        return view;
    }
}
