package com.user.myshop;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.user.myshop.Models.ConfigUrls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Inscription extends AppCompatActivity {
    EditText Email, Password, Nom, Prenom, Tel, Adress;
    String mail, password, nom, prenom, tel, adress;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        context = this;
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        Nom = findViewById(R.id.nom);
        Prenom = findViewById(R.id.prenom);
        Tel = findViewById(R.id.tel);
        Adress = findViewById(R.id.adress);
    }

    public void Authentifier(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void Valider(View view) {
        mail = Email.getText().toString().trim();
        password = Password.getText().toString().trim();
        nom = Nom.getText().toString().trim();
        prenom = Prenom.getText().toString().trim();
        adress = Adress.getText().toString().trim();
        tel = Tel.getText().toString().trim();

        if (Valider()) {
            if (isOnline()) {
                final ProgressDialog loading = ProgressDialog.show(context, "Traitement Des Données...", "S'il Vous Plaît, Attendez...", false, false);

                StringRequest request = new StringRequest(Request.Method.POST, ConfigUrls.ADD_USERS, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        switch (response) {
                            case "1":
                                Email.setError(getString(R.string.MailNexistePas));
                                //Toast.makeText(getApplicationContext(), getString(R.string.MailNexistePas), Toast.LENGTH_SHORT).show();
                                break;
                            case "2":
                                Toast.makeText(getApplicationContext(), getString(R.string.insert_eronee), Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                break;
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", "test");
                        loading.dismiss();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hmap = new HashMap<String, String>();
                        hmap.put("email", mail);
                        hmap.put("password", password);
                        hmap.put("nom", nom);
                        hmap.put("prenom", prenom);
                        hmap.put("tel", tel);
                        hmap.put("adress", adress);
                        return hmap;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(this);
                queue.add(request);
            } else {
                Toast.makeText(this, R.string.chek_internet, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean Valider() {
        boolean valide = true;
        if (mail.isEmpty()) {
            Email.setError(getString(R.string.champs_obligatoir));
            valide = false;
        }
        if (!mail.isEmpty() && (!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches())) {
            Email.setError(getString(R.string.email_invalide));
            valide = false;
        }
        if (password.isEmpty()) {
            Password.setError(getString(R.string.champs_obligatoir));
            valide = false;
        }
        if (nom.isEmpty()) {
            Nom.setError(getString(R.string.champs_obligatoir));
            valide = false;
        }
        if (prenom.isEmpty()) {
            Prenom.setError(getString(R.string.champs_obligatoir));
            valide = false;
        }
        if (tel.isEmpty()) {
            Tel.setError(getString(R.string.champs_obligatoir));
            valide = false;
        }
        if (adress.isEmpty()) {
            Adress.setError(getString(R.string.champs_obligatoir));
            valide = false;
        }
        return valide;
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
