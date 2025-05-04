Controller:
-
### 1) StopWatchController.java:

**Descrizione:**
- Il StopWatchController gestisce la logica dell'interfaccia utente associata a un timer utilizzato per misurare e valutare le performance di un pilota all'interno di una gara o simulazione.
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

### 2)SingleRaceMenùController.java

**Descrizione:**
- Il controller gestisce la schermata "SingleRaceMenù", che visualizza i piloti partecipanti a una gara specifica e offre funzionalità di ricerca e interazione.
- Permette all'utente di vedere le card dei piloti con informazioni come posizione, statistiche e miglior tempo.
- Gestisce la navigazione verso la schermata dei lanci di un pilota.

**Funzionalità principali:**

- **Inizializzazione dell'interfaccia:**
  - Imposta il testo di suggerimento per il campo di ricerca.
  - Carica l'icona della lente nel campo di ricerca.
  - Aggiunge un listener al campo di ricerca, che aggiorna dinamicamente la visualizzazione delle card dei piloti al cambiamento del testo inserito.

- **Gestione dei dati:**
  - I riferimenti alla gara e al campionato vengono impostati tramite il metodo `initRaceReference()`.
  - Quando viene avviato il controller, viene chiamato `renderPilotCards("")` per visualizzare tutte le card dei piloti.

- **Rendering delle card dei piloti:**
  - Il metodo `renderPilotCards(String filter)` filtra i piloti in base al testo di ricerca e aggiunge una card per ciascun pilota che soddisfa i criteri.
  - Usa un `Set` per evitare la visualizzazione di duplicati nei nomi dei piloti.
  - Ogni card viene creata con il metodo `createPilotCard(int i, int rank)`, che include informazioni come il nome del pilota, il numero di lanci effettuati, il miglior tempo e i punti totali.
  - Le card sono dotate di effetti visivi (hover, ombra) per migliorare l'interazione utente.

- **Navigazione:**
  - Il metodo `handleBackToChampionshipMenù()` consente di tornare alla schermata del campionato principale, utilizzando il metodo `Main.openSingleChampionshipMenù()`.

### 3) SingleChampionshipMenùController.java

**Descrizione:**
- Il `SingleChampionshipMenùController` gestisce la schermata dedicata a un singolo campionato.
- Permette di visualizzare informazioni generali sul campionato, inclusi:
  - La lista dei partecipanti
  - La classifica aggiornata dei piloti
  - Le gare create e i relativi dettagli
- Offre inoltre funzionalità per aggiungere gare e piloti, o gestire le gare esistenti.

**Funzionalità principali:**

- **Visualizzazione partecipanti:**
  - Mostra in ordine numerato i piloti iscritti al campionato.

- **Visualizzazione classifica:**
  - Mostra i piloti ordinati per punteggio, con posizione e punti totali.

- **Visualizzazione gare:**
  - Ogni gara viene rappresentata come una card con nome, data, partecipanti, stato e vincitore (se disponibile).

- **Gestione gare:**
  - Possibilità di aggiungere nuove gare o eliminarle.

- **Navigazione:**
  - Ritorno al menù generale dei campionati e apertura di viste specifiche per gare o piloti.

**Metodi principali:**

- `void initialize(URL location, ResourceBundle resources)`
  - Inizializza il controller impostando a `null` il riferimento al campionato.

- `void initChampionhip(Championship championshipReference)`
  - Inizializza il controller con il campionato selezionato, caricando:
    - Partecipanti (`setParticipantsContainer`)
    - Classifica (`setPilotsRankingContainer`)
    - Gare (`addRacesCard`)

- `void setParticipantsContainer()`
  - Mostra i partecipanti del campionato:
    - Se la lista è vuota, visualizza "Nessun Partecipante!"
    - Altrimenti elenca tutti i piloti con numerazione.

- `void setPilotsRankingContainer()`
  - Mostra la classifica piloti:
    - Se vuota, visualizza "Classifica Vuota!"
    - Altrimenti mostra ogni pilota con posizione e punteggio.

- `void addRacesCard()`
  - Crea dinamicamente le card per ciascuna gara:
    - Mostra nome, data, numero partecipanti, stato (In corso / Terminata), vincitore (se presente)
    - Ogni card può essere cliccata per aprire la vista

### 4)ShowThrowsController.java

**Descrizione:**
- Il `ShowThrowsController` gestisce la schermata in cui vengono visualizzati i lanci effettuati da un pilota all'interno di una gara specifica.
- Consente la consultazione dei tempi e dei punteggi relativi a ciascun lancio ed eventualmente l’esecuzione di un nuovo lancio, se non è stato ancora raggiunto il numero massimo consentito.

**Funzionalità principali:**

- **Visualizzazione dei lanci effettuati:**
  - Ogni lancio è rappresentato da una card che mostra:
    - Numero del lancio
    - Tempo registrato
    - Punti ottenuti

- **Gestione lancio:**
  - Permette l’avvio di un nuovo lancio tramite cronometro, se non è stato ancora raggiunto il limite massimo.

