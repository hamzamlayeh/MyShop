package com.user.myshop.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Produits implements Serializable {
    private int id;
    private String Nom;
    private String Description;
    private String Prix;
    private String Id_User;
    private String date;
    private ArrayList<String> listimage;

    public Produits() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrix() {
        return Prix;
    }

    public void setPrix(String prix) {
        Prix = prix;
    }

    public String getId_User() {
        return Id_User;
    }

    public void setId_User(String id_User) {
        Id_User = id_User;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<String> getListimage() {
        return listimage;
    }

    public void setListimage(ArrayList<String> listimage) {
        this.listimage = listimage;
    }
}
