package com.user.myshop;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.user.myshop.Adapter.CategorieAdapter;
import com.user.myshop.Adapter.GouvernementAdapter;
import com.user.myshop.Adapter.MarqueAdapter;
import com.user.myshop.Models.Categories;
import com.user.myshop.Models.Gouvernements;
import com.user.myshop.Models.Marques;
import com.user.myshop.Models.Produits;
import com.user.myshop.Models.RSResponse;
import com.user.myshop.Utils.Helpers;
import com.user.myshop.Webservice.WebService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBoutiqueActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Activity activity;
    LinearLayout linearMarque;
    TextInputLayout textInputEmail, textInputTel;
    CheckBox chekEmail, chekTel;
    TextInputEditText NomP, Prix, Description, Ville, Email, Tel;
    Spinner Marque, Categorie, Gouvernerat;
    String nomP, prix, description, ville, email, tel, categorie, marque, gouvernement, ID_Prod;
    int ID_user;
    Produits produit;
    SharedPreferences prefs;
    MarqueAdapter marqueAdapter;
    CategorieAdapter categorieAdapter;
    GouvernementAdapter gouvernementAdapter;
    List<Marques> listMarques = new ArrayList<>();
    List<Categories> listCategories = new ArrayList<>();
    List<Gouvernements> listGouvernerat = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_boutique);
        activity = this;
        prefs = getApplicationContext().getSharedPreferences("UserInfos", MODE_PRIVATE);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        NomP = findViewById(R.id.nomProd);
        Prix = findViewById(R.id.prix);
        Description = findViewById(R.id.description);
        Ville = findViewById(R.id.ville);
        Email = findViewById(R.id.email);
        Tel = findViewById(R.id.tel);
        Marque = findViewById(R.id.marque);
        Gouvernerat = findViewById(R.id.gouvernerat);
        Categorie = findViewById(R.id.categorie);
        linearMarque = findViewById(R.id.linearmarque);
        chekEmail = findViewById(R.id.chekemail);
        chekTel = findViewById(R.id.chektel);
        textInputEmail = findViewById(R.id.textInputEmail);
        textInputTel = findViewById(R.id.textInputTel);
        ID_user = prefs.getInt("ID_User", 0);
        Helpers.AddMenu(activity, bottomNavigationView);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ID_Prod = extras.getString("ID_Prod");
            if (Helpers.isConnected(activity)) {
                GetProduit();
                loadAllCategorie();
                loadAllMarque();
                loadAllGouvernement();
            } else {
                Helpers.ShowMessageConnection(activity);
            }
        }
        Marque.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (listMarques.size() > 0) {
                    marque = listMarques.get(i).getNomMarq();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        Categorie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (listCategories.size() > 0) {
                    if (listCategories.get(i).getAvecMarque() == 1) {
                        linearMarque.setVisibility(View.VISIBLE);
                    } else {
                        linearMarque.setVisibility(View.GONE);
                        marque = "";
                    }
                    categorie = listCategories.get(i).getNomCat();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        Gouvernerat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (listGouvernerat.size() > 0) {
                    gouvernement = listGouvernerat.get(i).getNom();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }


    public void AddItemBoutique(View view) {
        nomP = NomP.getText().toString();
        prix = Prix.getText().toString().trim();
        description = Description.getText().toString();
        ville = Ville.getText().toString();
        email = Email.getText().toString().trim();
        tel = Tel.getText().toString().trim();
        if (Helpers.isConnected(activity)) {
            if (Valider()) {
                Toast.makeText(activity, ID_Prod + "", Toast.LENGTH_SHORT).show();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
                final ProgressDialog loading = ProgressDialog.show(activity, "Traitement Des Données...", "S'il Vous Plaît, Attendez...", false, false);
                Calendar calendar = Calendar.getInstance();
                Call<RSResponse> callUpload = WebService.getInstance().getApi().addBoutique(
                        nomP, description, prix, ville, gouvernement, marque, categorie, email, tel, sdf.format(calendar.getTime()),
                        String.valueOf(produit.getListimage().size()), produit.getListimage(), String.valueOf(ID_user), ID_Prod
                );
                callUpload.enqueue(new Callback<RSResponse>() {
                    @Override
                    public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                        loading.dismiss();
                        if (response.body().getStatus() == 1) {
                            startActivity(new Intent(activity, BoutiqueActivity.class));
                            Toast.makeText(getApplicationContext(), getString(R.string.add_prod_succ), Toast.LENGTH_SHORT).show();
                        } else if (response.body().getStatus() == 0) {
                            Toast.makeText(activity, "err", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RSResponse> call, Throwable t) {
                        loading.dismiss();
                        Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            Helpers.ShowMessageConnection(activity);
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
        if (ville.isEmpty()) {
            Ville.setError(getString(R.string.champs_obligatoir));
            valide = false;
        }
        if (chekEmail.isChecked() && email.isEmpty()) {
            Email.setError(getString(R.string.champs_obligatoir));
            valide = false;
        }
        if (chekTel.isChecked() && tel.isEmpty()) {
            Tel.setError(getString(R.string.champs_obligatoir));
            valide = false;
        }
        if (!email.isEmpty() && (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            Email.setError(getString(R.string.email_invalide));
            valide = false;
        }
        if (!chekEmail.isChecked() && !chekTel.isChecked()) {
            Toast.makeText(activity, getString(R.string.chektype_contact), Toast.LENGTH_SHORT).show();
            valide = false;
        }

        return valide;
    }

    public void GetProduit() {
        Call<RSResponse> callUpload = WebService.getInstance().getApi().DetailProduit(ID_Prod);
        callUpload.enqueue(new Callback<RSResponse>() {
            @Override
            public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 1) {
                        produit = new Gson().fromJson(new Gson().toJson(response.body().getData()), Produits.class);
                        SetProduit(produit);
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
        if (produits != null) {
            NomP.setText(produits.getNom());
            Prix.setText(produits.getPrix());
            Description.setText(produits.getDescription());
        }
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
                        marqueAdapter = new MarqueAdapter(activity, listMarques);
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
                        categorieAdapter = new CategorieAdapter(activity, listCategories);
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

    private void loadAllGouvernement() {
        Call<RSResponse> callUpload = WebService.getInstance().getApi().AllGouvernerat();
        callUpload.enqueue(new Callback<RSResponse>() {
            @Override
            public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 1) {
                        Gouvernements[] gouvernements = new Gson().fromJson(new Gson().toJson(response.body().getData()), Gouvernements[].class);
                        listGouvernerat = Arrays.asList(gouvernements);
                        gouvernementAdapter = new GouvernementAdapter(activity, listGouvernerat);
                        Gouvernerat.setAdapter(gouvernementAdapter);
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

    public void ChekTel(View view) {
        if (chekTel.isChecked()) {
            textInputTel.setVisibility(View.VISIBLE);
        } else {
            textInputTel.setVisibility(View.GONE);
            Tel.setText("");
            Tel.setError(null);
        }
    }

    public void ChekEmail(View view) {
        if (chekEmail.isChecked()) {
            textInputEmail.setVisibility(View.VISIBLE);
        } else {
            textInputEmail.setVisibility(View.GONE);
            Email.setText("");
            Email.setError(null);
        }
    }

    public void Logout(View view) {
        startActivity(new Intent(activity, LoginActivity.class));
        finishAffinity();
    }
}
