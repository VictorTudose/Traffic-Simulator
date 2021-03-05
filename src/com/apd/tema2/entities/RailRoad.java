package com.apd.tema2.entities;

import com.apd.tema2.Main;
import com.apd.tema2.utils.Prints;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class RailRoad implements Runnable {

    public static Semaphore carsWaiting;
    public static Queue<Car> queue;

    public static void setNumberOfLanes(int nr)
    {
        carsWaiting = new Semaphore(-Main.carsNo+1);
        queue=new LinkedList<>();
    }
    public static void waitAtLane(Car car)
    {
        synchronized (Runnable.class) {
            Prints.waiting("railroad",car);
            queue.add(car);
        }
        carsWaiting.release();
    }

    public static void pass()
    {
        try {
            carsWaiting.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("The train has passed, cars can now proceed");
        while (!queue.isEmpty())
        {
            Prints.exited("railroad",
                    queue.poll());
        }
    }

    @Override
    public void run() {
        pass();
    }
}
