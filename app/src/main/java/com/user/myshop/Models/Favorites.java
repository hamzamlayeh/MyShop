package com.user.myshop.Models;

public class Favorites {
    private int id;
    private Boutiques boutique;

    public Favorites() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boutiques getBoutique() {
        return boutique;
    }

    public void setBoutique(Boutiques boutique) {
        this.boutique = boutique;
    }
}
