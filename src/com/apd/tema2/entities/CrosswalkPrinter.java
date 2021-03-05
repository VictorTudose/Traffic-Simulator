package com.apd.tema2.entities;

import com.apd.tema2.Main;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class CrosswalkPrinter implements Runnable {
    public static AtomicBoolean printGreen;
    public static AtomicBoolean printedRed;

    public static Semaphore carsReady;
    private static Semaphore carsSubscribed;

    private static ArrayList<Car> cars;

    public static void acquire()
    {
        try {
            CrosswalkPrinter.carsReady.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void setNumberOfCars()
    {

        cars=new ArrayList<>();

        carsReady=new Semaphore(0);
        carsSubscribed=new Semaphore(-Main.carsNo+1);

        printGreen=new AtomicBoolean(false);
        printedRed=new AtomicBoolean(true);

    }

    public static void printGreen()
    {
        if(printGreen.get())
            return;
        for(Car car:cars) {
            System.out.println("Car "
                    + car.getId()
                    + " has now green light");
        }
        printGreen.set(true);
        printedRed.set(false);

    }

    public static void printRed()
    {
        if(printedRed.get())
            return;
        for(Car car:cars) {
            System.out.println("Car "
                    + car.getId()
                    + " has now red light");
        }
        printedRed.set(true);
        printGreen.set(false);
    }

    public static void subscribe(Car car)
    {
        synchronized (CrosswalkPrinter.class) {
            cars.add(car);
        }
        carsSubscribed.release();
    }


    @Override
    public void run() {

        try {
            carsSubscribed.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        carsReady.release();

        while (!Main.pedestrians.isFinished())
        {
            if(!Main.pedestrians.isPass()) {
                CrosswalkPrinter.printGreen();
            }
            else
            {
                CrosswalkPrinter.printRed();
            }
        }
    }
}
