package com.apd.tema2.entities;

import com.apd.tema2.utils.Constants;
import com.apd.tema2.utils.Prints;
import com.apd.tema2.utils.Utils;

import java.util.concurrent.atomic.AtomicInteger;

public class PriorityRoad {

    public static AtomicInteger privilegedCount =new AtomicInteger();

    public static void highPriority(Car car)
    {

        privilegedCount.incrementAndGet();

        Prints.enterWithPriority(car);

        Utils.safeSleep(Constants.PRIORITY_INTERSECTION_PASSING);

        Prints.exitWithPriority(car);

        privilegedCount.decrementAndGet();
        synchronized (PriorityRoad.class)
        {
            PriorityRoad.class.notifyAll();
        }
    }

    public static void lowPriority(Car car)
    {
        Prints.tryEnterWithPriority(car);

        synchronized (PriorityRoad.class)
        {
            while(privilegedCount.get()>0){
                try {
                    PriorityRoad.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Prints.enterWithPriority(car);
        }

    }

    public static void pass(Car car){

        if(car.getPriority()>1) {
            highPriority(car);
        } else {
            lowPriority(car);
        }
    }
}
