package com.user.myshop;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;
import com.user.myshop.Models.Boutiques;
import com.user.myshop.Models.Produits;
import com.user.myshop.Models.RSResponse;
import com.user.myshop.Utils.Helpers;
import com.user.myshop.Webservice.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoutiqueDetails extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    String ID_Bout;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;
        setContentView(R.layout.activity_botuique_details);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        Helpers.AddMenu(this,bottomNavigationView);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ID_Bout= extras.getString("ID_Bout");
            GetBoutique();
        }
    }

    private void GetBoutique() {
        Call<RSResponse> callUpload = WebService.getInstance().getApi().DetailBoutique(ID_Bout);
        callUpload.enqueue(new Callback<RSResponse>() {
            @Override
            public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 1) {
                        Boutiques rsResponse = new Gson().fromJson(new Gson().toJson(response.body().getData()), Boutiques.class);
                        SetBoutique(rsResponse);
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
    }
}
