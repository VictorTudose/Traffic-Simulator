package com.apd.tema2;

import com.apd.tema2.entities.*;
import com.apd.tema2.io.Reader;
import com.apd.tema2.utils.Prints;
import com.apd.tema2.utils.Utils;

import java.util.Set;

public class Main {
    public static Pedestrians pedestrians = null;
    public static RailRoad railRoad= null;
    public static CrosswalkPrinter crosswalkPrinter= null;
    public static Intersection intersection;
    public static int carsNo;

    public static void main(String[] args) {

        Reader fileReader = Reader.getInstance(args[0]);
        Set<Thread> cars = fileReader.getCarsFromInput();

        for(Thread car : cars) {
            car.start();
        }

        if(pedestrians != null) {
            try {
                Thread p = new Thread(pedestrians);
                Thread q = new Thread(crosswalkPrinter);
                p.start();
                q.start();
                p.join();
                q.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(railRoad != null) {
            try {
                Thread p = new Thread(railRoad);
                p.start();
                p.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for(Thread car : cars) {
            try {
                car.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
