package com.example.progetto_informatica;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.Serializable;
import java.util.ArrayList;

public class Gara implements Serializable {
    private ArrayList<Pilota> piloti;
    private ArrayList<String> nomiVelivoliPiloti;
    private ArrayList<Lanci> lanciPiloti;

    private int lanciPerPilota;
    private String nomeGara;
    private int numeroPartecipanti;
    private boolean inCorso;

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
}
