package com.user.myshop.Models;

public class Favorite {
    private Botique botique;

    public Favorite(Botique botique) {
        this.botique = botique;
    }

    public Botique getBotique() {
        return botique;
    }

    public void setBotique(Botique botique) {
        this.botique = botique;
    }
}