- **Navigazione:**
  - Gestione del ritorno alla schermata precedente relativa alla gara.

**Metodi principali:**

- `void initialize(URL location, ResourceBundle resources)`
  - Metodo standard di inizializzazione del controller (attualmente non utilizzato direttamente).

- `void initThrowsData(Championship championshipReference, Race raceReference, int i)`
  - Inizializza il controller con:
    - Riferimento al campionato
    - Riferimento alla gara
    - Indice del pilota selezionato
  - Chiama `addThrowsCards()` per aggiornare la visualizzazione.

- `private void addThrowsCards()`
  - Costruisce dinamicamente la visualizzazione delle card dei lanci:
    - Per ogni lancio (fino a `Throws.nThrows`):
      - Se il lancio è stato effettuato, mostra tempo e punti
      - Altrimenti, mostra “Tempo: -” e “Punti: -”
    - Le card sono aggiunte al contenitore `throwsAnchor` e stilizzate per chiarezza visiva.

- `@FXML void handleExecuteThrow()`
  - Controlla se il pilota ha ancora lanci disponibili:
    - Se sì, apre la vista `StopWatchView` per misurare un nuovo lancio
    - Se no, mostra un messaggio d’errore

- `@FXML void handleBackToSavedPilotsView()`
  - Torna alla schermata precedente, ossia quella della gara (`SpecificRaceMenù`).

**Logica del dominio:**

- **Numero massimo di lanci:** controllato tramite `Throws.getMaxThrows()`
- **Condizione per nuovo lancio:** confronta il numero di lanci effettuati (`getThrowsDone()`) con il massimo

### 5)SavedPilotsController.java

**Descrizione:**
- Il `SavedPilotsController` gestisce la visualizzazione, creazione e rimozione dei piloti salvati, che possono essere successivamente aggiunti a un campionato.
- Si interfaccia con il `SingleChampionshipMenùController` per aggiornare la lista dei partecipanti al campionato selezionato.
- I dati dei piloti vengono salvati e caricati da un file binario (`pilots.bin`).

**Funzionalità principali:**

- **Visualizzazione piloti salvati:**
  - Ogni pilota è mostrato tramite una card contenente:
    - Numero progressivo
    - Nome e cognome
    - Pulsante per aggiungerlo al campionato attivo
    - Menu opzioni per la rimozione del pilota

- **Aggiunta pilota:**
  - Permette all’utente di inserire nome e cognome tramite due finestre di dialogo successive.
  - Se il pilota non è già presente, viene salvato su file e visualizzato.

- **Eliminazione pilota:**
  - Rimuove un pilota dalla lista dei salvati, aggiorna l’interfaccia e salva i dati aggiornati.

- **Aggiunta al campionato:**
  - Ogni pilota può essere aggiunto al campionato in corso (se non già presente).
  - Se l’aggiunta ha successo, vengono aggiornati i partecipanti e le relative gare nel controller del campionato.

- **Persistenza dati:**
  - I piloti vengono caricati e salvati da/in un file binario chiamato `pilots.bin`.

**Metodi principali:**

- `void initialize(URL location, ResourceBundle resources)`
  - Metodo chiamato automaticamente alla creazione del controller.
  - Imposta il riferimento al campionato a `null`.

- `void initChampionship(Championship championshipReference, SingleChampionshipMenùController singleChampionshipMenùControllerReference)`
  - Inizializza i riferimenti al campionato attivo e al relativo controller.
  - Carica i piloti salvati e aggiorna

### 6)AllChampionshipsMenùController.java

**Descrizione:**
- Il controller `AllChampionshipsMenùController` gestisce la schermata principale dell'applicazione dove vengono mostrati tutti i campionati disponibili.
- Permette la visualizzazione, creazione ed eliminazione dei campionati.
- I campionati sono rappresentati da schede interattive (cards), e i dati vengono caricati e salvati da/verso un file binario (`save.bin`).

**Funzionalità principali:**

- **Visualizzazione interfaccia campionati:**
  - Ogni campionato è visualizzato tramite una card che mostra:
    - Nome del campionato
    - Anno (assegnato automaticamente all’anno corrente alla creazione)
    - Numero di partecipanti
    - Stato del campionato (Aperto / Terminato)
  - Ogni card contiene anche un menu con opzione per eliminare il campionato.

- **Creazione campionato:**
  - L’utente può creare un nuovo campionato inserendo un nome tramite una finestra di dialogo.
  - Il nuovo campionato viene aggiunto alla lista e viene creata dinamicamente la relativa card.

- **Eliminazione campionato:**
  - Ogni card include un’opzione per rimuovere il campionato selezionato, con aggiornamento automatico dell’interfaccia.

- **Persistenza dati:**
  - I campionati vengono salvati e caricati da un file binario chiamato `save.bin` mediante serializzazione.

**Metodi principali:**

- `void initialize(URL location, ResourceBundle resources)`
  - Metodo di inizializzazione automatico.
  - Carica i campionati da file (se non già presenti) e crea le rispettive schede grafiche.

