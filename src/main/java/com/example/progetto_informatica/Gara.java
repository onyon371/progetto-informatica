package com.example.progetto_informatica;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;

public class Gara implements Serializable {
    private ArrayList<Pilota> piloti;
    private ArrayList<String> nomiVelivoliPiloti;
    private ArrayList<Lanci> lanciPiloti;
    private ArrayList<Integer> puntiPiloti;

    private int lanciPerPilota;
    private String nomeGara;
    private int numeroPartecipanti;
    private boolean inCorso;
    private Time targetTime;

    public Gara(int lanciPerPilota, String nomeGara) {

        this.lanciPerPilota = lanciPerPilota;
        this.nomeGara = nomeGara;
        numeroPartecipanti = 0;
        this.inCorso = true;
    }

    public void aggiungiPilota(Pilota p, String nomeVelivolo)
    {
        if(inCorso) {
            piloti.add(p);
            nomiVelivoliPiloti.add(nomeVelivolo);
            numeroPartecipanti++;
        }
    }

    public void rimuoviPilota(Pilota p)
    {
        for(int i = 0; i < piloti.size(); i++)
        {
            if(piloti.get(i).equals(p))
            {
                piloti.remove(i);
                nomiVelivoliPiloti.remove(i);
            }
        }
    }

    public void aggiungiTempo(Pilota p, Tempo t)
    {
        int indexPilota = piloti.indexOf(p);

        if(lanciPiloti.get(indexPilota).tempi.size() < lanciPerPilota)
        {
            lanciPiloti.get(indexPilota).aggiungiLancio(t);
        }
    }

    public void rimuoviTempo(Pilota p, Tempo t)
    {
        int indexPilota = piloti.indexOf(p);

        lanciPiloti.get(indexPilota).rimuoviTempo(t);
    }

    public void modificaTempo(Pilota p, Tempo oldTemp, Tempo newTemp)
    {
        int indexPilota = piloti.indexOf(p);

        lanciPiloti.get(indexPilota).modificaTempo(oldTemp, newTemp);
    }

    public void calcolaPunti()
    {
        int maxPoint = 0;
        for(int i = 0; i < piloti.size(); i++)
        {
            Lanci lanciAttuali = lanciPiloti.get(i);

            for(Tempo t : lanciAttuali.tempi)
            {
                int tempPoint = 0;
                int calculetedMaxPoint = tempPoint = targetTime.getMinutes()*60+targetTime.getSeconds();

                if(t.getCentesimi()>499)
                {
                    t.setSecondi(t.getSecondi()+1);
                    if(t.getSecondi()>=60)
                    {
                        t.setSecondi(0);
                        t.setMinuti(t.getMinuti()+1);
                    }
                }

                t.setCentesimi(0);

                if(t.equals(targetTime)) tempPoint = calculetedMaxPoint;

                else if(t.getMinuti() == targetTime.getMinutes())
                {
                    if(t.getSecondi() > targetTime.getSeconds())
                    {
                        tempPoint = calculetedMaxPoint - (t.getSecondi()-targetTime.getSeconds())*2;
                    }else
                    {
                        tempPoint = calculetedMaxPoint - (t.getSecondi()-targetTime.getSeconds());
                    }
                }else if(t.getMinuti() > targetTime.getMinutes())
                {
                    int secondi = t.getMinuti()*60+t.getSecondi();
                    tempPoint = calculetedMaxPoint - (secondi-calculetedMaxPoint)*2;

                }else
                {
                    int secondi = t.getMinuti()*60+t.getSecondi();
                    tempPoint = calculetedMaxPoint - (secondi-calculetedMaxPoint);
                }

                if(tempPoint>maxPoint) maxPoint=tempPoint;
            }
        }
    }
}
