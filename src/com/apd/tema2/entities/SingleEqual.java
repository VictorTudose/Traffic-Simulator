package com.apd.tema2.entities;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class SingleEqual {
    public static CyclicBarrier barrier;

    public static void setEqual(int nr)
    {
        barrier=new CyclicBarrier(nr);
    }

    public static void waitAtSingle()
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
