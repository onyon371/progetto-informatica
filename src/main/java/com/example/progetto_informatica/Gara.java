package com.example.progetto_informatica;
import java.util.ArrayList;

public class Gara {
    private ArrayList<Pilota> piloti;
    //private ArrayList<ArrayList<>> tempiPiloti;
    private ArrayList<Integer> puntiPiloti;
    private String nome;
    private int partecipanti;
    private boolean inCorso;
    private String vincitore;

    public Gara() {}

    public void aggiungiPilota(Pilota pilota) {
        piloti.add(pilota);

        puntiPiloti.add(0);
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPartecipanti(int partecipanti) {
        this.partecipanti = partecipanti;
    }

    public void setInCorso(boolean inCorso) {
        this.inCorso = inCorso;
    }

    public void setVincitore(String vincitore) {
        this.vincitore = vincitore;
    }

    public ArrayList<Pilota> getPiloti() {
        return piloti;
    }

    public String getNome() {
        return nome;
    }

    public int getPartecipanti() {
        return partecipanti;
    }

    public boolean isInCorso() {
        return inCorso;
    }

    public String getVincitore() {
        return vincitore;
    }

}
