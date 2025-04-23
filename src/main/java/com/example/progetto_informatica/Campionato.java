package com.example.progetto_informatica;

import java.util.ArrayList;

public class Campionato {
    private ArrayList<Gara> gareInerenti;
    private String nome;
    private int anno;
    private boolean inCorso;
    private int partecipanti;

    public Campionato(ArrayList<Gara> gareInerenti, String nome, int anno, boolean inCorso, int partecipanti) {
        this.gareInerenti = gareInerenti;
        this.nome = nome;
        this.anno = anno;
        this.inCorso = inCorso;
        this.partecipanti = partecipanti;
    }

    public void aggiungiGara(Gara gara) {
            gareInerenti.add(gara);
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public void setAnno(int anno) {
            this.anno = anno;
        }

        public void setInCorso(boolean inCorso) {
            this.inCorso = inCorso;
        }

        public void setPartecipanti(int partecipanti) {
            this.partecipanti = partecipanti;
        }

        public ArrayList<Gara> getGareInerenti() {
            return gareInerenti;
        }

        public String getNome() {
            return nome;
        }

        public int getAnno() {
            return anno;
        }

        public boolean isInCorso() {
            return inCorso;
        }

        public int getPartecipanti() {
            return partecipanti;
        }
}
