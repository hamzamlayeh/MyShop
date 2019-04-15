package com.user.myshop.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Boutiques implements Serializable {

    private int id;
    private String marque;
    private String categorie;
    private String tel;
    private String email;
    private String nomProd;
    private String description;
    private String prix;
    private String date;
    private String idUser;
    private String idProd;
    private String ville;
    private String gouvernement;
    private ArrayList<String> listimage;

    public Boutiques() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
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

    public String getNomProd() {
        return nomProd;
    }

    public void setNomProd(String nomProd) {
        this.nomProd = nomProd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getGouvernement() {
        return gouvernement;
    }

    public void setGouvernement(String gouvernement) {
        this.gouvernement = gouvernement;
    }

    public ArrayList<String> getListimage() {
        return listimage;
    }

    public void setListimage(ArrayList<String> listimage) {
        this.listimage = listimage;
    }

    public String getIdProd() {
        return idProd;
    }

    public void setIdProd(String idProd) {
        this.idProd = idProd;
    }
}
