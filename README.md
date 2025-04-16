Classi:
  class Pilota:
    Attributi:
      String nome;
      String cognome;
    Metodi:
      void setNome(String nome);
      void setCognome(String cognome);
      String getNome();
      String getCognome();

  class Campionato:
    Attributi:
      ArrayList<Gara> gareInerenti;
      String nome;
      int anno;
      boolean inCorso;
      int partecipanti;
    Metodi:
      void aggiungiGara(Gara gara);
      void setNome(String nome);
      void setAnno(int anno);
      void setInCorso(boolean inCorso);
      void setPartecipanti(int partecipanti);
      ArrayList<Gara> getGareInerenti();
      String getNome();
      int getAnno();
      boolean isInCorso();
      int getPartecipanti();

  class Gara:
    Attributi:
      ArrayList<Pilota> piloti;
      String nome;
      int partecipanti;
      boolean inCorso;
      String vincitore;
    Metodi:
      void aggiungiPilota(Pilota pilota);
      void setNome(String nome);
      void setPartecipanti(int partecipanti);
      void setInCorso(boolean inCorso);
      void setVincitore(String vincitore);
      ArrayList<Pilota> getPiloti();
      String getNome();
      int getPartecipanti();
      boolean isInCorso();
      String getVincitore();

  class Timer:
    Attributi:
      int minuti;
      int secondi;
      int decimi;
    Metodi:
      void setMinuti(int minuti);
      void setSecondi(int secondi);
      void setDecimi(int decimi);
      int getMinuti();
      int getSecondi();
      int getDecimi();
      String visualizzaTempo();
      void startTimer();
      void stopTimer();
