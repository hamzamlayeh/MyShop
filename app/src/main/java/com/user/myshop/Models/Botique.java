package com.user.myshop.Models;

public class Botique {
    private int Id;
    private String Catigorie;
    private String Tel;
    private String Email;
    private  Produit Produit;

    public Botique(int id, String catigorie, String tel, String email, com.user.myshop.Models.Produit produit) {
        Id = id;
        Catigorie = catigorie;
        Tel = tel;
        Email = email;
        Produit = produit;
    }

    public Botique( com.user.myshop.Models.Produit produit) {

        Produit = produit;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCatigorie() {
        return Catigorie;
    }

    public void setCatigorie(String catigorie) {
        Catigorie = catigorie;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public com.user.myshop.Models.Produit getProduit() {
        return Produit;
    }

    public void setProduit(com.user.myshop.Models.Produit produit) {
        Produit = produit;
    }


}
