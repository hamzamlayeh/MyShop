package com.user.myshop;

import android.app.Activity;
import android.content.Intent;
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
import com.user.myshop.Utils.Helpers;
import com.user.myshop.Webservice.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProduitDetails extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TextView NomProd, Prix, Description, Date;
    String ID_Prod;
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
                        Produits rsResponse = new Gson().fromJson(new Gson().toJson(response.body().getData()), Produits.class);
                        SetProduit(rsResponse);
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
        NomProd.setText(produits.getNom());
        Prix.setText(produits.getPrix());
        Description.setText(produits.getDescription());
        Date.setText(produits.getDate());
    }

    public void LoadPhoto(View view) {
    }

    public void AddBoutique(View view) {
        if (ID_Prod != null) {
            Intent intent = new Intent(activity, AddBoutiqueActivity.class);
            intent.putExtra("ID_Prod", ID_Prod);
            startActivity(intent);
        }
    }

}
