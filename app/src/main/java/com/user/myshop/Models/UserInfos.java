package com.user.myshop.Models;

import java.io.Serializable;

public class UserInfos implements Serializable {
    private int id_user;
    private String nom;
    private String prenom;
    private String tel;
    private String email;
    private String password;
    private String adress;
    private String etat;
    private String image_profile;
    private int NB_Prod;
    private int NB_fav;

    public UserInfos() {
    }

    public UserInfos(String nom, String prenom, String tel, String email, String password, String adress) {
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.password = password;
        this.adress = adress;
        this.etat = "0";
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getImage_profile() {
        return image_profile;
    }

    public void setImage_profile(String image_profile) {
        this.image_profile = image_profile;
    }

    public int getNB_Prod() {
        return NB_Prod;
    }

    public void setNB_Prod(int NB_Prod) {
        this.NB_Prod = NB_Prod;
    }

    public int getNB_fav() {
        return NB_fav;
    }

    public void setNB_fav(int NB_fav) {
        this.NB_fav = NB_fav;
    }
}