- `void handleAddTournament()`
  - Mostra un `TextInputDialog` per inserire il nome di un nuovo campionato.
  - Crea un nuovo oggetto `Championship`, lo aggiunge alla lista e ne genera la card visiva.
  - Mostra un errore se il nome inserito è vuoto.

- `void deleteTournament(Championship championshipReference)`
  - Rimuove un campionato dalla lista e ricarica dinamicamente l’interfaccia.

- `void addAllChampionshipsCards()`
  - Cancella tutte le schede visive correnti e le ricrea sulla base della lista aggiornata.

- `void addTournamentCard(String year, String title, String participants, boolean status, Championship championshipReference)`
  - Crea graficamente una card per un singolo campionato.
  - Imposta:
    - Testo descrittivo (nome, anno, partecipanti, stato)
    - Azione di apertura al click
    - Menu per eliminazione del campionato

- `void getChampionships()`
  - Carica da file `save.bin` la lista dei campionati salvati.

- `static void saveChampionships()`
  - Salva la lista attuale di campionati in `save.bin` tramite serializzazione.
---
Model:
- 
### 1) Pilot.java

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
### 2)Race.java
   **Descrizione:**
- La classe Race rappresenta una gara all'interno di un campionato.
- Contiene informazioni sulla gara, come il nome, la data, lo stato della gara (aperta o chiusa), i piloti partecipanti e i lanci effettuati dai piloti.
- Permette di gestire i punti dei piloti, visualizzare i migliori piloti in base ai punteggi e altre statistiche.

**Funzionalità principali:**

- **Attributi principali:**
  - `name`: Il nome della gara.
  - `date`: La data in cui si svolge la gara.
  - `raceOpen`: Stato della gara (true se aperta, false se chiusa).
  - `pilots`: Lista dei piloti che partecipano alla gara.
  - `throwsPilots`: Lista degli oggetti Throws, che rappresentano i lanci effettuati dai piloti.

- **Metodi principali:**
  - **Costruttore (Race(String name, ArrayList<Pilot> pilots)):**
    - Inizializza la gara con il nome, la lista dei piloti, e lo stato della gara come aperto.
    - La data viene impostata alla data corrente.
    - Ogni pilota riceve un oggetto Throws associato.

  - **addPilot(Pilot p):**
    - Aggiunge un pilota alla gara e crea un oggetto Throws per il pilota.

  - **getPilotPointsList():**
    - Restituisce una lista di oggetti PilotPoint, che rappresentano il punteggio di ogni pilota nella gara.

  - **getBestPilotsAndPoints():**
    - Restituisce una lista dei 3 migliori piloti della gara, ordinati per punteggio.
    - Se ci sono 3 o meno piloti, restituisce tutti i piloti.

  - **getMaxThrows():**
    - Restituisce il numero massimo di lanci consentiti per la gara.

  - **getThrowsCompletedOfSpecificPilot(int index):**
    - Restituisce il numero di lanci completati da un pilota specifico, identificato dal suo indice nella lista dei piloti.

  - **getMaxPoints():**
    - Restituisce il punteggio massimo ottenibile da un pilota nella gara, basato sui lanci e sul punteggio per lancio.

  - **getPointsOfSpecificPilot(int index):**
    - Restituisce i punti totalizzati da un pilota specifico, identificato dal suo indice.

  - **getBestTimeOfSpecificPilot(int index):**
    - Restituisce il miglior tempo registrato da un pilota specifico. Se il pilota non ha effettuato lanci, restituisce `00:00:00`.

### 3) PilotPoint.java

**Descrizione:**
  - La classe `PilotPoint` rappresenta l'associazione tra un pilota e il punteggio da lui totalizzato in una gara.
  - Viene utilizzata per gestire classifiche, statistiche e visualizzare i risultati dei piloti.

**Funzionalità principali:**

- Rappresentazione del punteggio associato a un pilota con:
  - Oggetto `Pilot` (`p`)
  - Intero `points`, che rappresenta il punteggio totalizzato

- Implementazione dei metodi:
  - `getP()`: restituisce il pilota associato
  - `setP(Pilot p)`: imposta il pilota associato
  - `getPoints()`: restituisce il punteggio del pilota
  - `setPoints(Integer points)`: imposta il punteggio del pilota

### 4) Throws.java

**Descrizione:**
  - La classe `Throws` rappresenta l’insieme dei lanci effettuati da un pilota durante una gara.
  - Contiene i tempi registrati per ogni lancio, calcola i punteggi associati e gestisce il totale dei punti considerando solo i migliori lanci.
  - Permette di simulare l’effetto di un tempo ideale con penalità diverse in base alla distanza da tale tempo.

**Funzionalità principali:**

