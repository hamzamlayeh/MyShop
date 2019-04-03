package com.user.myshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;



public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void Profie(View view) {
        startActivity(new Intent(this, ProfileActivity.class));
    }

    public void Favorite(View view) {
        startActivity(new Intent(this, FavoriteActivity.class));
    }

    public void MesProduit(View view) {
        startActivity(new Intent(this, Produits.class));
    }

    public void Boutique(View view) {
        startActivity(new Intent(this, BoutiqueActivity.class));
    }
}
