package com.user.myshop.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.user.myshop.BoutiqueActivity;
import com.user.myshop.Models.Boutiques;
import com.user.myshop.Models.RSResponse;
import com.user.myshop.R;
import com.user.myshop.Utils.Helpers;
import com.user.myshop.Webservice.WebService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BotuiqueAdapter extends BaseAdapter {

    private List<Boutiques> list;
    private Activity activity;
    private boolean isfavore = false;

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

    private ImageView unfollowed, followed;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
//        convertView = layoutInflater.inflate(R.layout.item_porduit, parent,false);
        LayoutInflater inflator = activity.getLayoutInflater();
        if (convertView == null) {
            convertView = inflator.inflate(R.layout.item_boutique, null);

            TextView ID = convertView.findViewById(R.id.Id);
            TextView nomP = convertView.findViewById(R.id.NomProduit);
            TextView categorie = convertView.findViewById(R.id.categorie);
            TextView prix = convertView.findViewById(R.id.Prix);
            SimpleDraweeView imgProduit = convertView.findViewById(R.id.imgProduit);
            followed = convertView.findViewById(R.id.followed);
            unfollowed = convertView.findViewById(R.id.unfollowed);

            ID.setText(String.valueOf(list.get(position).getId()));
            nomP.setText(list.get(position).getNomProd());
            categorie.setText(list.get(position).getCategorie());
            prix.setText(list.get(position).getPrix());

            if (list.get(position).getListimage().size() != 0)
                imgProduit.setImageURI(list.get(position).getListimage().get(0));
            //verifier produit favore
            //ISfavore(list.get(position).getIdUser(), list.get(position).getId());


            followed.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (Helpers.isConnected(activity)) {
                        //followed(list.get(position).getIdUser(), list.get(position).getId());
                        followed.setVisibility(View.GONE);
                        unfollowed.setVisibility(View.VISIBLE);
                    } else {
                        Helpers.ShowMessageConnection(activity);
                    }

                }
            });

            unfollowed.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (Helpers.isConnected(activity)) {
                        //unfollowed(list.get(position).getId());
                        unfollowed.setVisibility(View.VISIBLE);
                        followed.setVisibility(View.GONE);
                    } else {
                        Helpers.ShowMessageConnection(activity);
                    }
                }
            });
        }
        return convertView;
    }

    private void ISfavore(String idUser, int id_bout) {

        Call<RSResponse> callUpload = WebService.getInstance().getApi().isFavoree(String.valueOf(id_bout), idUser);
        callUpload.enqueue(new Callback<RSResponse>() {
            @Override
            public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                if (response.body().getStatus() == 1) {
                    isfavore = true;
                    unfollowed.setVisibility(View.VISIBLE);
                    followed.setVisibility(View.GONE);
                } else if (response.body().getStatus() == 2) {
                    isfavore = false;
                    followed.setVisibility(View.VISIBLE);
                    unfollowed.setVisibility(View.GONE);
                } else if (response.body().getStatus() == 0) {
                    Toast.makeText(activity, "rrr", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RSResponse> call, Throwable t) {
                Log.d("err", t.getMessage());
            }
        });
    }

    private void followed(String idUser, int id_bout) {
        Call<RSResponse> callUpload = WebService.getInstance().getApi().AddFavorite(String.valueOf(id_bout), idUser);
        callUpload.enqueue(new Callback<RSResponse>() {
            @Override
            public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                if (response.body().getStatus() == 1) {
                    // unfollowed.setVisibility(View.GONE);
                    Log.i("ff", "flo");
                    //followed.setVisibility(View.VISIBLE);
                    Toast.makeText(activity, activity.getString(R.string.suivi), Toast.LENGTH_SHORT).show();
                } else if (response.body().getStatus() == 0) {
                    Toast.makeText(activity, "rrr", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RSResponse> call, Throwable t) {
                Log.d("err", t.getMessage());
            }
        });
    }

    private void unfollowed(int id_bout) {
        if (Helpers.isConnected(activity)) {
            Call<RSResponse> callUpload = WebService.getInstance().getApi().DeleteFavorite(String.valueOf(id_bout));
            callUpload.enqueue(new Callback<RSResponse>() {
                @Override
                public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                    if (response.body().getStatus() == 1) {
                        //unfollowed.setVisibility(View.VISIBLE);
                        Log.i("ff", "inflo");
                        //followed.setVisibility(View.GONE);
                        Toast.makeText(activity, activity.getString(R.string.no_suvi), Toast.LENGTH_SHORT).show();
                    } else if (response.body().getStatus() == 0) {
                        Toast.makeText(activity, "rrr", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RSResponse> call, Throwable t) {
                    Log.d("err", t.getMessage());
                }
            });
        } else {
            Helpers.ShowMessageConnection(activity);
        }
    }


}
