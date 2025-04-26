package com.example.progetto_informatica;

public class Timer {

    private long startTime;
    private long endTime;
    private boolean running = false;

    public void start() {
        startTime = System.currentTimeMillis();
        running = true;
    }

    public Tempo stop() {
        if (!running) {
            System.out.println("TIMER NON AVVIATO!");
        }
        endTime = System.currentTimeMillis();
        running = false;

        long elapsedMillis = endTime - startTime;

        int centesimi = (int) ((elapsedMillis % 1000) / 10);
        int secondi = (int) ((elapsedMillis / 1000) % 60);
        int minuti = (int) ((elapsedMillis / 1000) / 60);

        return new Tempo(minuti, secondi, centesimi);
    }
}


