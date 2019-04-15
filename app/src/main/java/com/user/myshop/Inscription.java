package com.user.myshop;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.user.myshop.Models.RSResponse;
import com.user.myshop.Models.UserInfos;
import com.user.myshop.Utils.Helpers;
import com.user.myshop.Webservice.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Inscription extends AppCompatActivity {
    EditText Email, Password, Nom, Prenom, Tel, Adress;
    String mail, password, nom, prenom, tel, adress;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        activity = this;
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        Nom = findViewById(R.id.nom);
        Prenom = findViewById(R.id.prenom);
        Tel = findViewById(R.id.tel);
        Adress = findViewById(R.id.adress);
    }

    public void Authentifier(View view) {
        startActivity(new Intent(activity, LoginActivity.class));
    }

    public void Valider(View view) {
        mail = Email.getText().toString().trim();
        password = Password.getText().toString().trim();
        nom = Nom.getText().toString().trim();
        prenom = Prenom.getText().toString().trim();
        adress = Adress.getText().toString().trim();
        tel = Tel.getText().toString().trim();

        if (Valider()) {
            if (Helpers.isConnected(activity)) {
                final ProgressDialog loading = ProgressDialog.show(activity, "Traitement Des Données...", "S'il Vous Plaît, Attendez...", false, false);
                UserInfos userInfos = new UserInfos(nom, prenom, tel, mail, password, adress);
                Call<RSResponse> callUpload = WebService.getInstance().getApi().inscrireUser(userInfos);
                callUpload.enqueue(new Callback<RSResponse>() {
                    @Override
                    public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                        loading.dismiss();
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                startActivity(new Intent(activity, LoginActivity.class));
                            } else if (response.body().getStatus() == 0) {
                                Toast.makeText(activity, getString(R.string.insert_eronee), Toast.LENGTH_SHORT).show();
                            } else if (response.body().getStatus() == 2) {
                                Email.setError(getString(R.string.MailNexistePas));
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
}
