package com.example.progetto_informatica;

import java.io.Serializable;
import java.util.ArrayList;

public class Race implements Serializable {

    private String name;
    private boolean raceOpen;

    private ArrayList<Pilot> pilots;
    private ArrayList<Throws> throwsPilots;

    public Race(String name, ArrayList<Pilot> pilots) {
        this.name = name;
        this.pilots = pilots;
        raceOpen = true;

        pilots = new ArrayList<Pilot>();
        throwsPilots = new ArrayList<Throws>();
    }

    public void addPilot(Pilot p)
    {
        pilots.add(p);
    }
}