- **Attributi principali:**
  - `times`: Lista di oggetti `LocalTime` che rappresentano i tempi registrati per ogni lancio.
  - `points`: Lista di interi, ciascuno rappresentante il punteggio calcolato per un lancio.
  - `defaultTargetTime`: Tempo ideale da raggiungere (4 minuti esatti).
  - `underTimePenality`: Penalità per ogni secondo sotto il tempo ideale (1 punto per secondo).
  - `overTimePenality`: Penalità per ogni secondo oltre il tempo ideale (2 punti per secondo).
  - `nThrows`: Numero massimo di lanci consentiti (4).
  - `nDiscardedTimes`: Numero di lanci da scartare (quelli peggiori, 1 di default).

- **Metodi principali:**
  - **Costruttore (`Throws()`):**
    - Inizializza le liste dei tempi e dei punteggi.

  - **`addNewThrow(LocalTime time)`:**
    - Aggiunge un nuovo lancio con il tempo specificato.
    - Se viene superato il numero massimo di lanci, genera un'eccezione.

  - **`getPoints()`:**
    - Calcola e restituisce i punteggi di tutti i lanci effettuati.

  - **`getTotPoints()`:**
    - Calcola e restituisce la somma dei migliori punteggi, escludendo i peggiori.

  - **`getMaxPoints()`:**
    - Restituisce il punteggio massimo ottenibile per un singolo lancio.

  - **`getMaxThrows()`:**
    - Restituisce il numero massimo di lanci consentiti.

  - **`getThrowsDone()`:**
    - Restituisce il numero di lanci effettuati.

  - **`getBestTime()`:**
    - Restituisce il miglior tempo tra quelli registrati (in base al punteggio).
    - Se non ci sono lanci, restituisce `00:00:00`.

  - **`getTimes()`:**
    - Restituisce la lista di tutti i tempi registrati.
### 5) Championship.java

**Descrizione:**
  - La classe `Championship` rappresenta un campionato, contenente più gare (`Race`) e piloti (`Pilot`).
  - Gestisce lo stato del campionato, i partecipanti e permette di aggiungere nuove gare o piloti.
  - Fornisce funzionalità per calcolare i migliori piloti in base alla somma dei punteggi ottenuti nelle gare.

**Funzionalità principali:**

- **Attributi principali:**
  - `races`: Lista delle gare appartenenti al campionato.
  - `championshipName`: Nome del campionato.
  - `championshipYear`: Anno in cui si svolge il campionato.
  - `championshipOpen`: Booleano che indica se il campionato è ancora aperto.
  - `pilots`: Lista dei piloti iscritti al campionato.

- **Metodi principali:**
  - **Costruttore (`Championship(String championshipName, int championshipYear)`):**
    - Inizializza il nome, anno, stato (aperto), la lista dei piloti e delle gare.

  - **`addPilot(Pilot p)`:**
    - Aggiunge un nuovo pilota al campionato.
    - Il pilota viene automaticamente aggiunto anche a tutte le gare esistenti.
    - Lancia un’eccezione se il pilota è già presente.

  - **`addRace(String raceName)`:**
    - Crea una nuova gara con il nome specificato e vi assegna tutti i piloti attuali.

  - **`getBestPilotsAndPoints()`:**
    - Calcola e restituisce una lista con i 3 piloti migliori in base alla somma dei punti ottenuti in tutte le gare.
    - Se ci sono meno di 3 piloti, restituisce comunque quelli presenti.

  - **`getChampionshipName()`:**
    - Restituisce il nome del campionato.

  - **`getChampionshipYear()`:**
    - Restituisce l'anno del campionato.

  - **`getChampionshipParticipantsNumber()`:**
    - Restituisce il numero di piloti iscritti al campionato.

  - **`isChampionshipOpen()`:**
    - Ritorna `true` se il campionato è ancora aperto.

  - **`getRaces()`:**
    - Restituisce la lista delle gare del campionato.

  - **`getPilots()`:**
    - Restituisce la lista dei piloti iscritti.

  - **`setPilots(ArrayList<Pilot> pilots)`:**
    - Imposta una nuova lista di piloti (es. caricati da file).

  - **`toString()`**
    - Ritorna una stringa rappresentativa del campionato con il suo nome.

---
View:
### 1)racesMenù.fxml

**Descrizione:**
- Il file `racesMenù.fxml` definisce l'interfaccia grafica per il menù delle gare di un singolo campionato.
- Consente la gestione di gare e partecipanti, la visualizzazione della classifica e l'elenco delle gare svolte.
- L'interfaccia è collegata al controller `SingleChampionshipMenùController`.
- Il layout è personalizzato tramite il file CSS esterno `racesMenùStyle.css`.

**Struttura della GUI:**

