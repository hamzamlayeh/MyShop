package com.user.myshop;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.user.myshop.Adapter.ImageProduitAdapter;
import com.user.myshop.Models.Produits;
import com.user.myshop.Models.RSResponse;
import com.user.myshop.Utils.Helpers;
import com.user.myshop.Webservice.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProduitDetails extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TextView NomProd, Prix, Description, Date;
    SimpleDraweeView Img_Prod;
    GridView grid;
    String ID_Prod;
    Produits Produit;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produit_details);
        activity = this;
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        NomProd = findViewById(R.id.NomP);
        Prix = findViewById(R.id.Prix);
        Description = findViewById(R.id.Description);
        Date = findViewById(R.id.Date);
        Img_Prod = findViewById(R.id.imageprod);
        grid = findViewById(R.id.grid_Prod);

        Helpers.AddMenu(activity, bottomNavigationView);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ID_Prod = extras.getString("ID_Prod");
            if (Helpers.isConnected(activity)) {
                GetProduit();
            } else {
                Helpers.ShowMessageConnection(activity);
            }
        }
    }

    public void GetProduit() {
        Call<RSResponse> callUpload = WebService.getInstance().getApi().DetailProduit(ID_Prod);
        callUpload.enqueue(new Callback<RSResponse>() {
            @Override
            public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 1) {
                        Produit = new Gson().fromJson(new Gson().toJson(response.body().getData()), Produits.class);
                        SetProduit(Produit);
                    } else if (response.body().getStatus() == 0) {

                        Toast.makeText(ProduitDetails.this, "rrr", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RSResponse> call, Throwable t) {
                Log.d("err", t.getMessage());
            }
        });
    }

    public void SetProduit(Produits produits) {
        if (produits != null) {
            NomProd.setText(produits.getNom());
            Prix.setText(produits.getPrix());
            Description.setText(produits.getDescription());
            Date.setText(produits.getDate());
            if (produits.getListimage().size() != 0)
                Img_Prod.setImageURI(produits.getListimage().get(0));
        }
    }

    public void LoadPhoto(View view) {
        Log.i("size", Produit.getListimage().size() + "");
        if (Produit != null) {
            ImageProduitAdapter imageProduitAdapter = new ImageProduitAdapter(Produit.getListimage(), activity);
            grid.setAdapter(imageProduitAdapter);
        }
    }

    public void AddBoutique(View view) {
        if (ID_Prod != null) {
            if (Helpers.isConnected(activity)) {
                Call<RSResponse> callUpload = WebService.getInstance().getApi().boutiqueExiste(ID_Prod);
                callUpload.enqueue(new Callback<RSResponse>() {
                    @Override
                    public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                        if (response.body().getStatus() == 1) {
                            Intent intent = new Intent(activity, AddBoutiqueActivity.class);
                            intent.putExtra("ID_Prod", ID_Prod);
                            startActivity(intent);
                        } else if (response.body().getStatus() == 0) {
                            Toast.makeText(activity, getString(R.string.produit_existe), Toast.LENGTH_SHORT).show();
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

    public void SupprimerProduit(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle(R.string.supp_prod)
                .setIcon(R.drawable.ic_delete)
                .setMessage("\n" + getString(R.string.question_supp_prod))
                .setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final ProgressDialog loading = ProgressDialog.show(activity, "Traitement Des Données...", "S'il Vous Plaît, Attendez...", false, false);
                        if (Helpers.isConnected(activity)) {
                            Call<RSResponse> callUpload = WebService.getInstance().getApi().DeleteProduit(ID_Prod);
                            callUpload.enqueue(new Callback<RSResponse>() {
                                @Override
                                public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                                    loading.dismiss();
                                    if (response.body().getStatus() == 1) {
                                        Toast.makeText(activity, getString(R.string.produit_supp), Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(activity, ProduitsActivity.class));
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
                }).setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }
}
