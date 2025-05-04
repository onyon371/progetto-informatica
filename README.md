Controller:
-
### 1) TimerViewController.java:

**Descrizione:**
- Il TimerViewController gestisce la logica dell'interfaccia utente associata a un timer utilizzato per misurare e valutare le performance di un pilota all'interno di una gara o simulazione.
- Il timer mostra minuti, secondi e centesimi di secondo, consente l'avvio e l'arresto tramite pulsanti, e aggiorna automaticamente il punteggio del pilota in base al tempo registrato.

**Funzionalità principali:**

- Visualizzazione in tempo reale di:
  - Minuti
  - Secondi
  - Centesimi di secondo

**Calcolo e assegnazione del punteggio basato sul tempo impiegato:**
- Tempo massimo consentito: 240 secondi (4 minuti)
- Penalità: -2 punti per ogni secondo oltre il limite
- Punteggio minimo: 0 punti

- Gestione dei pulsanti Start e Stop
- Integrazione con i modelli Pilot e PilotPoint

**Metodi principali:**

- void initialize(URL location, ResourceBundle resources)
  - Inizializza lo stato dell'interfaccia utente disabilitando il pulsante di stop.

- void initPilotData(Pilot pilot, PilotPoint pilotPoint)
  - Imposta i riferimenti al pilota e al suo punteggio.

- void handleStart()
  - Avvia il timer se non è già in esecuzione.
  - Disabilita il pulsante di avvio e abilita quello di stop.
  - Il tempo viene aggiornato ogni frame tramite AnimationTimer.

- void handleStop()
  - Ferma il timer, calcola il tempo totale trascorso e aggiorna il punteggio del pilota secondo le regole stabilite.
  - Riabilita il pulsante di avvio per eventuali nuove misurazioni.

**Logica del punteggio:**

- Se il tempo totale è ≤ 240 secondi:
  - score = totalSeconds

- Se il tempo totale è > 240 secondi:
  - score = MAX_TIME_SECONDS - (secondsOverLimit × 2)

- Il punteggio viene aggiornato nell'oggetto PilotPoint associato.

Model:
- 
### 2) Pilot.java

**Descrizione:**
- La classe `Pilot` rappresenta un pilota all'interno del sistema.
- Contiene le informazioni anagrafiche di base: nome e cognome.
- È una classe modello, serializzabile, e utilizzata per identificare univocamente i partecipanti a una gara o competizione.

**Funzionalità principali:**

- Rappresentazione anagrafica di un pilota con:
  - Nome
  - Cognome

- Implementazione dei metodi:
  - `getters` e `setters` per ogni campo
  - `equals()` per confronto logico tra piloti
  - `toString()` per una rappresentazione leggibile (es. "Mario Rossi")

View:
-
### 1) racesMenù.fxml

**Descrizione:**
- Il file `racesMenù.fxml` definisce l'interfaccia grafica per il menù delle gare di un singolo campionato.
- Consente la gestione di gare e partecipanti, la visualizzazione della classifica e l'elenco delle gare svolte.
- L'interfaccia è collegata al controller `SingleChampionshipMenùController`.
- Il layout è personalizzato tramite il file CSS esterno `racesMenùStyle.css`.

**Struttura della GUI:**

- L’interfaccia è strutturata verticalmente tramite un contenitore `VBox`, suddivisa in cinque sezioni principali:

  **1. Barra Azioni (HBox)**
  - Contiene tre pulsanti per le azioni principali:
    - Aggiungi Gara → `handleAddNewRace`
    - Aggiungi Partecipante → `handleAddNewPilot`
    - Indietro → `handleBackToChampionshipMenù`

  **2. Sezione Partecipanti**
  - Visualizza la lista dei partecipanti al campionato.
  - È presente un contenitore scrollabile (`ScrollPane`) con:
    - `VBox fx:id="pilotsParticipantsContainer"` popolato dinamicamente con i partecipanti.

  **3. Classifica**
  - Mostra la classifica attuale del campionato.
  - È presente un:
    - `VBox fx:id="pilotsRankingContainer"` popolato dinamicamente con i punteggi dei piloti.

  **4. Titolo Gare**
  - Visualizza semplicemente l’etichetta “Gare” al centro dell’interfaccia.

  **5. Lista Gare**
  - Contiene l’elenco delle gare create.
  - È presente un contenitore scrollabile (`ScrollPane`) con:
    - `VBox fx:id="racesContainer"` popolato dinamicamente con i dati di ogni gara.
### 2) SingleRaceMenù.fxml

**Descrizione:**
- Il file `SingleRaceMenù.fxml` definisce l'interfaccia grafica per la gestione di una singola gara.
- L’utente può:
  - Tornare indietro al menù del campionato.
  - Cercare un pilota.
  - Visualizzare l’elenco dei partecipanti alla gara corrente.
- L’interfaccia è associata al controller `SingleRaceMenùController`.
- Il layout è stilizzato tramite il CSS esterno `SingleRaceMenù.css`.

**Struttura della GUI:**

- L'interfaccia è organizzata verticalmente tramite un contenitore principale `VBox` e presenta tre sezioni principali:

  **1. Barra dei Pulsanti**
  - Contiene un solo pulsante:
    - "Indietro", che richiama il metodo `handleBackToChampionshipMenù()` nel controller.

  **2. Barra di Ricerca Pilota**
  - Icona di ricerca (`ImageView`) decorativa.
  - Campo di testo:
    - `TextField fx:id="searchField"` con `promptText` per l’inserimento del nome o cognome del pilota da cercare.

  **3. Elenco dei Piloti (ScrollPane)**
  - Contenitore scrollabile dove vengono inseriti dinamicamente i componenti grafici che rappresentano i piloti.
  - Il contenitore:
    - `VBox fx:id="pilotsAnchor"` viene popolato a runtime tramite il controller.

CSS:
-
### 1)SingleRaceMenù.css

**Descrizione:**
- Questo foglio di stile definisce l’aspetto grafico dell’interfaccia utente della schermata SingleRaceMenù.
- Gestisce layout, colori, padding, effetti hover, bordi, stili dei pulsanti e del campo di ricerca.

**Funzionalità principali:**

- Layout generale:
  - Sfondo grigio chiaro per il contenitore principale.
  - Padding interno uniforme.
  - Spaziatura ordinata tra gli elementi.

**Pulsanti:**
- Sfondo blu con testo bianco.
- Bordi arrotondati.
- Effetto hover che modifica leggermente il colore.
- Cursore a forma di mano per evidenziare la cliccabilità.

**ScrollPane:**
- Sfondo trasparente.
- Scrollbar minimali:
  - Solo il pollice visibile.
  - Colore neutro e angoli arrotondati.

**Sezioni per tornei (Card)**
- Sfondo bianco.
- Bordi arrotondati.
- Ombra leggera per profondità visiva.
- Spaziatura interna coerente.

**Etichette e titoli**
- Colore grigio scuro per etichette secondarie.
- Titoli più grandi e in grassetto.

**Etichette di stato**
- Giallo per “in corso”.
- Verde per “completato”.
- Etichette con bordi arrotondati, padding interno e testo evidenziato.

**Campo di ricerca**
- Stile moderno con bordi e sfondo arrotondati.
- Ombra leggera e colore neutro.
- Cambia colore al focus per feedback visivo.
