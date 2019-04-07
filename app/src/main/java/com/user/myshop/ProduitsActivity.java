package com.user.myshop;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;
import com.user.myshop.Adapter.ProduitsAdapter;
import com.user.myshop.Models.Produit;
import com.user.myshop.Models.Produits;
import com.user.myshop.Models.RSResponse;
import com.user.myshop.Webservice.WebService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProduitsActivity extends AppCompatActivity {
    GridView grid;
    List<Produits> listProduits = new ArrayList<>();
    BottomNavigationView bottomNavigationView;
    SharedPreferences prefs;
    int ID_user;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produits);
        activity = this;
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        grid = findViewById(R.id.grid);
        prefs = getApplicationContext().getSharedPreferences("Users", MODE_PRIVATE);
        ID_user = prefs.getInt("ID_User", 0);
        if (ID_user != 0) {
            Call<RSResponse> callUpload = WebService.getInstance().getApi().loadUserProduit(String.valueOf(1));
            callUpload.enqueue(new Callback<RSResponse>() {
                @Override
                public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                    if (response.body() != null) {
                        if (response.body().getStatus() == 1) {
                            Produits[] tab = new Gson().fromJson(new Gson().toJson(response.body().getData()), Produits[].class);
                            listProduits = Arrays.asList(tab);
                            Log.d("tt", listProduits.get(0).getListimage().size() + "");
                            ProduitsAdapter adapter = new ProduitsAdapter(activity, listProduits);
                            grid.setAdapter(adapter);
                        } else if (response.body().getStatus() == 2) {
                            Toast.makeText(ProduitsActivity.this, "Pas de produit", Toast.LENGTH_SHORT).show();
                        } else if (response.body().getStatus() == 0) {
                            Toast.makeText(ProduitsActivity.this, "errrr", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<RSResponse> call, Throwable t) {
                    Log.d("err", t.getMessage());
                }
            });
        }
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ProduitDetails.class);
                startActivity(intent);
            }
        });
        AddMenu();
    }

    public void AddProd(View view) {
        startActivity(new Intent(this, AjoutProduitActivity.class));
    }

    private void AddMenu() {
        BottomNavigationItem bottomNavigationItem = new BottomNavigationItem
                (getString(R.string.profil), ContextCompat.getColor(this, R.color.colorPrimary), R.drawable.ic_profile);
        BottomNavigationItem bottomNavigationItem1 = new BottomNavigationItem
                (getString(R.string.produits), ContextCompat.getColor(this, R.color.colorAccent), R.drawable.ic_allproduit);
        BottomNavigationItem bottomNavigationItem2 = new BottomNavigationItem
                (getString(R.string.favori), ContextCompat.getColor(this, R.color.colorAccent), R.drawable.ic_favorite);
        BottomNavigationItem bottomNavigationItem3 = new BottomNavigationItem
                (getString(R.string.ajouter_botique), ContextCompat.getColor(this, R.color.colorAccent), R.drawable.ic_addproduit);
        bottomNavigationView.addTab(bottomNavigationItem);
        bottomNavigationView.addTab(bottomNavigationItem1);
        bottomNavigationView.addTab(bottomNavigationItem2);
        bottomNavigationView.addTab(bottomNavigationItem3);
        bottomNavigationView.setOnBottomNavigationItemClickListener(new OnBottomNavigationItemClickListener() {
            @Override
            public void onNavigationItemClick(int index) {
                Toast.makeText(getApplicationContext(), "Item " + index + " clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
