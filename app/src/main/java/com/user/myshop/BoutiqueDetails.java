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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.user.myshop.Models.Boutiques;
import com.user.myshop.Models.RSResponse;
import com.user.myshop.Utils.Helpers;
import com.user.myshop.Webservice.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoutiqueDetails extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TextView Prix, Nom, Date, Location, Categorie, Marque,Description;
    ImageView iconMarque;
    String ID_Bout;
    Boutiques boutiques;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_botuique_details);
        activity = this;
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        Prix = findViewById(R.id.Prix);
        Nom = findViewById(R.id.nom);
        Date = findViewById(R.id.date);
        Location = findViewById(R.id.location);
        Categorie = findViewById(R.id.categorie);
        Marque = findViewById(R.id.Marque);
        Description = findViewById(R.id.description);
        iconMarque = findViewById(R.id.imageView40);


        Helpers.AddMenu(activity, bottomNavigationView);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ID_Bout = extras.getString("ID_Bout");
            if (Helpers.isConnected(activity))
                GetBoutique();
            else
                Helpers.ShowMessageConnection(activity);
        }
    }

    private void GetBoutique() {
        Call<RSResponse> callUpload = WebService.getInstance().getApi().DetailBoutique(ID_Bout);
        callUpload.enqueue(new Callback<RSResponse>() {
            @Override
            public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 1) {
                        boutiques = new Gson().fromJson(new Gson().toJson(response.body().getData()), Boutiques.class);
                        SetBoutique(boutiques);
                    } else if (response.body().getStatus() == 0) {
                        Toast.makeText(activity, "rrr", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RSResponse> call, Throwable t) {
                Log.d("err", t.getMessage());
            }
        });
    }

    private void SetBoutique(Boutiques boutiques) {
        if (boutiques != null) {
            Prix.setText(boutiques.getPrix());
            Nom.setText(boutiques.getNomProd());
            Date.setText(boutiques.getDate());
            Location.setText(String.format("%s,%s", boutiques.getGouvernement(), boutiques.getVille()));
            Categorie.setText(boutiques.getCategorie());
            Description.setText(boutiques.getDescription());
            if (!boutiques.getMarque().isEmpty()) {
                Marque.setVisibility(View.VISIBLE);
                iconMarque.setVisibility(View.VISIBLE);
                Marque.setText(boutiques.getMarque());
            }
        }
    }

    public void Logout(View view) {
        startActivity(new Intent(activity, LoginActivity.class));
        finishAffinity();
    }

    public void SupprimerItemBoutique(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle(R.string.supp_prod)
                .setIcon(R.drawable.ic_delete)
                .setMessage("\n" + getString(R.string.question_supp_bout))
                .setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final ProgressDialog loading = ProgressDialog.show(activity, "Traitement Des Données...", "S'il Vous Plaît, Attendez...", false, false);
                        if (Helpers.isConnected(activity)) {
                            Call<RSResponse> callUpload = WebService.getInstance().getApi().DeleteBoutique(ID_Bout);
                            callUpload.enqueue(new Callback<RSResponse>() {
                                @Override
                                public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                                    loading.dismiss();
                                    if (response.body().getStatus() == 1) {
                                        Toast.makeText(activity, getString(R.string.produit_supp), Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(activity, BoutiqueActivity.class));
                                    } else if (response.body().getStatus() == 0) {
                                        Toast.makeText(activity, "rrr", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<RSResponse> call, Throwable t) {
                                    loading.dismiss();
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

    public void Contacter(View view) {
    }
}
