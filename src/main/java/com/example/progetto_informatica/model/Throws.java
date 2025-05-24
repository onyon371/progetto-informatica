package com.example.progetto_informatica.model;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Throws implements Serializable {

    // Lista dei tempi registrati per ogni lancio
    private ArrayList<LocalTime> times;

    // Lista dei punteggi calcolati per ogni tempo
    private ArrayList<Integer> points;

    // Tempo ideale da raggiungere (4 minuti esatti)
    private static final LocalTime defaultTargetTime = LocalTime.of(0, 4, 0, 0);

    // Penalità per lanci sotto il tempo ideale (1 punto per ogni secondo in meno)
    private final int underTimePenality = 1;

    // Penalità per lanci sopra il tempo ideale (2 punti per ogni secondo in più)
    private final int overTimePenality = 2;

    // Numero massimo di lanci consentiti
    public static final int nThrows = 4;

    // Numero di tempi da scartare (quelli peggiori)
    public static final int nDiscardedTimes = 1;

    // Costruttore: inizializza le liste
    public Throws() {
        times = new ArrayList<>();
        points = new ArrayList<>();
    }

    public ArrayList<Integer> getPoints()
    {
        calculatePoints();
        return points;
    }

    // Calcola i punti di ciascun lancio in base alla distanza dal tempo ideale
    private void calculatePoints() {
        points.clear();

        for (int i = 0; i < times.size(); i++) {
            if (times.get(i).equals(defaultTargetTime)) {
                // Se il tempo è esattamente quello ideale, assegna il massimo punteggio
                points.add(defaultTargetTime.getMinute() * 60 + defaultTargetTime.getSecond());
            } else {
                long deltaSeconds = times.get(i).until(defaultTargetTime, ChronoUnit.SECONDS);
                long deltaPositivo = Math.abs(deltaSeconds);

                if (deltaSeconds < 0) {
                    points.add((int)((defaultTargetTime.getMinute() * 60 + defaultTargetTime.getSecond()) - deltaPositivo * overTimePenality));
                } else {
                    points.add((int)((defaultTargetTime.getMinute() * 60 + defaultTargetTime.getSecond()) - deltaPositivo * underTimePenality));
                }

            }
        }
    }

    // Restituisce la somma dei migliori punteggi, scartando i peggiori
    public int getTotPoints() {
        if (times.isEmpty()) return 0;

        calculatePoints();
        ArrayList<Integer> tempPointArray = new ArrayList<>(points);
        int nTimesToKeep = nThrows - nDiscardedTimes;

        while (tempPointArray.size() > nTimesToKeep) {
            int lowest = tempPointArray.getFirst();
            int index = 0;
            for (int i = 1; i < tempPointArray.size(); i++) {
                if (tempPointArray.get(i) < lowest) {
                    lowest = tempPointArray.get(i);
                    index = i;
                }
            }
            tempPointArray.remove(index); // Rimuove il punteggio più basso
        }

        int tot = 0;
        for (Integer i : tempPointArray) tot += i;

        return tot;
    }

    // Aggiunge un nuovo lancio con un determinato tempo (serve come input)
    public void addNewThrow(LocalTime time) {
        if (times.size() >= nThrows) {
            throw new RuntimeException("Throw limit reached");
        }
        times.add(time);
        calculatePoints();
    }

    // Restituisce il punteggio massimo ottenibile per un singolo lancio
    public static Integer getMaxPoints() {
        return defaultTargetTime.getMinute() * 60 + defaultTargetTime.getSecond();
    }

    // Restituisce il numero massimo di lanci consentiti
    public static Integer getMaxThrows() {
        return nThrows;
    }

    // Restituisce il numero di lanci effettuati
    public Integer getThrowsDone() {
        return times.size();
    }

    // Restituisce il miglior tempo tra quelli registrati
    public LocalTime getBestTime() {
        if(times.isEmpty()) return LocalTime.of(0, 0, 0, 0);

        int best = 0;
        int index = 0;
        for (int i = 0; i < points.size(); i++) {
            if (best < points.get(i)) {
                best = points.get(i);
                index = i;
            }
        }
        return times.get(index);
    }

    public ArrayList<LocalTime> getTimes()
    {
        return times;
    }
}
