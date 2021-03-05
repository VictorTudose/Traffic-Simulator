package com.apd.tema2.entities;

import java.util.concurrent.Semaphore;

public class LaneMaxim {
    public static Semaphore[] semaphores;

    public static void setNumberOfLanes(int nr)
    {
        semaphores=new Semaphore[nr];
    }

    public static void setMaxim(int nr)
    {
        for (int i=0;i<semaphores.length;i++)
            semaphores[i]=new Semaphore(nr);
    }

    public static void acquire(int lan)
    {
        try {
            semaphores[lan].acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void release(int lan)
    {
        semaphores[lan].release();
    }
}
