package com.user.myshop.Models;

public class Favorite {
    private Boutique boutique;

    public Favorite(Boutique boutique) {
        this.boutique = boutique;
    }

    public Boutique getBoutique() {
        return boutique;
    }

    public void setBoutique(Boutique boutique) {
        this.boutique = boutique;
    }
}
