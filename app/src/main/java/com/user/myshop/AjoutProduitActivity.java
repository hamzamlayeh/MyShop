package com.user.myshop;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;

public class AjoutProduitActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    EditText NomProd, Description, Prix;
    String nomProd, description, prix;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_produit);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        NomProd = findViewById(R.id.NomProduit);
        Description = findViewById(R.id.Desc);
        Prix = findViewById(R.id.Prix);
        AddMenu();
    }

    public void Appareilphoto(View view) {
    }

    public void Gallery(View view) {
    }

    public void Valider(View view) {
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
                switch (index) {
                    case 0:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getApplicationContext(), Produits.class));
                        break;
                    case 2:
                        startActivity(new Intent(getApplicationContext(), FavoriteActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getApplicationContext(), BoutiqueActivity.class));
                        break;
                }
            }
        });
    }
}
