package com.user.myshop;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.user.myshop.Models.RSResponse;
import com.user.myshop.Models.User;
import com.user.myshop.Models.UserInfos;
import com.user.myshop.Utils.Helpers;
import com.user.myshop.Webservice.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText Email, Password;
    String mail, password;
    Activity activity;
    SharedPreferences pref;
    SharedPreferences.Editor editors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       //startActivity(new Intent(this, ProfileActivity.class));
        activity = this;
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        pref = getApplicationContext().getSharedPreferences("UserInfos", MODE_PRIVATE);

    }

    public void inscrire(View view) {
        startActivity(new Intent(activity, Inscription.class));
    }

    public void Valider(View view) {
        mail = Email.getText().toString().trim();
        password = Password.getText().toString().trim();
        if (Valider()) {
            if (Helpers.isConnected(activity)) {
                final ProgressDialog loading = ProgressDialog.show(activity, "Traitement Des Données...", "S'il Vous Plaît, Attendez...", false, false);
                User user = new User(mail, password);
                Call<RSResponse> callUpload = WebService.getInstance().getApi().loginUser(user);
                callUpload.enqueue(new Callback<RSResponse>() {
                    @Override
                    public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                        loading.dismiss();
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                UserInfos user = new Gson().fromJson(new Gson().toJson(response.body().getData()), UserInfos.class);
                                editors = pref.edit();
                                editors.putString("Email", user.getEmail());
                                editors.putInt("ID_User", user.getId_user());
                                editors.apply();
                                startActivity(new Intent(activity, HomeActivity.class));
                            } else if (response.body().getStatus() == 0) {
                                Toast.makeText(activity, "err", Toast.LENGTH_SHORT).show();
                            } else if (response.body().getStatus() == 2) {
                                Toast.makeText(activity, getString(R.string.EmailOuMotDePasseInvalide), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RSResponse> call, Throwable t) {
                        loading.dismiss();
                        Log.i("err", t.getMessage());
                    }
                });
            } else {
                Helpers.ShowMessageConnection(activity);
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
