package com.user.myshop.Webservice;

import com.user.myshop.Models.RSResponse;
import com.user.myshop.Models.User;
import com.user.myshop.Models.UserInfos;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface API {
    @Headers({
            "Accept: application/json"
    })

    //***********************Login*******************
    @POST("Login.php")
    Call<RSResponse> loginUser(@Body User user);

    //***********************Inscription*******************
    @POST("Inscription.php")
    Call<RSResponse> inscrireUser(@Body UserInfos userInfos);

    //***********************User*******************
    @GET("GetUsersById.php")
    Call<RSResponse> loadUser(@Query("id_user") String id_user);

    @Multipart
    @POST("UpdateProfil.php")
    Call<RSResponse> editProfile(
            @Part MultipartBody.Part part,
            @Part("nom") RequestBody nom,
            @Part("prenom") RequestBody prenom,
            @Part("tel") RequestBody tel,
            @Part("adress") RequestBody adress,
            @Part("password") RequestBody password,
            @Part("id_user") RequestBody id_user
    );

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

    @DELETE("DeleteProduit.php")
    Call<RSResponse> DeleteProduit(@Query("id_prod") String id_prod);

    // *********************Boutique*****************
    @FormUrlEncoded
    @POST("AddBoutique.php")
    Call<RSResponse> addBoutique(
            @Field("nom_prod") String nom_prod,
            @Field("description") String description,
            @Field("prix") String prix,
            @Field("ville") String ville,
            @Field("gouvernement") String gouvernement,
            @Field("marque") String marque,
            @Field("categorie") String categorie,
            @Field("contactEmail") String contactEmail,
            @Field("contactTel") String contactTel,
            @Field("date") String date,
            @Field("size") String size,
            @Field("listImage[]") ArrayList<String> listImage,
            @Field("id_user") String id_user,
            @Field("id_prod") String id_prod
    );

    @GET("GetAllBoutique.php")
    Call<RSResponse> loadBoutique();

    @GET("BoutiqueExiste.php")
    Call<RSResponse> boutiqueExiste(@Query("id_prod") String id_prod);

    @GET("DetailBoutique.php")
    Call<RSResponse> DetailBoutique(@Query("id_bout") String id_boutique);

    @DELETE("DeleteBoutique.php")
    Call<RSResponse> DeleteBoutique(@Query("id_bout") String id_bout);

    // *********************Favorite*****************
    @FormUrlEncoded
    @POST("AddFavorie.php")
    Call<RSResponse> AddFavorite(
            @Field("id_bout") String id_bout,
            @Field("id_user") String id_user
    );

    @FormUrlEncoded
    @POST("isFavore.php")
    Call<RSResponse> isFavoree(
            @Field("id_bout") String id_bout,
            @Field("id_user") String id_user
    );

    @DELETE("DeleteFavorie.php")
    Call<RSResponse> DeleteFavorite(@Query("id_bout") String id_bout);

    @GET("GetUserFav.php")
    Call<RSResponse> loadUserFavorie(@Query("id_user") String id_user);

    // *********************Categorie*****************
    @GET("GetALLCategorie.php")
    Call<RSResponse> AllCategories();

    // *********************Marques*****************
    @GET("GetALLMarques.php")
    Call<RSResponse> AllMarques();

    // *********************Gouvernements*****************
    @GET("GetALLGouvernerat.php")
    Call<RSResponse> AllGouvernerat();
}
