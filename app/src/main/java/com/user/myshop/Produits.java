package com.user.myshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.user.myshop.Asapter.ProduitsAdapter;
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

    }
}
