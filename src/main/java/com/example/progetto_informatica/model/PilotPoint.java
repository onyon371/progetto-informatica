package com.example.progetto_informatica.model;

public class PilotPoint {
    Pilot p;
    Integer points;

    public PilotPoint(Pilot p, Integer points) {
        this.p = p;
        this.points = points;
    }

    public Pilot getP() {
        return p;
    }

    public void setP(Pilot p) {
        this.p = p;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
