Classi:
- class Pilota:
  - Attributi:
    - String nome;
    - String cognome;
  - Metodi:
    - void setNome(String nome);
    - void setCognome(String cognome);
    - String getNome();
    - String getCognome();

- class Campionato:
  - Attributi:
    - ArrayList<Gara> gareInerenti;
    - String nome;
    - int anno;
    - boolean inCorso;
    - int partecipanti;
  - Metodi:
    - void aggiungiGara(Gara gara);
    - void setNome(String nome);
    - void setAnno(int anno);
    - void setInCorso(boolean inCorso);
    - void setPartecipanti(int partecipanti);
    - ArrayList<Gara> getGareInerenti();
    - String getNome();
    - int getAnno();
    - boolean isInCorso();
    - int getPartecipanti();

- class GestioneCampionati():
  - Attributi:
    - ArrayList<Campionato> campionati;
    - button aggiungi;
    - button modifica;
    - button elimina;
  - Metodi:
    - void setCampionati(ArrayList<Campionato> campionati);
    - void getCampionati();
    - void onAggiungi();
    - void onModifica();
    - void onElimina();

- class Gara:
  - Attributi:
    - ArrayList<Pilota> piloti;
    - ArrayList<ArrayList<Tempo>> tempiPiloti;
    - ArrayList<Integer> puntiPiloti;
    - String nome;
    - int partecipanti;
    - boolean inCorso;
    - String vincitore;
  - Metodi:
    - void aggiungiPilota(Pilota pilota);
    - void setNome(String nome);
    - void setPartecipanti(int partecipanti);
    - void setInCorso(boolean inCorso);
    - void setVincitore(String vincitore);
    - ArrayList<Pilota> getPiloti();
    - String getNome();
    - int getPartecipanti();
    - boolean isInCorso();
    - String getVincitore();

- class GestioneGare:
  - Attributi:
    - ArrayList<Gara> gare;
    - button aggiungi;
    - button modifica;
    - button elimina;
  - Metodi:
    -   

- class Tempo:
  - Atrributi:
    - int minuti;
    - int secondi
    - int centesimi
  - Metodi: 
    - void setMinuti(int minuti);
    - void setSecondi(int secondi);
    - void setCentesimi(int centesimi);
    - Integer getMinuti();
    - Integer getSecondi();
    - Integer getCentesimi();

- class Timer:
  - Attributi:
    - Tempo tempo;
  - Metodi:
    - String visualizzaTempo();
    - void startTimer();
    - void stopTimer();
