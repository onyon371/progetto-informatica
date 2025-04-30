package com.example.progetto_informatica;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Race implements Serializable {

    private String name;
    private LocalDate date;
    private boolean raceOpen;

    private ArrayList<Pilot> pilots;

    private ArrayList<Throws> throwsPilots;

    public Race(String name, ArrayList<Pilot> pilots) {
        this.name = name;
        this.pilots = pilots;
        raceOpen = true;

        this.date = LocalDate.now();

        pilots = new ArrayList<Pilot>();
        throwsPilots = new ArrayList<Throws>();
    }

    public void addPilot(Pilot p)
    {
        pilots.add(p);
    }

    public ArrayList<PilotPoint> getPilotPointsList()
    {
        ArrayList<PilotPoint> pilotPointsList = new ArrayList<PilotPoint>();
        for(int i = 0; i < throwsPilots.size(); i++)
        {
            pilotPointsList.add(new PilotPoint(pilots.get(i), throwsPilots.get(i).getTotPoints()));
        }
        return pilotPointsList;
    }

    public ArrayList<PilotPoint> getBestPilotsAndPoints()
    {
        int pilotPoints = 0;
        ArrayList<PilotPoint> pilotPointsList = new ArrayList<PilotPoint>();

        if(throwsPilots.size() <= 3) {
            for (int i = 0; i < throwsPilots.size(); i++) {
                pilotPointsList.add(new PilotPoint(pilots.get(i), throwsPilots.get(i).getTotPoints()));
            }
        }else
        {
            for(int i = 0; i < throwsPilots.size(); i++)
            {
                pilotPointsList.add(new PilotPoint(pilots.get(i), throwsPilots.get(i).getTotPoints()));
            }

            while(pilotPointsList.size() > 3)
            {
                int lower = pilotPointsList.get(0).getPoints();
                int index = 0;
                for(int i = 0; i < throwsPilots.size(); i++)
                {
                    if(pilotPointsList.get(0).getPoints() < lower)
                    {
                        lower = pilotPointsList.get(0).getPoints();
                        index = i;
                    }
                }
                pilotPointsList.remove(index);
            }
        }
        return pilotPointsList;
    }


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


}
