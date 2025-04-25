package com.example.progetto_informatica;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;

public class Gara {
    private ArrayList<Pilota> piloti;
    //private ArrayList<ArrayList<>> tempiPiloti;
    private ArrayList<Integer> puntiPiloti;
    private String nome;
    private int partecipanti;
    private boolean inCorso;
    private String vincitore;

    public Gara() {
        this.piloti = new ArrayList<>();
        this.puntiPiloti = new ArrayList<>();
    }

    public void aggiungiPilota(Pilota pilota) {
        if(!piloti.contains(pilota)){
            piloti.add(pilota);
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Pilota aggiunto con successo!", ButtonType.OK);
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"Pilota già presente!",ButtonType.OK);
            alert.showAndWait();
        }
        puntiPiloti.add(0);
    }

    public void rimuoviPilota(Pilota pilota){
        if(piloti.contains(pilota)){
            piloti.remove(pilota);
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Pilota rimosso con successo!", ButtonType.OK);
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"Pilota NON presente!",ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPartecipanti(int partecipanti) {
        this.partecipanti = partecipanti;
    }

    public void setInCorso(boolean inCorso) {
        this.inCorso = inCorso;
    }

    public void setVincitore(String vincitore) {
        this.vincitore = vincitore;
    }

    public ArrayList<Pilota> getPiloti() {
        return piloti;
    }

    public String getNome() {
        return nome;
    }

    public int getPartecipanti() {
        return partecipanti;
    }

    public boolean isInCorso() {
        return inCorso;
    }

    public String getVincitore() {
        return vincitore;
    }

}
