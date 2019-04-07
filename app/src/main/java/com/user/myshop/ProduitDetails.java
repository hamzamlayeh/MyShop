package com.user.myshop;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;
import com.user.myshop.Models.Produits;
import com.user.myshop.Models.RSResponse;
import com.user.myshop.Webservice.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProduitDetails extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TextView NomProd, Prix, Description, Date;
    int Position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produit_details);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        NomProd = findViewById(R.id.NomP);
        Prix = findViewById(R.id.Prix);
        Description = findViewById(R.id.Description);
        Date = findViewById(R.id.Date);

        AddMenu();
        Call<RSResponse> callUpload = WebService.getInstance().getApi().loadProduit(String.valueOf(14));
        callUpload.enqueue(new Callback<RSResponse>() {
            @Override
            public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 1) {
                        Produits rsResponse = new Gson().fromJson(new Gson().toJson(response.body().getData()), Produits.class);
                        LoadPoduit(rsResponse);
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

    public void LoadPoduit(Produits produits) {
        NomProd.setText(produits.getNom());
        Prix.setText(produits.getPrix());
        Description.setText(produits.getDescription());
        Date.setText(produits.getDate());
    }

    public void LoadPhoto(View view) {
    }

    public void AddBoutique(View view) {
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
