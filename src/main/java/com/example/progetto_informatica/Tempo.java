package com.example.progetto_informatica;

import java.util.Objects;

public class Tempo {
    private int minuti;
    private int secondi;
    private int centesimi;

    public Tempo(int minuti, int secondi, int centesimi) {
        this.minuti = minuti;
        this.secondi = secondi;
        this.centesimi = centesimi;
    }

    public void setMinuti(int minuti) {
        this.minuti = minuti;
    }

    public void setSecondi(int secondi) {
        this.secondi = secondi;
    }

    public void setCentesimi(int centesimi) {
        this.centesimi = centesimi;
    }

    public Integer getMinuti() {
        return minuti;
    }

    public Integer getSecondi() {
        return secondi;
    }

    public Integer getCentesimi() {
        return centesimi;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tempo tempo = (Tempo) o;
        return minuti == tempo.minuti && secondi == tempo.secondi && centesimi == tempo.centesimi;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minuti, secondi, centesimi);
    }
}

