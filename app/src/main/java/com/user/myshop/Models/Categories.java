package com.user.myshop.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Categories implements Serializable {
    @SerializedName("id_cat")
    private int id_cat;
    @SerializedName("nom")
    private String nomCat;
    @SerializedName("avec_marque")
    private int AvecMarque;

    public Categories() {
    }

    public int getId_cat() {
        return id_cat;
    }

    public void setId_cat(int id_cat) {
        this.id_cat = id_cat;
    }

    public String getNomCat() {
        return nomCat;
    }

    public void setNomCat(String nomCat) {
        this.nomCat = nomCat;
    }

    public int getAvecMarque() {
        return AvecMarque;
    }

    public void setAvecMarque(int avecMarque) {
        AvecMarque = avecMarque;
    }
}