- L’interfaccia è strutturata verticalmente tramite un contenitore `VBox`, suddivisa in cinque sezioni principali:

  **Barra Azioni (HBox)**
  - Contiene tre pulsanti per le azioni principali:
    - Aggiungi Gara → `handleAddNewRace`
    - Aggiungi Partecipante → `handleAddNewPilot`
    - Indietro → `handleBackToChampionshipMenù`

  **Sezione Partecipanti**
  - Visualizza la lista dei partecipanti al campionato.
  - È presente un contenitore scrollabile (`ScrollPane`) con:
    - `VBox fx:id="pilotsParticipantsContainer"` popolato dinamicamente con i partecipanti.

  **Classifica**
  - Mostra la classifica attuale del campionato.
  - È presente un:
    - `VBox fx:id="pilotsRankingContainer"` popolato dinamicamente con i punteggi dei piloti.

  **Titolo Gare**
  - Visualizza semplicemente l’etichetta “Gare” al centro dell’interfaccia.

  **Lista Gare**
  - Contiene l’elenco delle gare create.
  - È presente un contenitore scrollabile (`ScrollPane`) con:
    - `VBox fx:id="racesContainer"` popolato dinamicamente con i dati di ogni gara.

---

### 2)SingleRaceMenù.fxml

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

  **Barra dei Pulsanti**
  - Contiene un solo pulsante:
    - "Indietro", che richiama il metodo `handleBackToChampionshipMenù()` nel controller.

  **Barra di Ricerca Pilota**
  - Icona di ricerca (`ImageView`) decorativa.
  - Campo di testo:
    - `TextField fx:id="searchField"` con `promptText` per l’inserimento del nome o cognome del pilota da cercare.

  **Elenco dei Piloti (ScrollPane)**
  - Contenitore scrollabile dove vengono inseriti dinamicamente i componenti grafici che rappresentano i piloti.
  - Il contenitore:
    - `VBox fx:id="pilotsAnchor"` viene popolato a runtime tramite il controller.

    
### 3)championshipsMenù.fxml

**Descrizione:**

- Il file FXML descrive l'interfaccia utente della schermata "All Championships Menu".
- Visualizza un elenco di campionati con un'opzione per aggiungere un nuovo campionato.
- Utilizza layout in verticale (`VBox`) e orizzontale (`HBox`) per organizzare i componenti.
- Include una barra di scorrimento per visualizzare l'elenco dei campionati.


**Attributi principali:**

- **`VBox (root element)`**: Contenitore principale con layout verticale.
  - **`spacing="20"`**: Distanza verticale tra i componenti figli.
  - **`styleClass="main-container"`**: Classe CSS per la personalizzazione dello stile.
  - **`fx:controller="com.example.progetto_informatica.controller.AllChampionshipsMenùController"`**: Associa il controller per la gestione dell'interfaccia.

- **`HBox`**: Contenitore orizzontale per i pulsanti.
  - **`spacing="10"`**: Distanza orizzontale tra i componenti figli.
  - **`styleClass="button-container"`**: Classe CSS per lo stile del contenitore dei pulsanti.

- **`Button`**:
  - **Testo**: "Nuovo Campionato".
  - **`styleClass="action-button"`**: Classe CSS per personalizzare l'aspetto del pulsante.
  - **`onAction="#handleAddTournament"`**: Associa un'azione al pulsante, che invoca il metodo `handleAddTournament` del controller quando il pulsante viene premuto.

- **`ScrollPane`**:
  - **`fitToWidth="true"`**: Rende la larghezza della barra di scorrimento adattabile alla larghezza del contenitore principale.
  - **`styleClass="scroll-pane"`**: Classe CSS per personalizzare l'aspetto della barra di scorrimento.
  - **`VBox.vgrow="ALWAYS"`**: Permette alla `VBox` di crescere verticalmente all'interno dello spazio disponibile.
  - **`fx:id="scrollPane"`**: Identificatore unico per il componente, utilizzato nel controller per l'interazione.

- **`VBox (contenitore per la lista dei campionati)`**:
  - **`spacing="15"`**: Distanza verticale tra gli elementi della lista dei campionati.
  - **`styleClass="tournaments-list"`**: Classe CSS per stilizzare l'elenco dei campionati.
  - **`fx:id="championshipAnchor"`**: Identificatore per accedere alla lista dei campionati dal controller.

**Foglio di stile**

- **`stylesheets:`**
  - Collega il foglio di stile `championshipsMenùStyle.css` per la personalizzazione visiva dell'interfaccia.

### 4)savedPilotsView.fxml

**Descrizione:**
- Il file FXML descrive l'interfaccia utente per la schermata "Saved Pilots", che consente agli utenti di visualizzare un elenco di piloti salvati.
- Include un'opzione per creare un nuovo pilota e una barra di scorrimento per visualizzare la lista dei piloti.

**Struttura principale:**

- **VBox (root element):**
  - **Tipo di layout:** Verticale.
  - **Attributi:**
    - `spacing="20"`: Distanza verticale tra i componenti figli.
    - `styleClass="main-container"`: Classe CSS associata per personalizzare lo stile del contenitore principale.
    - `fx:controller="com.example.progetto_informatica.controller.SavedPilotsController"`: Specifica il controller associato a questo file FXML.

- **Foglio di stile:**
  - **stylesheets:** Collega il foglio di stile `savedPilots.css` per la personalizzazione visiva dell'interfaccia.

**Componenti principali:**

- **HBox:**
  - **Tipo di layout:** Orizzontale.
  - **Attributi:**
    - `spacing="10"`: Distanza orizzontale tra i componenti figli.
    - `styleClass="button-container"`: Classe CSS per stilizzare il contenitore dei pulsanti.

