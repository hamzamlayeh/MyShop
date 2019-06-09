package com.user.myshop;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.user.myshop.Adapter.ImagesBoutiqueAdapter;
import com.user.myshop.Models.Boutiques;
import com.user.myshop.Models.RSResponse;
import com.user.myshop.Utils.Helpers;
import com.user.myshop.Webservice.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoutiqueDetails extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TextView Prix, Nom, Date, Location, Categorie, Marque, Description, Contact_tel, Contact_email;
    ImageView iconMarque, iconContact_email, iconContact_tel, unfollowed, followed,btn_supp;
    RecyclerView recyclerView;
    Boutiques boutiques;
    Activity activity;
    SharedPreferences prefs;
    String ID_Bout;
    int ID_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_botuique_details);
        activity = this;
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        Prix = findViewById(R.id.Prix);
        Nom = findViewById(R.id.nom);
        Date = findViewById(R.id.date);
        Location = findViewById(R.id.location);
        Categorie = findViewById(R.id.categorie);
        Marque = findViewById(R.id.Marque);
        Contact_tel = findViewById(R.id.contact_tel);
        Contact_email = findViewById(R.id.contact_email);
        Description = findViewById(R.id.description);
        iconMarque = findViewById(R.id.imageView40);
        iconContact_email = findViewById(R.id.imageView42);
        iconContact_tel = findViewById(R.id.imageView41);
        followed = findViewById(R.id.followed);
        unfollowed = findViewById(R.id.unfollowed);
        btn_supp = findViewById(R.id.imageView6);
        recyclerView = findViewById(R.id.grid_IMG);

        Helpers.AddMenu(activity, bottomNavigationView);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            prefs = getApplicationContext().getSharedPreferences("UserInfos", MODE_PRIVATE);
            ID_user = prefs.getInt("ID_User", 0);
            ID_Bout = extras.getString("ID_Bout");
            ISfavore(String.valueOf(ID_user), Integer.parseInt(ID_Bout));
            if (Helpers.isConnected(activity))
                GetBoutique();
            else
                Helpers.ShowMessageConnection(activity);
        }

        followed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (Helpers.isConnected(activity)) {
                    followed(String.valueOf(ID_user), Integer.parseInt(ID_Bout));
                    followed.setVisibility(View.GONE);
                    unfollowed.setVisibility(View.VISIBLE);
                } else {
                    Helpers.ShowMessageConnection(activity);
                }

            }
        });

        unfollowed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (Helpers.isConnected(activity)) {
                    unfollowed(Integer.parseInt(ID_Bout));
                    unfollowed.setVisibility(View.GONE);
                    followed.setVisibility(View.VISIBLE);
                } else {
                    Helpers.ShowMessageConnection(activity);
                }
            }
        });
    }

    private void GetBoutique() {
        Call<RSResponse> callUpload = WebService.getInstance().getApi().DetailBoutique(ID_Bout);
        callUpload.enqueue(new Callback<RSResponse>() {
            @Override
            public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 1) {
                        boutiques = new Gson().fromJson(new Gson().toJson(response.body().getData()), Boutiques.class);
                        SetBoutique(boutiques);
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

    private void SetBoutique(Boutiques boutiques) {
        if (boutiques != null) {
            Prix.setText(boutiques.getPrix());
            Nom.setText(boutiques.getNomProd());
            Date.setText(boutiques.getDate());
            Location.setText(String.format("%s,%s", boutiques.getGouvernement(), boutiques.getVille()));
            Categorie.setText(boutiques.getCategorie());
            Description.setText(boutiques.getDescription());
            if (!boutiques.getMarque().isEmpty()) {
                Marque.setVisibility(View.VISIBLE);
                iconMarque.setVisibility(View.VISIBLE);
                Marque.setText(boutiques.getMarque());
            }
            if (!boutiques.getEmail().isEmpty()) {
                Contact_email.setVisibility(View.VISIBLE);
                iconContact_email.setVisibility(View.VISIBLE);
                Contact_email.setText(boutiques.getEmail());
            }
            if (!boutiques.getTel().isEmpty()) {
                Contact_tel.setVisibility(View.VISIBLE);
                iconContact_tel.setVisibility(View.VISIBLE);
                Contact_tel.setText(boutiques.getTel());
            }
            if (boutiques.getIdUser().equals(String.valueOf(ID_user))){
                btn_supp.setVisibility(View.VISIBLE);
            }
        }
    }

    public void Logout(View view) {
        startActivity(new Intent(activity, LoginActivity.class));
        finishAffinity();
    }

    public void SupprimerItemBoutique(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle(R.string.supp_prod)
                .setIcon(R.drawable.ic_delete)
                .setMessage("\n" + getString(R.string.question_supp_bout))
                .setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final ProgressDialog loading = ProgressDialog.show(activity, "Traitement Des Données...", "S'il Vous Plaît, Attendez...", false, false);
                        if (Helpers.isConnected(activity)) {
                            Call<RSResponse> callUpload = WebService.getInstance().getApi().DeleteBoutique(ID_Bout);
                            callUpload.enqueue(new Callback<RSResponse>() {
                                @Override
                                public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                                    loading.dismiss();
                                    if (response.body().getStatus() == 1) {
                                        Toast.makeText(activity, getString(R.string.produit_supp), Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(activity, BoutiqueActivity.class));
                                    } else if (response.body().getStatus() == 0) {
                                        Toast.makeText(activity, "rrr", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<RSResponse> call, Throwable t) {
                                    loading.dismiss();
                                    Log.d("err", t.getMessage());
                                }
                            });
                        } else {
                            Helpers.ShowMessageConnection(activity);
                        }
                    }
                }).setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).show();
    }

    private void ISfavore(String idUser, int id_bout) {
        Call<RSResponse> callUpload = WebService.getInstance().getApi().isFavoree(String.valueOf(id_bout), idUser);
        callUpload.enqueue(new Callback<RSResponse>() {
            @Override
            public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                if (response.body().getStatus() == 1) {
                    unfollowed.setVisibility(View.VISIBLE);
                    followed.setVisibility(View.GONE);
                } else if (response.body().getStatus() == 2) {
                    followed.setVisibility(View.VISIBLE);
                    unfollowed.setVisibility(View.GONE);
                } else if (response.body().getStatus() == 0) {
                    Toast.makeText(activity, "rrr", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RSResponse> call, Throwable t) {
                Log.d("err", t.getMessage());
            }
        });
    }

    private void followed(String idUser, int id_bout) {
        Call<RSResponse> callUpload = WebService.getInstance().getApi().AddFavorite(String.valueOf(id_bout), idUser);
        callUpload.enqueue(new Callback<RSResponse>() {
            @Override
            public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                if (response.body().getStatus() == 1) {
                    Toast.makeText(activity, activity.getString(R.string.suivi), Toast.LENGTH_SHORT).show();
                } else if (response.body().getStatus() == 0) {
                    Toast.makeText(activity, "rrr", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RSResponse> call, Throwable t) {
                Log.d("err", t.getMessage());
            }
        });
    }

    private void unfollowed(int id_bout) {
        if (Helpers.isConnected(activity)) {
            Call<RSResponse> callUpload = WebService.getInstance().getApi().DeleteFavorite(String.valueOf(id_bout));
            callUpload.enqueue(new Callback<RSResponse>() {
                @Override
                public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(activity, activity.getString(R.string.no_suvi), Toast.LENGTH_SHORT).show();
                    } else if (response.body().getStatus() == 0) {
                        Toast.makeText(activity, "rrr", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RSResponse> call, Throwable t) {
                    Log.d("err", t.getMessage());
                }
            });
        } else {
            Helpers.ShowMessageConnection(activity);
        }
    }

    public void Contacter(View view) {
        if (!Contact_tel.getText().toString().isEmpty()) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + Contact_tel.getText().toString()));
            startActivity(callIntent);
        } else {
            Toast.makeText(activity, "Pas de numero de telephone", Toast.LENGTH_SHORT).show();
        }
    }

    public void LoadPhotoBoutique(View view) {
        if (boutiques != null) {
            recyclerView.setVisibility(View.VISIBLE);
            ImagesBoutiqueAdapter imagesBoutiqueAdapter = new ImagesBoutiqueAdapter(boutiques.getListimage(), activity);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                    LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setAdapter(imagesBoutiqueAdapter);
        }
    }
}
