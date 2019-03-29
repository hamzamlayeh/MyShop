package com.user.myshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.user.myshop.Adapter.ProduitsAdapter;
import com.user.myshop.Models.Produit;

import java.util.ArrayList;

public class Produits extends AppCompatActivity {
    GridView grid;
    ArrayList<Produit> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produits);
        grid=findViewById(R.id.grid);
        list.add(new Produit("khale","hizawi","http://13.80.41.22/stillvalid/StillValid/web/bundles/contrats/970665416.jpg","2015"));
        list.add(new Produit("khale","hizawi","http://13.80.41.22/stillvalid/StillValid/web/bundles/contrats/970665416.jpg","2015"));
        list.add(new Produit("khale","hizawi","http://13.80.41.22/stillvalid/StillValid/web/bundles/contrats/970665416.jpg","2015"));
        list.add(new Produit("khale","hizawi","http://13.80.41.22/stillvalid/StillValid/web/bundles/contrats/970665416.jpg","2015"));
        list.add(new Produit("khale","hizawi","http://13.80.41.22/stillvalid/StillValid/web/bundles/contrats/970665416.jpg","2015"));
        list.add(new Produit("khale","hizawi","http://13.80.41.22/stillvalid/StillValid/web/bundles/contrats/970665416.jpg","251"));
        list.add(new Produit("khale","hizawi","http://13.80.41.22/stillvalid/StillValid/web/bundles/contrats/970665416.jpg","57"));

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

    }
}
