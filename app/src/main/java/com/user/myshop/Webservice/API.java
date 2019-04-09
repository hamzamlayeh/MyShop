package com.user.myshop.Webservice;

import com.user.myshop.Models.RSResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface API {
    // *********************Produit*****************
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

    @GET("DetailProduit.php")
    Call<RSResponse> DetailProduit(@Query("id_prod") String id_prod);

    @GET("GetUserProd.php")
    Call<RSResponse> loadUserProduit(@Query("id_user") String id_user);

    // *********************Boutique*****************
//    @Multipart
//    @POST("AddBoutique.php")
//    Call<RSResponse> AddBoutique(
//            @Part List<MultipartBody.Part> parts,
//            @Part("size") RequestBody size,
//            @Part("nom_prod") RequestBody nom_prod,
//            @Part("description") RequestBody description,
//            @Part("prix") RequestBody prix,
//            @Part("date") RequestBody date,
//            @Part("id_user") RequestBody id_user
//    );

    @GET("GetAllBoutique.php")
    Call<RSResponse> loadBoutique();

    @GET("DetailBoutique.php")
    Call<RSResponse> DetailBoutique(@Query("id_bout") String id_boutique);

    // *********************Favorite*****************
//    @Multipart
//    @POST("AddFavorite.php")
//    Call<RSResponse> AddFavorite(
//            @Part List<MultipartBody.Part> parts,
//            @Part("size") RequestBody size,
//            @Part("nom_prod") RequestBody nom_prod,
//            @Part("description") RequestBody description,
//            @Part("prix") RequestBody prix,
//            @Part("date") RequestBody date,
//            @Part("id_user") RequestBody id_user
//    );

    @GET("GetUserFav.php")
    Call<RSResponse> loadUserFavorie(@Query("id_user") String id_user);

    // *********************Categorie*****************
    @GET("GetALLCategorie.php")
    Call<RSResponse> AllCategories();

    // *********************Marques*****************
    @GET("GetALLMarques.php")
    Call<RSResponse> AllMarques();
}
