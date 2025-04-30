package com.example.progetto_informatica;

import java.io.Serializable;
import java.util.ArrayList;

public class Championship implements Serializable {

    private ArrayList<Race> races;
    private String championshipName;
    private int championshipYear;
    private boolean championshipOpen;
    private ArrayList<Pilot> pilots;

    public Championship(String championshipName, int championshipYear) {
        this.championshipName = championshipName;
        this.championshipYear = championshipYear;
        championshipOpen = true;
    }

    public boolean addPilot(Pilot p)
    {
        if(pilots.contains(p))
        {
            throw new RuntimeException("pilot already presents");
        }
        pilots.add(p);
        for(int i = 0; i < races.size(); i++)
        {
            races.get(i).addPilot(p);
        }
        return true;
    }

    public void addRace(String raceName)
    {
        races.add(new Race(raceName, pilots));
    }

    public String getChampionshipName() {
        return championshipName;
    }

    public int getChampionshipYear() {
        return championshipYear;
    }

    public int getChampionshipParticipantsNumber()
    {
        return pilots.size();
    }

    public boolean isChampionshipOpen() {
        return championshipOpen;
    }
}
