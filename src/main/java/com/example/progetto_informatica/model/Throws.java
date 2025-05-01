package com.example.progetto_informatica.model;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Throws implements Serializable {

    private ArrayList<LocalTime> times;
    private ArrayList<Integer> points;

    private static final LocalTime defaultTargetTime = LocalTime.of(0, 4, 0, 0);
    private final int underTimePenality = 1;
    private final int overTimePenality = underTimePenality*2;
    private static final int nThrows = 4;
    private final int nDiscardedTimes = 1;

    public Throws()
    {
        times = new ArrayList<LocalTime>();
        points = new ArrayList<Integer>();
    }

    private void calculatePoints()
    {
        points.clear();
        for(int i = 0; i < times.size(); i++)
        {

            if(times.get(i).compareTo(defaultTargetTime) == 0)
            {
                points.add(defaultTargetTime.getMinute()*60 + defaultTargetTime.getSecond());
            }else
            {
                Long deltaSeconds = times.get(i).until(defaultTargetTime, ChronoUnit.SECONDS);
                if(deltaSeconds > 0)
                {
                    points.add((int) ((defaultTargetTime.getMinute()*60 + defaultTargetTime.getSecond()) - deltaSeconds*overTimePenality));
                }else
                {
                    points.add((int) ((defaultTargetTime.getMinute()*60 + defaultTargetTime.getSecond()) - deltaSeconds*underTimePenality));
                }
            }
        }
    }

    public int getTotPoints()
    {
        if(times.isEmpty()) return 0;

        calculatePoints();
        ArrayList<Integer> tempPointArray = new ArrayList<Integer>(points);
        int nTimesToKeep = nThrows-nDiscardedTimes;

        while (tempPointArray.size() > nTimesToKeep)
        {
            int lowest = tempPointArray.getFirst();
            int index = 0;
            for(int i = 1; i < tempPointArray.size(); i++)
            {
                if(tempPointArray.get(i) < lowest)
                {
                    lowest = tempPointArray.get(i);
                    index = i;
                }
            }
            tempPointArray.remove(index);
        }

        int tot = 0;
        for(Integer i : tempPointArray) tot+=i;
        return tot;
    }

    public void addNewThrow()
    {
        if(times.size() > nThrows)
        {
            throw new RuntimeException("Throw limit reached");
        }
    }

    public static Integer getMaxPoints()
    {
        return (int) defaultTargetTime.getMinute()*60 + defaultTargetTime.getSecond();
    }

    public static Integer getMaxThrows()
    {
        return nThrows;
    }

    public Integer getThrowsDone()
    {
        return times.size();
    }

    public LocalTime getBestTime()
    {
        LocalTime maxTime = LocalTime.of(0,0,0,0);

        for(LocalTime t : times)
        {
            if(t.compareTo(maxTime) > 0)
            {
                maxTime = t;
            }
        }

        return maxTime;
    }
}


