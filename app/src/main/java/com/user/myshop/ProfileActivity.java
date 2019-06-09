package com.user.myshop;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.google.gson.Gson;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;
import com.user.myshop.Models.RSResponse;
import com.user.myshop.Models.UserInfos;
import com.user.myshop.Utils.Constants;
import com.user.myshop.Utils.FileCompressor;
import com.user.myshop.Utils.FileUtils;
import com.user.myshop.Utils.Helpers;
import com.user.myshop.Webservice.WebService;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TextView Nom, Email, Adress, Tel, NB_favoris, NB_produit;
    SimpleDraweeView Img_profil,Img_ProfileModifier;
    ImageView Menu;
    private FileCompressor mCompressor;
    private File mPhotoFile;
    Uri imageUri = null;
    SharedPreferences prefs;
    int ID_user;
    Activity activity;
    View popupInputDialogView = null;
    EditText passwordEditText, adresseEditText, nomEditText, prenomEditText, telEditText;
    String password, adresse, nom, prenom, tel;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        activity = this;
        mCompressor = new FileCompressor(activity);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        Nom = findViewById(R.id.nom);
        Email = findViewById(R.id.email);
        Adress = findViewById(R.id.adress);
        Tel = findViewById(R.id.tel);
        Img_profil = findViewById(R.id.img_profil);
        NB_favoris = findViewById(R.id.nb_favoris);
        NB_produit = findViewById(R.id.nb_produit);
        Menu = findViewById(R.id.menu);

        prefs = getApplicationContext().getSharedPreferences("UserInfos", MODE_PRIVATE);
        ID_user = prefs.getInt("ID_User", 0);
        if (ID_user != 0) {
            LoadUserInfo(ID_user, Constants.PROFIL);
        }
        Helpers.AddMenu(activity, bottomNavigationView);
    }

    private void LoadUserInfo(int id_user, final String TAG) {
        if (Helpers.isConnected(activity)) {
            Call<RSResponse> callUpload = WebService.getInstance().getApi().loadUser(String.valueOf(id_user));
            callUpload.enqueue(new Callback<RSResponse>() {
                @Override
                public void onResponse(Call<RSResponse> call, retrofit2.Response<RSResponse> response) {
                    if (response.body().getStatus() == 1) {
                        UserInfos userInfos = new Gson().fromJson(new Gson().toJson(response.body().getData()), UserInfos.class);
                        setUserInfos(userInfos, TAG);
                    } else if (response.body().getStatus() == 0) {
                        Toast.makeText(activity, "errrr", Toast.LENGTH_SHORT).show();
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

    private void setUserInfos(UserInfos userInfos, String TAG) {
        if (userInfos != null) {
            switch (TAG) {
                case Constants.PROFIL:
                    Nom.setText(String.format("%s %s", userInfos.getNom(), userInfos.getPrenom()));
                    Email.setText(userInfos.getEmail());
                    Adress.setText(userInfos.getAdress());
                    Tel.setText(userInfos.getTel());
                    NB_favoris.setText(String.valueOf(userInfos.getNB_fav()));
                    NB_produit.setText(String.valueOf(userInfos.getNB_Prod()));
                    if (userInfos.getImage_profile() == null) {
                        Img_profil.setImageResource(R.drawable.ic_useravatar);
                    } else {
                      Log.i("image", userInfos.getImage_profile());
                        Img_profil.setImageURI(userInfos.getImage_profile());
                    }
                    break;
                case Constants.EDITE_PROFIL:
                    nomEditText.setText(userInfos.getNom());
                    prenomEditText.setText(userInfos.getPrenom());
                    adresseEditText.setText(userInfos.getAdress());
                    telEditText.setText(userInfos.getTel());
                    if (userInfos.getImage_profile() == null) {
                        Img_ProfileModifier.setImageResource(R.drawable.ic_useravatar);
                    } else {
                        Img_ProfileModifier.setImageURI(userInfos.getImage_profile());
                        //Log.i("imageModifier", "plan");
                    }
                    break;
            }
        }
    }

    public void openGalleryProfil(View view) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.REQUEST_PERMISSION_STORAGE);
            }
        } else {
            openGallery();
        }
    }

    private void openGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, Constants.REQUEST_GALLERY_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.REQUEST_GALLERY_PHOTO) {
                Uri selectedImage = data.getData();
                try {
                    mPhotoFile = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String path = MediaStore.Images.Media.insertImage(getContentResolver(), BitmapFactory.decodeFile(mPhotoFile.getAbsolutePath()), "Image Description", null);
                imageUri = Uri.parse(path);
                Img_ProfileModifier.setImageURI(imageUri);
            }
        }
    }

    private String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] project = {MediaStore.Images.Media.DATA};
            cursor = getApplicationContext().getContentResolver().query(contentUri, project, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.REQUEST_PERMISSION_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }

    public void MeunProfil(View view) {
        PopupMenu dropDownMenu = new PopupMenu(activity, Menu);
        dropDownMenu.getMenuInflater().inflate(R.menu.menu_profile, dropDownMenu.getMenu());
        dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.editProfil:
                        openDialogProfilUpdate();
                        return true;
                    case R.id.logout:
                        startActivity(new Intent(activity, LoginActivity.class));
                        finishAffinity();
                        return true;
                    default:
                        return false;
                }
            }
        });
        dropDownMenu.show();
    }

    private void openDialogProfilUpdate() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle(getString(R.string.modifier_profile));
        alertDialogBuilder.setCancelable(true);
        initPopupViewControls();
        alertDialogBuilder.setView(popupInputDialogView);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /* Initialize popup dialog view and ui controls in the popup dialog. */
    private void initPopupViewControls() {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        popupInputDialogView = layoutInflater.inflate(R.layout.profil_dialog_update, null);

        nomEditText = popupInputDialogView.findViewById(R.id.nom);
        prenomEditText = popupInputDialogView.findViewById(R.id.prenom);
        adresseEditText = popupInputDialogView.findViewById(R.id.adress);
        telEditText = popupInputDialogView.findViewById(R.id.tel);
        passwordEditText = popupInputDialogView.findViewById(R.id.password);
        Img_ProfileModifier = popupInputDialogView.findViewById(R.id.imageProfile);
        LoadUserInfo(ID_user, Constants.EDITE_PROFIL);
    }

    public void ModifierProfile(View view) {
        password = passwordEditText.getText().toString().trim();
        nom = nomEditText.getText().toString().trim();
        prenom = prenomEditText.getText().toString().trim();
        tel = telEditText.getText().toString().trim();
        adresse = adresseEditText.getText().toString().trim();
        if (Valider()) {
            if (Helpers.isConnected(activity)) {
                MultipartBody.Part part = null;
                if (imageUri != null) {
                    part = prepareFilePart(imageUri);
                }
                Call<RSResponse> callUpload = WebService.getInstance().getApi().editProfile(
                        part,
                        createPartFormString(nom),
                        createPartFormString(prenom),
                        createPartFormString(tel),
                        createPartFormString(adresse),
                        createPartFormString(password),
                        createPartFormString(String.valueOf(ID_user))
                );
                callUpload.enqueue(new Callback<RSResponse>() {
                    @Override
                    public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                        //loading.dismiss();
                        if (response.body().getStatus() == 1) {
                            alertDialog.cancel();
                            ImagePipeline imagePipeline = Fresco.getImagePipeline();
                            imagePipeline.clearMemoryCaches();
                            imagePipeline.clearDiskCaches();
                            imagePipeline.clearCaches();
                            LoadUserInfo(ID_user, Constants.PROFIL);
                        } else if (response.body().getStatus() == 0) {
                            Toast.makeText(getApplicationContext(), "err", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RSResponse> call, Throwable t) {
                        //loading.dismiss();
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Helpers.ShowMessageConnection(activity);
            }
        }
    }

    private boolean Valider() {
        boolean valide = true;
        if (password.isEmpty()) {
            passwordEditText.setError(getString(R.string.champs_obligatoir));
            valide = false;
        }
        if (nom.isEmpty()) {
            nomEditText.setError(getString(R.string.champs_obligatoir));
            valide = false;
        }
        if (prenom.isEmpty()) {
            passwordEditText.setError(getString(R.string.champs_obligatoir));
            valide = false;
        }
        if (tel.isEmpty()) {
            telEditText.setError(getString(R.string.champs_obligatoir));
            valide = false;
        }
        if (adresse.isEmpty()) {
            adresseEditText.setError(getString(R.string.champs_obligatoir));
            valide = false;
        }
        return valide;
    }

    private MultipartBody.Part prepareFilePart(Uri fileUri) {
        File file = FileUtils.getFile(activity, fileUri);
        RequestBody requestBody = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), file);
        return MultipartBody.Part.createFormData("image", file.getName(), requestBody);
    }

    private RequestBody createPartFormString(String value) {
        return RequestBody.create(MultipartBody.FORM, value);
    }

    public void CancelDialog(View view) {
        alertDialog.cancel();
    }
}
