package com.example.progetto_informatica;

import java.io.Serializable;
import java.util.ArrayList;

public class Lanci implements Serializable {

    public ArrayList<Tempo> tempi;

    public void aggiungiLancio(Tempo t)
    {
        tempi.add(t);
    }

    public void rimuoviTempo(Tempo t)
    {
        tempi.remove(t);
    }

    public void modificaTempo(Tempo oldTemp, Tempo newTemp)
    {
        for(int i = 0; i < tempi.size(); i++) {
            if (tempi.get(i).equals(oldTemp)) {
                tempi.set(i, newTemp);
                break;
            }
        }
    }
}


