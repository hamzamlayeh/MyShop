package com.user.myshop;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.user.myshop.Adapter.BotuiqueAdapter;
import com.user.myshop.Models.Boutiques;
import com.user.myshop.Models.RSResponse;
import com.user.myshop.Utils.Helpers;
import com.user.myshop.Webservice.WebService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoutiqueActivity extends AppCompatActivity {
    GridView grid;
    List<Boutiques> listBoutique = new ArrayList<>();
    BottomNavigationView bottomNavigationView;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boutique);
        activity = this;
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        grid = findViewById(R.id.grid);
        Helpers.AddMenu(activity, bottomNavigationView);
        GetAllBoutique();

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txtId = view.findViewById(R.id.Id);
                Intent intent = new Intent(activity, BoutiqueDetails.class);
                intent.putExtra("ID_Bout", txtId.getText().toString());
                startActivity(intent);
            }
        });

    }

    private void GetAllBoutique() {
        Call<RSResponse> callUpload = WebService.getInstance().getApi().loadBoutique();
        callUpload.enqueue(new Callback<RSResponse>() {
            @Override
            public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 1) {
                        Boutiques[] tab = new Gson().fromJson(new Gson().toJson(response.body().getData()), Boutiques[].class);
                        listBoutique = Arrays.asList(tab);
                        BotuiqueAdapter adapter = new BotuiqueAdapter(activity, listBoutique);
                        grid.setAdapter(adapter);
                    } else if (response.body().getStatus() == 0) {
                        Toast.makeText(getApplicationContext(), "Pas de Produit dans le boutique", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RSResponse> call, Throwable t) {
                Log.d("err", t.getMessage());
            }
        });
    }
}