- **Button:**
  - **Testo:** "Crea Nuovo Pilota".
  - **styleClass="action-button"**: Classe CSS per personalizzare l'aspetto del pulsante.
  - **onAction="#handleCreateNewPilot"**: Associa un'azione al pulsante, che invoca il metodo `handleCreateNewPilot` del controller quando il pulsante viene premuto.

- **ScrollPane:**
  - **Attributi:**
    - `fitToWidth="true"`: La larghezza della barra di scorrimento si adatta automaticamente alla larghezza del contenitore principale.
    - `styleClass="scroll-pane"`: Classe CSS per personalizzare l'aspetto della barra di scorrimento.
    - `VBox.vgrow="ALWAYS"`: Permette alla `VBox` di crescere verticalmente all'interno dello spazio disponibile.
    - `fx:id="scrollPane"`: Identificatore per interagire con questo componente dal controller.

- **VBox (contenitore per la lista dei piloti):**
  - **Attributi:**
    - `spacing="15"`: Distanza verticale tra gli elementi della lista dei piloti.
    - `styleClass="pilots-list"`: Classe CSS per stilizzare l'elenco dei piloti.
    - `fx:id="pilotsAnchor"`: Identificatore per accedere alla lista dei piloti dal controller.

### 5) ShowThrows.fxml

**Descrizione:**
- Il file `ShowThrows.fxml` definisce l'interfaccia grafica per la schermata dedicata alla gestione e visualizzazione dei lanci effettuati da un pilota.
- L’interfaccia consente di eseguire nuovi lanci, tornare alla schermata precedente e visualizzare in modo ordinato l’elenco dei lanci già effettuati.
- È collegata al controller `ShowThrowsController`.
  - Il layout grafico è personalizzato tramite il foglio di stile `showThrowsStyle.css`.

**Struttura principale:**

- **VBox (root element):**
  - `spacing="20"`
  - `styleClass="main-container"`
  - `fx:controller="com.example.progetto_informatica.controller.ShowThrowsController"`

- **Foglio di stile:**
  - `stylesheets:` `showThrowsStyle.css`

**Componenti principali:**

- **HBox (barra dei pulsanti):**
  - `spacing="10"`
  - `styleClass="button-container"`

  - **Button "Effettua Lancio":**
    - `onAction="#handleExecuteThrow"`
    - `styleClass="action-button"`

  - **Button "Indietro":**
    - `onAction="#handleBackToSavedPilotsView"`
    - `styleClass="action-button"`

- **Label (nome pilota):**
  - `fx:id="pilotNameLabel"`
  - `styleClass="pilot-name-title"`

- **ScrollPane (contenitore lanci):**
  - `fitToWidth="true"`
  - `styleClass="scroll-pane"`
  - Contiene:
    - **VBox fx:id="throwsAnchor"**
      - `spacing="15"`

### 6) StopWatchView.fxml

**Descrizione:**
- Il file `StopWatchView.fxml` definisce l'interfaccia grafica per la schermata dedicata al cronometro, utilizzato per la misurazione del tempo in contesti come gare o simulazioni.
- L’interfaccia consente di avviare e arrestare il conteggio del tempo, visualizzando minuti, secondi e centesimi di secondo in tempo reale.
- È collegata al controller `StopWatchController`.
- Il layout grafico è personalizzato tramite il foglio di stile `StopWatchViewStyle.css`.

**Struttura della GUI:**

- L’interfaccia è organizzata verticalmente tramite un contenitore principale `VBox`, suddiviso in tre sezioni principali:

  **1. Titolo della Schermata (Label)**
  - Etichetta con testo “CRONOMETRO”.
  - Stile:
    - Testo centrato.
    - Grassetto.
    - Dimensione ampia: `-fx-font-size: 36px`.
  - Funzione descrittiva per indicare la schermata all’utente.

  **2. Visualizzazione Tempo (HBox)**
  - Contenitore orizzontale per la visualizzazione del tempo.
  - Include tre `VBox`, una per ciascuna unità di misura:

    - **Minuti:**
      - Label statica: “minuti”.
      - Label dinamica: `fx:id="minutesLabel"` inizializzata a “00”.

    - **Secondi:**
      - Label statica: “secondi”.
      - Label dinamica: `fx:id="secondsLabel"` inizializzata a “00”.

    - **Centesimi:**
      - Label statica: “centesimi”.
      - Label dinamica: `fx:id="millisecondsLabel"` inizializzata a “00”.

  - Ogni `VBox` è:
    - Allineata al centro.
    - Stilizzata con bordi, padding e spaziatura omogenei per leggibilità.
    - Distribuita equamente all'interno dell'`HBox`.

  **3. Pulsanti di Controllo (HBox)**
  - Contenitore orizzontale per i pulsanti operativi:

    - **Pulsante "START":**
      - `fx:id="startButton"`
      - `onAction="#handleStart"`
      - Stile: classe CSS `start-button`.

    - **Pulsante "STOP":**
      - `fx:id="stopButton"`
      - `onAction="#handleStop"`
      - Stile: classe CSS `stop-button`.

  - L’`HBox` ha:
    - `spacing="30"` per separazione visiva chiara.
    - Allineamento centrato per un layout bilanciato.

