package com.user.myshop;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;
import com.user.myshop.Models.RSResponse;
import com.user.myshop.Utils.Constants;
import com.user.myshop.Utils.FileCompressor;
import com.user.myshop.Utils.FileUtils;
import com.user.myshop.Webservice.WebService;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.user.myshop.Utils.Constants.REQUEST_PERMISSION_CAMERA;

public class AjoutProduitActivity extends AppCompatActivity {

    private FileCompressor mCompressor;
    private File mPhotoFile;
    private List<Uri> listPics = new ArrayList<>();
    BottomNavigationView bottomNavigationView;
    EditText NomProd, Description, Prix;
    String nomProd, description, prix;
    SharedPreferences prefs;
    int ID_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_produit);

        prefs = getApplicationContext().getSharedPreferences("Users", MODE_PRIVATE);
        mCompressor = new FileCompressor(this);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        NomProd = findViewById(R.id.NomProduit);
        Description = findViewById(R.id.Desc);
        Prix = findViewById(R.id.Prix);
        AddMenu();
        ID_user = prefs.getInt("ID_User", 0);
        Toast.makeText(this, ID_user + "", Toast.LENGTH_SHORT).show();

    }

    public void Valider(View view) {
        nomProd = NomProd.getText().toString().trim();
        description = Description.getText().toString().trim();
        prix = Prix.getText().toString().trim();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        if (Valider()) {
            final ProgressDialog loading = ProgressDialog.show(this, "Traitement Des Données...", "S'il Vous Plaît, Attendez...", false, false);
            Calendar calendar = Calendar.getInstance();
            List<MultipartBody.Part> parts = new ArrayList<>();
            for (int i = 0; i < listPics.size(); i++) {
                parts.add(prepareFilePart("image[]", listPics.get(i)));
                //Log.d("file", listPics.get(i) + "");
            }
            Call<RSResponse> callUpload = WebService.getInstance().getApi().AddProduit(
                    parts,
                    createPartFormString(String.valueOf(listPics.size())),
                    createPartFormString(nomProd),
                    createPartFormString(description),
                    createPartFormString(prix),
                    createPartFormString(sdf.format(calendar.getTime())),
                    createPartFormString(String.valueOf(ID_user))
            );
            callUpload.enqueue(new Callback<RSResponse>() {
                @Override
                public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                    loading.dismiss();
                    if (response.body().getStatus() == 1) {
                        startActivity(new Intent(getApplicationContext(), ProduitsActivity.class));
                        Toast.makeText(getApplicationContext(), getString(R.string.add_prod_succ), Toast.LENGTH_SHORT).show();
                    } else if (response.body().getStatus() == 0) {
                        Toast.makeText(getApplicationContext(), "err", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RSResponse> call, Throwable t) {
                    loading.dismiss();
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean Valider() {
        boolean valide = true;
        if (nomProd.isEmpty()) {
            NomProd.setError(getString(R.string.champs_obligatoir));
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
        if (ID_user == 0) {
            Toast.makeText(this, "no ID", Toast.LENGTH_SHORT).show();
            valide = false;
        }
        if (listPics.size() <= 0) {
            Toast.makeText(this, getString(R.string.chose_img), Toast.LENGTH_SHORT).show();
            valide = false;
        }
        return valide;
    }

    public void ClickCamera(View view) {
        if (listPics.size() > Constants.MAX_PHOTO_PRODUITS) {
            Toast.makeText(this, Constants.MAX_PHOTO_PRODUITS + " Photos au maximum.", Toast.LENGTH_SHORT).show();
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CAMERA);
                }
            } else {
                openCamera();
            }
        }
    }

    public void ClickGallery(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.REQUEST_PERMISSION_STORAGE);
            }
        } else {
            openGallery();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.REQUEST_PERMISSION_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            }
        } else if (requestCode == Constants.REQUEST_PERMISSION_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }

    private void openGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, Constants.REQUEST_GALLERY_PHOTO);
    }

    private void openCamera() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            mPhotoFile = photoFile;
            Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", photoFile);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(pictureIntent, Constants.REQUEST_TAKE_PHOTO);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            Uri imageUri;
            if (requestCode == Constants.REQUEST_TAKE_PHOTO) {
                try {
                    mPhotoFile = mCompressor.compressToFile(mPhotoFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String path = MediaStore.Images.Media.insertImage(getContentResolver(), BitmapFactory.decodeFile(mPhotoFile.getAbsolutePath()), "Image Description", null);
                imageUri = Uri.parse(path);
                listPics.add(imageUri);
            } else if (requestCode == Constants.REQUEST_GALLERY_PHOTO) {
                Uri selectedImage = data.getData();
                try {
                    mPhotoFile = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String path = MediaStore.Images.Media.insertImage(getContentResolver(), BitmapFactory.decodeFile(mPhotoFile.getAbsolutePath()), "Image Description", null);
                imageUri = Uri.parse(path);
                listPics.add(imageUri);
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

    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        File file = FileUtils.getFile(this, fileUri);
        RequestBody requestBody = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestBody);
    }

    private RequestBody createPartFormString(String value) {
        return RequestBody.create(MultipartBody.FORM, value);
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
                switch (index) {
                    case 0:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getApplicationContext(), ProduitsActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getApplicationContext(), FavoriteActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getApplicationContext(), BoutiqueActivity.class));
                        break;
                }
            }
        });
    }
}
