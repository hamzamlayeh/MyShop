package com.user.myshop.Models;

public class Favorites {
    private int id;
    private int id_user;
    private Boutiques boutique;

    public Favorites() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public Boutiques getBoutique() {
        return boutique;
    }

    public void setBoutique(Boutiques boutique) {
        this.boutique = boutique;
    }
}
