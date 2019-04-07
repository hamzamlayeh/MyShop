package com.user.myshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;
import com.user.myshop.Models.ConfigUrls;
import com.user.myshop.Models.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Users users;
    TextView Nom, Email, Adress, Tel, NB_favoris, NB_produit;
    ImageView Img_profil;
    SharedPreferences prefs;
    int ID_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        Nom = findViewById(R.id.nom);
        Email = findViewById(R.id.email);
        Adress = findViewById(R.id.adress);
        Tel = findViewById(R.id.tel);
        Img_profil = findViewById(R.id.img_profil);
        NB_favoris = findViewById(R.id.nb_favoris);
        NB_produit = findViewById(R.id.nb_produit);

        prefs = getApplicationContext().getSharedPreferences("Users", MODE_PRIVATE);
        ID_user = prefs.getInt("ID_User", 0);
        if (ID_user != 0) {
            LoadUserInfo(ID_user);
        }
        AddMenu();
    }

    private void LoadUserInfo(int id_user) {
        final ProgressDialog loading = ProgressDialog.show(this, "Traitement Des Données...", "S'il Vous Plaît, Attendez...", false, false);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, ConfigUrls.UsersById + id_user, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();
                try {
                    if (response.getInt("Status") != 0) {
                        //Convert Tableaux to json
                        JSONArray TabUser = response.getJSONArray("User");
                        JSONObject UserInfos = TabUser.getJSONObject(0);
                        Nom.setText(String.format("%s %s", UserInfos.getString("nom"), UserInfos.getString("prenom")));
                        Email.setText(UserInfos.getString("email"));
                        Adress.setText(UserInfos.getString("adress"));
                        Tel.setText(UserInfos.getString("tel"));

                        NB_favoris.setText(response.getString("NB_fav"));
                        NB_produit.setText(response.getString("NB_Prod"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
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
                switch (index){
                    case 0:
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getApplicationContext(), ProduitsActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getApplicationContext(),FavoriteActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getApplicationContext(), BoutiqueActivity.class));
                        break;
                }
               // Toast.makeText(getApplicationContext(), "Item " + index + " clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
