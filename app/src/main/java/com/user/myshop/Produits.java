package com.user.myshop;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;
import com.user.myshop.Adapter.ProduitsAdapter;
import com.user.myshop.Models.Produit;

import java.util.ArrayList;

public class Produits extends AppCompatActivity {
    GridView grid;
    ArrayList<Produit> list = new ArrayList<>();
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produits);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);

        grid = findViewById(R.id.grid);
        list.add(new Produit("rahma", "belhaj", "http://13.80.41.22/stillvalid/StillValid/web/bundles/contrats/970665416.jpg", "2015"));
        list.add(new Produit("rahma", "belhaj", "http://13.80.41.22/stillvalid/StillValid/web/bundles/contrats/970665416.jpg", "2015"));
        list.add(new Produit("rahma", "belhaj", "http://13.80.41.22/stillvalid/StillValid/web/bundles/contrats/970665416.jpg", "2015"));
        list.add(new Produit("rahma", "belhaj", "http://13.80.41.22/stillvalid/StillValid/web/bundles/contrats/970665416.jpg", "2015"));
        list.add(new Produit("khouloud", "aloui", "http://13.80.41.22/stillvalid/StillValid/web/bundles/contrats/970665416.jpg", "2015"));
        list.add(new Produit("khouloud", "aloui", "http://13.80.41.22/stillvalid/StillValid/web/bundles/contrats/970665416.jpg", "251"));
        list.add(new Produit("khouloud", "aloui", "http://13.80.41.22/stillvalid/StillValid/web/bundles/contrats/970665416.jpg", "57"));

        ProduitsAdapter adapter = new ProduitsAdapter(this, list);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                // Sending image id to FullScreenActivity
                Intent i = new Intent(getApplicationContext(), ProduitDetails.class);
                // passing array index
                i.putExtra("id", position);
                startActivity(i);
            }
        });
        AddMenu();
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
