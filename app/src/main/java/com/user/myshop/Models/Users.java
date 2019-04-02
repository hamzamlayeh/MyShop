package com.user.myshop.Models;

public class Users {
    private int idUser;
    private String nom;
    private String prenom;
    private String tel;
    private String email;
    private String password;
    private String adress;
    private String etat;
    private String imageProfile;

    public Users() {
    }

    public Users(int idUser, String nom, String prenom, String tel, String email, String password, String adress, String etat, String imageProfile) {
        this.idUser = idUser;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.password = password;
        this.adress = adress;
        this.etat = etat;
        this.imageProfile = imageProfile;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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

    public String getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(String imageProfile) {
        this.imageProfile = imageProfile;
    }
}
