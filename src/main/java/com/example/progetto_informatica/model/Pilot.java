package com.example.progetto_informatica.model;

import java.io.Serializable;
import java.util.Objects;

public class Pilot implements Serializable {

    // Nome del pilota
    private String name;

    // Cognome del pilota
    private String surname;

    // Costruttore: inizializza il pilota con nome e cognome
    public Pilot(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    // Restituisce il nome del pilota
    public String getName() {
        return name;
    }

    // Imposta il nome del pilota
    public void setName(String name) {
        this.name = name;
    }

    // Restituisce il cognome del pilota
    public String getSurname() {
        return surname;
    }

    // Imposta il cognome del pilota
    public void setSurname(String surname) {
        this.surname = surname;
    }

    // Verifica l'uguaglianza tra due oggetti Pilot (uguali se hanno stesso nome e cognome)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pilot pilot = (Pilot) o;
        return Objects.equals(name, pilot.name) && Objects.equals(surname, pilot.surname);
    }

    // Restituisce una rappresentazione testuale del pilota (es. "Mario Rossi")
    @Override
    public String toString() {
        return name + " " + surname;
    }
}