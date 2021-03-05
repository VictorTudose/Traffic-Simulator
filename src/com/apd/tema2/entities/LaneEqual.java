package com.apd.tema2.entities;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class LaneEqual {
    public static CyclicBarrier[] cyclicBarriers;

    public static void setNumberOfLanes(int nr)
    {
        cyclicBarriers=new CyclicBarrier[nr];
    }

    public static void setEqual(int nr)
    {
        for(int i=0;i<cyclicBarriers.length;i++) {
            cyclicBarriers[i] = new CyclicBarrier(nr);
        }
    }

    public static void wait_at_lane(int lane)
    {
        try {
            cyclicBarriers[lane].await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

}
