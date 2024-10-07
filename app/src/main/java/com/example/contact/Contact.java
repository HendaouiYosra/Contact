package com.example.contact;

public class Contact {
    int id;

    public Contact(int id, String nom, int num) {
        this.id = id;
        this.nom = nom;
        this.num = num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    String nom;
    int num;
}
