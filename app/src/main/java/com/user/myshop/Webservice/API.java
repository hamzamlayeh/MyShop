package com.user.myshop.Webservice;

import com.user.myshop.Models.RSResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface API {
    @Multipart
    @POST("AddProduit.php")
    Call<RSResponse> AddProduit(
            @Part List<MultipartBody.Part> parts,
            @Part("size") RequestBody size,
            @Part("nom_prod") RequestBody nom_prod,
            @Part("description") RequestBody description,
            @Part("prix") RequestBody prix,
            @Part("date") RequestBody date,
            @Part("id_user") RequestBody id_user
    );
}
