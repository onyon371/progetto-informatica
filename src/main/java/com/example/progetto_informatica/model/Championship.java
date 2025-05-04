package com.example.progetto_informatica.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Championship implements Serializable {

    // Lista delle gare del campionato
    private ArrayList<Race> races;

    // Nome del campionato
    private String championshipName;

    // Anno del campionato
    private int championshipYear;

    // Stato del campionato: true se è aperto, false se chiuso
    private boolean championshipOpen;

    // Lista dei piloti iscritti al campionato
    private ArrayList<Pilot> pilots;

    // Costruttore: inizializza il campionato con nome e anno
    public Championship(String championshipName, int championshipYear) {
        this.championshipName = championshipName;
        this.championshipYear = championshipYear;
        championshipOpen = true;

        pilots = new ArrayList<>();
        races = new ArrayList<>();
    }

    // Aggiunge un pilota al campionato e a tutte le gare esistenti
    public boolean addPilot(Pilot p) {
        if (pilots.contains(p)) {
            throw new RuntimeException("pilot already presents"); // Eccezione se il pilota è già presente
        }
        pilots.add(p);
        for (int i = 0; i < races.size(); i++) {
            races.get(i).addPilot(p); // Aggiunge il pilota a tutte le gare già create
        }
        return true;
    }

    // Calcola i migliori piloti in base alla somma dei punti ottenuti nelle gare
    public ArrayList<PilotPoint> getBestPilotsAndPoints() {
        if (races.isEmpty()) return new ArrayList<>();

        // Ottiene la lista di punti dei piloti dalla prima gara
        ArrayList<PilotPoint> pilotPointsList = races.getFirst().getPilotPointsList();

        // Somma i punti ottenuti nelle gare successive
        if (races.size() > 1) {
            for (int i = 1; i < races.size(); i++) {
                ArrayList<PilotPoint> currentRacePilotPointList = races.get(i).getPilotPointsList();
                for (int q = 0; q < pilotPointsList.size(); q++) {
                    pilotPointsList.get(q).points += currentRacePilotPointList.get(q).points;
                }
            }
        }

        // Mantiene solo i primi 3 piloti con più punti
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

        return pilotPointsList;
    }

    // Aggiunge una nuova gara al campionato, inizializzata con i piloti attuali
    public void addRace(String raceName) {
        races.add(new Race(raceName, pilots));
    }

    // Restituisce il nome del campionato
    public String getChampionshipName() {
        return championshipName;
    }

    // Restituisce l'anno del campionato
    public int getChampionshipYear() {
        return championshipYear;
    }

    // Restituisce il numero di piloti iscritti al campionato
    public int getChampionshipParticipantsNumber() {
        return pilots.size();
    }

    // Ritorna true se il campionato è aperto
    public boolean isChampionshipOpen() {
        return championshipOpen;
    }

    // Restituisce la lista di gare del campionato
    public ArrayList<Race> getRaces() {
        return races;
    }

    // Restituisce la lista dei piloti del campionato
    public ArrayList<Pilot> getPilots() {
        return pilots;
    }

    // Imposta la lista dei piloti (es. da file)
    public void setPilots(ArrayList<Pilot> pilots) {
        this.pilots = pilots;
    }

    // Rappresentazione testuale dell'oggetto Championship
    @Override
    public String toString() {
        return "Championship{" +
                "championshipName='" + championshipName + '\'' +
                '}';
    }
}