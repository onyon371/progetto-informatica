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

        pilots = new ArrayList<Pilot>();
        races = new ArrayList<Race>();
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

    public ArrayList<PilotPoint> getBestPilotsAndPoints()
    {
        ArrayList<PilotPoint> pilotPointsList = races.getFirst().getPilotPointsList();

        if(races.size() > 1)
        {
            for(int i = 1; i < races.size(); i++)
            {
                ArrayList<PilotPoint> currentRacePilotPointList = races.get(i).getPilotPointsList();
                for(int q = 0; q < pilotPointsList.size(); q++)
                {
                    pilotPointsList.get(q).points += currentRacePilotPointList.get(q).points;
                }
            }
        }

        while(pilotPointsList.size() > 3)
        {
            int lower = pilotPointsList.get(0).getPoints();
            int index = 0;
            for(int i = 0; i < pilotPointsList.size(); i++)
            {
                if(pilotPointsList.get(0).getPoints() < lower)
                {
                    lower = pilotPointsList.get(0).getPoints();
                    index = i;
                }
            }
            pilotPointsList.remove(index);
        }

        return pilotPointsList;
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

    public ArrayList<Race> getRaces() { return races;}

    public ArrayList<Pilot> getPilots() {
        return pilots;
    }

    public void setPilots(ArrayList<Pilot> pilots) {
        this.pilots = pilots;
    }

    @Override
    public String toString() {
        return "Championship{" +
                "championshipName='" + championshipName + '\'' +
                '}';
    }
}