**Stili CSS collegati:**
- Foglio di stile: `StopWatchViewStyle.css`
- Definisce:
  - Stile del contenitore principale: `main-container`.
  - Colori, bordi e padding delle sezioni dei tempi.
  - Stili dei pulsanti (`start-button`, `stop-button`), inclusi effetti hover, transizioni ed eventuali ombre.

---
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


### 2)racesMenùStyle.css

**Descrizione:**
- Il foglio di stile `racesMenùStyle.css` definisce l'aspetto grafico della schermata del menù gare.
- Fornisce un design chiaro, ordinato e coerente con l'interfaccia generale dell’applicazione.

**Funzionalità principali:**

- **Struttura principale:**
  - Contenitore principale con sfondo grigio chiaro.
  - Padding uniforme su tutti i lati.
  - Distanziamento verticale regolare tra le varie sezioni.

- **Pulsanti:**
  - Colore di sfondo blu con testo bianco.
  - Bordi arrotondati per un aspetto moderno.
  - Effetto hover che scurisce leggermente il colore.
  - Cursore a forma di mano per indicare l’interattività.

- **Sezioni:**
  - Contenitori bianchi per le aree principali (partecipanti, classifica, gare).
  - Angoli arrotondati e ombre leggere per profondità visiva.
  - Titoli in grassetto, colore scuro e padding per evidenziarli.

- **ScrollPane:**
  - Aree scrollabili con sfondo trasparente.
  - Nessun bordo visibile.
  - Liste interne con spaziatura ordinata e padding adeguato.

- **Elenchi e voci:**
  - Font leggibile e colori neutri per tutte le liste (vincitori, gare).
  - Dati come titolo, data, stato e vincitore ben distinti tramite tipografia e colore.

- **Stato delle gare:**
  - Badge di stato con colori distintivi:
    - Completata: verde con testo bianco.
    - In corso: verde con testo scuro.
    - In programma: grigio scuro con testo bianco.
  - Tutti i badge sono arrotondati, in grassetto e ben visibili all’interno delle card gara.

  
### 3)savedPilots.css

**Descrizione:**
- Questo file CSS definisce lo stile della schermata "Saved Pilots", utilizzando classi JavaFX per migliorare l’aspetto grafico e l’esperienza utente.
- Controlla layout, colori, padding, tipografia, interazioni (hover/pressed) e scrollbar.

**Classi principali:**

- **.main-container**
  - `-fx-padding: 20`: Padding interno di 20 pixel.
  - `-fx-background-color: #f5f5f5`: Sfondo grigio chiaro.
  - `-fx-spacing: 15`: Spazio verticale tra i figli.
  - `-fx-border-radius: 10px`: Bordi arrotondati.

- **.button-container**
  - `-fx-alignment: center`: Allineamento centrale dei contenuti.
  - `-fx-spacing: 10`: Spaziatura orizzontale tra i figli.
  - `-fx-padding: 0 0 15 0`: Padding inferiore di 15 pixel.

- **.action-button**
  - `-fx-background-color: #4a90e2`: Colore di sfondo blu.
  - `-fx-text-fill: white`: Testo bianco.
  - `-fx-font-weight: bold`: Testo in grassetto.
  - `-fx-padding: 8 15`: Spaziatura interna.
  - `-fx-background-radius: 5`: Bordi arrotondati.
  - `-fx-cursor: hand`: Mostra una mano al passaggio del mouse.

- **.action-button:hover**
  - `-fx-background-color: #3a7bc8`: Sfondo più scuro al passaggio del mouse.

- **.action-button:pressed**
  - `-fx-background-color: #357ABD`: Sfondo ancora più scuro quando premuto.

- **.scroll-pane**
  - `-fx-background-color: transparent`: Sfondo trasparente.
  - `-fx-padding: 0`: Nessun padding.
  - `-fx-fit-to-width: true`: Adatta alla larghezza del contenitore.

- **.scroll-pane .viewport**
  - `-fx-background-color: transparent`: Viewport trasparente.

- **.scroll-pane .scroll-bar:vertical**
  - `-fx-background-color: transparent`: Barra verticale trasparente.

- **.scroll-pane .scroll-bar:vertical .track**
  - `-fx-background-color: transparent`: Traccia trasparente.
  - `-fx-border-color: transparent`: Nessun bordo.

- **.scroll-pane .scroll-bar:vertical .thumb**
  - `-fx-background-color: #cccccc`: Colore grigio per il pollice della scrollbar.
  - `-fx-background-radius: 5`: Arrotondamento del pollice.

- **.pilots-list**
  - `-fx-padding: 5`: Padding interno.

