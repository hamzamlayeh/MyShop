package com.user.myshop.Models;

import java.io.Serializable;
import java.util.List;

public class Produit implements Serializable {
    private int id_prod;
    private String Nom;
    private String Description;
    private String Marque;
    private String Image;
    private String Prix;
    private String Id_User;
    private String date;
    private String[] listimage;

    public Produit() {
    }

    public Produit(int id, String nom, String description, String marque, String image, String prix, String id_User) {
        id_prod = id;
        Nom = nom;
        Description = description;
        Marque = marque;
        Image = image;
        Prix = prix;
        Id_User = id_User;
    }
    public Produit(String nom, String marque, String image, String prix) {

        this.Nom = nom;
        this.Marque=marque;
        this.Prix = prix;
        this.Image = image;

    }
    public Produit(String nom, String marque, String prix) {

        this.Nom = nom;
        this.Marque=marque;
        this.Prix = prix;

    }
    public int getId() {
        return id_prod;
    }

    public void setId(int id) {
        id_prod = id;
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

    public String getMarque() {
        return Marque;
    }

    public void setMarque(String marque) {
        Marque = marque;
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

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String[] getListimage() {
        return listimage;
    }

    public void setListimage(String[] listimage) {
        this.listimage = listimage;
    }
}
