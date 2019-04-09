package com.user.myshop;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.user.myshop.Models.ConfigUrls;
import com.user.myshop.Utils.Helpers;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText Email, Password;
    String mail, password;
    Context context;
    SharedPreferences pref;
    SharedPreferences.Editor editors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //startActivity(new Intent(this, AddBoutiqueActivity.class));
        context = this;
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        pref = getApplicationContext().getSharedPreferences("Users", MODE_PRIVATE);

    }

    public void inscrire(View view) {
        startActivity(new Intent(this, Inscription.class));
    }

    public void Valider(View view) {
        mail = Email.getText().toString().trim();
        password = Password.getText().toString().trim();
        if (Valider()) {
            if (Helpers.isConnected(this)) {
                final ProgressDialog loading = ProgressDialog.show(context, "Traitement Des Données...", "S'il Vous Plaît, Attendez...", false, false);
                StringRequest request = new StringRequest(Request.Method.POST, ConfigUrls.LOGIN, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        switch (response) {
                            case "0":
                                Toast.makeText(getApplicationContext(), getString(R.string.EmailOuMotDePasseInvalide), Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    editors = pref.edit();
                                    editors.putString("Email", mail);
                                    editors.putInt("ID_User", obj.getInt("id_user"));
                                    editors.apply();
                                    Toast.makeText(getApplicationContext(), obj.length() + "//" + obj.getInt("id_user"), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
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
        return valide;
    }
}
