package com.user.myshop;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;
import com.user.myshop.Adapter.FavoriteAdapter;
import com.user.myshop.Models.Boutique;
import com.user.myshop.Models.Favorite;
import com.user.myshop.Models.Produit;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    GridView grid;
    ArrayList<Boutique> list_B = new ArrayList<>();
    ArrayList<Produit> list = new ArrayList<>();
    ArrayList<Favorite> list_F = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        AddMenu();
        grid = findViewById(R.id.grid);

        list.add(new Produit("rahma", "belhaj", "http://13.80.41.22/stillvalid/StillValid/web/bundles/contrats/970665416.jpg", "2015"));

        list_B.add(new Boutique(list.get(0)));


        list_F.add(new Favorite(list_B.get(0)));
        list_F.add(new Favorite(list_B.get(0)));
        list_F.add(new Favorite(list_B.get(0)));
        list_F.add(new Favorite(list_B.get(0)));
        list_F.add(new Favorite(list_B.get(0)));
        FavoriteAdapter adapter = new FavoriteAdapter(this,list_F );
        grid.setAdapter(adapter);
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
