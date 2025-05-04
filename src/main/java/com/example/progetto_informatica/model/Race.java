package com.example.progetto_informatica.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Race implements Serializable {

    // Nome della gara
    private String name;

    // Data in cui si tiene la gara
    private LocalDate date;

    // Stato della gara: true se aperta, false se chiusa
    private boolean raceOpen;

    // Lista dei piloti che partecipano alla gara
    private ArrayList<Pilot> pilots;

    // Lista dei lanci effettuati dai piloti (oggetti Throws)
    private ArrayList<Throws> throwsPilots;

    // Costruttore: inizializza la gara con nome e lista di piloti
    public Race(String name, ArrayList<Pilot> pilots) {
        this.name = name;
        this.pilots = pilots;
        this.raceOpen = true;
        this.date = LocalDate.now();

        this.pilots = new ArrayList<>();
        this.throwsPilots = new ArrayList<>();
    }

    // Aggiunge un pilota alla gara
    public void addPilot(Pilot p) {
        pilots.add(p);
    }

    // Restituisce una lista di PilotPoint con i punti ottenuti da ciascun pilota
    public ArrayList<PilotPoint> getPilotPointsList() {
        ArrayList<PilotPoint> pilotPointsList = new ArrayList<>();
        for (int i = 0; i < throwsPilots.size(); i++) {
            pilotPointsList.add(new PilotPoint(pilots.get(i), throwsPilots.get(i).getTotPoints()));
        }
        return pilotPointsList;
    }

    // Restituisce i 3 migliori piloti della gara, ordinati per punteggio
    public ArrayList<PilotPoint> getBestPilotsAndPoints() {
        ArrayList<PilotPoint> pilotPointsList = new ArrayList<>();

        // Se ci sono 3 o meno piloti, restituisci tutti
        if (throwsPilots.size() <= 3) {
            for (int i = 0; i < throwsPilots.size(); i++) {
                pilotPointsList.add(new PilotPoint(pilots.get(i), throwsPilots.get(i).getTotPoints()));
            }
        } else {
            // Calcola i punteggi per tutti i piloti
            for (int i = 0; i < throwsPilots.size(); i++) {
                pilotPointsList.add(new PilotPoint(pilots.get(i), throwsPilots.get(i).getTotPoints()));
            }

            // Rimuove i piloti con punteggio più basso finché ne restano solo 3
            while (pilotPointsList.size() > 3) {
                int lower = pilotPointsList.get(0).getPoints();
                int index = 0;
                for (int i = 0; i < pilotPointsList.size(); i++) {
                    if (pilotPointsList.get(i).getPoints() < lower) {
                        lower = pilotPointsList.get(i).getPoints();
                        index = i;
                    }
                }
                pilotPointsList.remove(index);
            }
        }
        return pilotPointsList;
    }

    // Getter e setter per i principali attributi della gara

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRaceOpen() {
        return raceOpen;
    }

    public void setRaceOpen(boolean raceOpen) {
        this.raceOpen = raceOpen;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ArrayList<Pilot> getPilots() {
        return pilots;
    }

    public void setPilots(ArrayList<Pilot> pilots) {
        this.pilots = pilots;
    }

    public ArrayList<Throws> getThrows() {
        return throwsPilots;
    }

    // Restituisce il numero massimo di lanci consentiti
    public Integer getMaxThrows() {
        return Throws.getMaxThrows();
    }

    // Restituisce il numero di lanci completati da un pilota specifico
    public Integer getThrowsCompletedOfSpecificPilot(int index) {
        if (throwsPilots.isEmpty()) return 0;
        return throwsPilots.get(index).getThrowsDone();
    }

    // Restituisce il punteggio massimo ottenibile
    public Integer getMaxPoints() {
        return Throws.getMaxPoints();
    }

    // Restituisce i punti totalizzati da un pilota specifico
    public Integer getPointsOfSpecificPilot(int index) {
        if (throwsPilots.isEmpty()) return 0;
        return throwsPilots.get(index).getTotPoints();
    }

    // Restituisce il miglior tempo registrato da un pilota specifico
    public LocalTime getBestTimeOfSpecificPilot(int index) {
        if (throwsPilots.isEmpty()) return LocalTime.of(0, 0, 0, 0);
        return throwsPilots.get(index).getBestTime();
    }
}
