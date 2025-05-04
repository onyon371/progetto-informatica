package com.example.progetto_informatica.model;

public class PilotPoint {

    // Riferimento al pilota a cui sono assegnati i punti
    Pilot p;

    // Punti totalizzati dal pilota
    Integer points;

    // Costruttore: inizializza il pilota e i suoi punti
    public PilotPoint(Pilot p, Integer points) {
        this.p = p;
        this.points = points;
    }

    // Restituisce il pilota associato
    public Pilot getP() {
        return p;
    }

    // Imposta il pilota associato
    public void setP(Pilot p) {
        this.p = p;
    }

    // Restituisce il punteggio del pilota
    public Integer getPoints() {
        return points;
    }

    // Imposta il punteggio del pilota
    public void setPoints(Integer points) {
        this.points = points;
    }
}
