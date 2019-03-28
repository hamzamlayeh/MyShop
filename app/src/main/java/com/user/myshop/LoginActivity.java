package com.user.myshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void inscrire(View view) {
        startActivity(new Intent(this,Inscription.class));
    }

    public void Valider(View view) {
        startActivity(new Intent(this,Produits.class));
    }
}
