package com.example.progetto_informatica;

public class Pilota {
    private String nome;
    private String cognome;

    public Pilota(String nome, String cognome) {
        this.nome = nome;
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    @Override
    //serve a far considerare 2 piloti uguali
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pilota)) return false;
        Pilota pilota = (Pilota) o;
        return nome.equalsIgnoreCase(pilota.nome) && cognome.equalsIgnoreCase(pilota.cognome);
    }
}
