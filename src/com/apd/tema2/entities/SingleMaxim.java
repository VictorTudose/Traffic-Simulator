package com.apd.tema2.entities;

import java.util.concurrent.Semaphore;

public class SingleMaxim {
    public static Semaphore semaphore;
    public static void setMaxim(int n)
    {
        semaphore=new Semaphore(n);
    }

    public static void acquire() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void release(){ semaphore.release();}

}
