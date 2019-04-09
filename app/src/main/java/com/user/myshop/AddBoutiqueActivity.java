package com.user.myshop;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.user.myshop.Adapter.CategorieAdapter;
import com.user.myshop.Adapter.MarqueAdapter;
import com.user.myshop.Models.Categories;
import com.user.myshop.Models.Marques;
import com.user.myshop.Models.Produits;
import com.user.myshop.Models.RSResponse;
import com.user.myshop.Utils.Helpers;
import com.user.myshop.Webservice.WebService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBoutiqueActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Activity activity;
    TextView NomP, Prix, Description, Ville, Email, Tel;
    Spinner Marque, Categorie;
    String nomP, prix, description, ville, email, tel, categorie, marque, ID_Prod;
    MarqueAdapter marqueAdapter;
    CategorieAdapter categorieAdapter;
    ArrayAdapter<Marques> Adapter;
    List<Marques> listMarques = new ArrayList<>();
    List<Categories> listCategories = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_boutique);
        activity = this;
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        NomP = findViewById(R.id.nomProd);
        Prix = findViewById(R.id.prix);
        Description = findViewById(R.id.description);
        Ville = findViewById(R.id.ville);
        Email = findViewById(R.id.email);
        Tel = findViewById(R.id.tel);
        Marque = findViewById(R.id.marque);
        Categorie = findViewById(R.id.categorie);
        Helpers.AddMenu(activity, bottomNavigationView);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            ID_Prod = extras.getString("ID_Prod");
            if (Helpers.isConnected(activity)) {
                GetProduit();
                loadAllCategorie();
                loadAllMarque();
            } else {
                Helpers.ShowMessageConnection(activity);
            }
        }
    }

    public void AddItemBoutique(View view) {
        nomP = NomP.getText().toString().trim();
        prix = Prix.getText().toString().trim();
        description = Description.getText().toString().trim();
        ville = Ville.getText().toString().trim();
        if (Valider()) {
            if (Helpers.isConnected(activity)) {

            } else {
                Helpers.ShowMessageConnection(activity);
            }
        }
    }

    private boolean Valider() {
        boolean valide = true;
        if (nomP.isEmpty()) {
            NomP.setError(getString(R.string.champs_obligatoir));
            valide = false;
        }
        if (description.isEmpty()) {
            Description.setError(getString(R.string.champs_obligatoir));
            valide = false;
        }
        if (prix.isEmpty()) {
            Prix.setError(getString(R.string.champs_obligatoir));
            valide = false;
        }
//        if (listPics.size() <= 0) {
//            Toast.makeText(this, getString(R.string.chose_img), Toast.LENGTH_SHORT).show();
//            valide = false;
//        }
        return valide;
    }

    public void GetProduit() {
        Call<RSResponse> callUpload = WebService.getInstance().getApi().DetailProduit(ID_Prod);
        callUpload.enqueue(new Callback<RSResponse>() {
            @Override
            public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 1) {
                        Produits rsResponse = new Gson().fromJson(new Gson().toJson(response.body().getData()), Produits.class);
                        SetProduit(rsResponse);
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

    private void SetProduit(Produits produits) {
        NomP.setText(produits.getNom());
        Prix.setText(produits.getPrix());
        Description.setText(produits.getDescription());
    }

    private void loadAllMarque() {
        Call<RSResponse> callUpload = WebService.getInstance().getApi().AllMarques();
        callUpload.enqueue(new Callback<RSResponse>() {
            @Override
            public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 1) {
                        Marques[] marques = new Gson().fromJson(new Gson().toJson(response.body().getData()), Marques[].class);
                        listMarques = Arrays.asList(marques);
                        marqueAdapter=new MarqueAdapter(activity,listMarques);
                        Marque.setAdapter(marqueAdapter);
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

    private void loadAllCategorie() {
        Call<RSResponse> callUpload = WebService.getInstance().getApi().AllCategories();
        callUpload.enqueue(new Callback<RSResponse>() {
            @Override
            public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 1) {
                        Categories[] categories = new Gson().fromJson(new Gson().toJson(response.body().getData()), Categories[].class);
                        listCategories = Arrays.asList(categories);
                        categorieAdapter=new CategorieAdapter(activity,listCategories);
                        Categorie.setAdapter(categorieAdapter);
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
}
