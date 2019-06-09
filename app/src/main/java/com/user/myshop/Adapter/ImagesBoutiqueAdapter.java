package com.user.myshop.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.user.myshop.R;

import java.util.ArrayList;

public class ImagesBoutiqueAdapter extends RecyclerView.Adapter<ImagesBoutiqueAdapter.ViewHolder>  {

    private ArrayList<String> list;
    private Activity activity;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView imgProduit;

        public ViewHolder(View itemView) {
            super(itemView);

            imgProduit = itemView.findViewById(R.id.image);

        }
    }

    public ImagesBoutiqueAdapter(ArrayList<String> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_image, viewGroup, false);

        return new ImagesBoutiqueAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        if (list.size() != 0)
            holder.imgProduit.setImageURI(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
