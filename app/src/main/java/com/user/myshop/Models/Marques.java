package com.user.myshop.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Marques implements Serializable {
    @SerializedName("id_marq")
    private int id_marq;
    @SerializedName("nom")
    private String nomMarq;

    public Marques() {
    }

    public int getId_marq() {
        return id_marq;
    }

    public void setId_marq(int id_marq) {
        this.id_marq = id_marq;
    }

    public String getNomMarq() {
        return nomMarq;
    }

    public void setNomMarq(String nomMarq) {
        this.nomMarq = nomMarq;
    }
}
