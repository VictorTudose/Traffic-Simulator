package com.apd.tema2.entities;

import com.apd.tema2.Main;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CarEntry {
    public static CyclicBarrier barrier;

    static
    {
        barrier=new CyclicBarrier(Main.carsNo);
    }

    public static void waitForAll()
    {
        try {
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

}
