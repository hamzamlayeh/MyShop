package com.user.myshop.Models;

public class Produit {
    private int Id;
    private String Nom;
    private String Description;
    private String Marque;
    private String Image;
    private String Prix;
    private int Id_User;

    public Produit(int id, String nom, String description, String marque, String image, String prix, int id_User) {
        Id = id;
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
        return Id;
    }

    public void setId(int id) {
        Id = id;
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

    public int getId_User() {
        return Id_User;
    }

    public void setId_User(int id_User) {
        Id_User = id_User;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
