package com.user.myshop;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.user.myshop.Adapter.FavoriteAdapter;
import com.user.myshop.Models.Favorites;
import com.user.myshop.Models.RSResponse;
import com.user.myshop.Utils.Helpers;
import com.user.myshop.Webservice.WebService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    GridView grid;
    List<Favorites> listFavorite = new ArrayList<>();
    SharedPreferences prefs;
    int ID_user;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_favorite);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        grid = findViewById(R.id.grid);
        Helpers.AddMenu(activity, bottomNavigationView);
        prefs = getApplicationContext().getSharedPreferences("UserInfos", MODE_PRIVATE);
        ID_user = prefs.getInt("ID_User", 0);
        if (ID_user != 0) {
            GetFavorites();
        }
    }

    private void GetFavorites() {
        if (Helpers.isConnected(activity)) {
            Call<RSResponse> callUpload = WebService.getInstance().getApi().loadUserFavorie(String.valueOf(ID_user));
            callUpload.enqueue(new Callback<RSResponse>() {
                @Override
                public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                    if (response.body() != null) {
                        if (response.body().getStatus() == 1) {
                            Favorites[] tab = new Gson().fromJson(new Gson().toJson(response.body().getData()), Favorites[].class);
                            listFavorite = Arrays.asList(tab);
                            FavoriteAdapter adapter = new FavoriteAdapter(activity, listFavorite);
                            grid.setAdapter(adapter);
                        } else if (response.body().getStatus() == 2) {
                            Toast.makeText(activity, "Pas de Favorite", Toast.LENGTH_SHORT).show();
                        } else if (response.body().getStatus() == 0) {
                            Toast.makeText(activity, "errrr", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<RSResponse> call, Throwable t) {
                    Log.d("err", t.getMessage());
                }
            });
        }else {
            Helpers.ShowMessageConnection(activity);
        }
    }

    public void Logout(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        finishAffinity();
    }
}