- **.pilot-container**
  - `-fx-padding: 15`: Spaziatura interna.
  - `-fx-background-color: white`: Sfondo bianco.
  - `-fx-background-radius: 8px`: Bordi arrotondati.
  - `-fx-effect`: Ombra leggera per profondità.
  - `-fx-spacing: 5`: Spaziatura verticale tra gli elementi interni.

- **.pilot-number**
  - `-fx-font-size: 16px`: Dimensione del font maggiore.
  - `-fx-font-weight: bold`: Testo in grassetto.
  - `-fx-text-fill: #333`: Testo grigio scuro.

- **.pilot-data**
  - `-fx-font-size: 14px`: Font standard.
  - `-fx-text-fill: #666`: Testo grigio.
  - `-fx-padding: 0 10`: Spaziatura orizzontale.

- **.pilot-container .action-button**
  - `-fx-background-color: #4CAF50`: Verde per azioni specifiche del pilota.
  - `-fx-text-fill: white`: Testo bianco.
  - `-fx-padding: 8 15`: Padding interno.
  - `-fx-background-radius: 5`: Arrotondamento.
  - `-fx-cursor: hand`: Cursore mano.

- **.pilot-container .action-button:hover**
  - `-fx-background-color: #45a049`: Verde più scuro al passaggio del mouse.

- **.pilot-container .action-button:pressed**
  - `-fx-background-color: #388e3c`: Verde più intenso alla pressione.

- **.separator**
  - `-fx-padding: 10 0`: Padding verticale.

- **.alert-error**
  - `-fx-background-color: #f44336`: Sfondo rosso per errori.
  - `-fx-text-fill: white`: Testo bianco.
  - `-fx-padding: 10px`: Padding interno.
  - `-fx-border-radius: 5px`: Bordi arrotondati.

- **.three-dots**
  - `-fx-font-size: 14px`: Font standard.
  - `-fx-padding: 5`: Padding interno.
  - `-fx-cursor: hand`: Cursore a mano.

- **.menu-button**
  - `-fx-background-color: transparent`: Sfondo trasparente.
  - `-fx-padding: 5 10`: Padding orizzontale.
  - `-fx-border-radius: 5`: Bordi arrotondati.
  - `-fx-font-size: 14px`: Dimensione del testo.
  - `-fx-font-weight: normal`: Peso normale.

- **.menu-item**
  - `-fx-padding: 10`: Spaziatura interna.
  - `-fx-text-fill: #333`: Testo grigio scuro.


### 4)showThrowsStyle.css
**Descrizione:**
- Questo foglio di stile CSS personalizza la schermata dei dettagli di un pilota, includendo elementi come nome, modello, carte dei lanci, etichette e pulsanti d'azione.
- Utilizza classi JavaFX per definire padding, colori, font e comportamenti interattivi (hover/pressed).

**Classi principali:**

- **.main-container**
  - `-fx-padding: 20`: Padding interno uniforme di 20 pixel.
  - `-fx-background-color: white`: Sfondo bianco per il contenitore principale.

- **.pilot-name**
  - `-fx-font-size: 30px`: Testo grande per il nome del pilota.
  - `-fx-font-weight: bold`: Carattere in grassetto.

- **.model-name**
  - `-fx-font-size: 14px`: Dimensione minore per il nome del modello.
  - `-fx-text-fill: gray`: Colore grigio.
  - `-fx-translate-y: 10px`: Traslazione verticale per distanziarlo dal nome pilota.

- **.throw-card**
  - `-fx-background-color: #e2dede`: Sfondo grigio chiaro per ogni "carta lancio".
  - `-fx-padding: 15`: Spaziatura interna.
  - `-fx-border-radius: 10`: Bordi arrotondati.
  - `-fx-background-radius: 10`: Arrotondamento anche dello sfondo.
  - `-fx-border-color: black`: Bordo nero.
  - `-fx-border-width: 1`: Spessore bordo di 1 pixel.

- **.throw-card.error**
  - `-fx-background-color: #f3b6b6`: Sfondo rosso chiaro per carte che indicano un errore.

- **.throw-label**
  - `-fx-font-weight: bold`: Testo in grassetto.
  - `-fx-font-size: 16px`: Dimensione per etichette dei lanci.

- **.throw-info**
  - `-fx-font-size: 14px`: Dimensione del testo informativo.
  - `-fx-text-fill: black`: Colore del testo nero.

- **.action-button**
  - `-fx-background-color: #4a90e2`: Sfondo blu principale.
  - `-fx-text-fill: white`: Testo bianco.
  - `-fx-font-weight: bold`: Testo in grassetto.
  - `-fx-padding: 8 15`: Spaziatura interna.
  - `-fx-background-radius: 5`: Arrotondamento dei bordi.
  - `-fx-cursor: hand`: Cursore a mano al passaggio del mouse.

- **.action-button:hover**
  - `-fx-background-color: #3a7bc8`: Colore di sfondo più scuro al passaggio del mouse.

- **.action-button:pressed**
  - `-fx-background-color: #357ABD`: Sfondo ancora più scuro alla pressione del pulsante.

