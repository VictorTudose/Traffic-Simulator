package com.apd.tema2.utils;

import com.apd.tema2.entities.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;


public class Prints {

    public static PrintStream out;
    public static PrintStream debug;

    static{
        out=System.out;
        try {
            debug=new PrintStream(new File("debug.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void setOut(String filepath)
    {
        try {
            out=new PrintStream(new File(filepath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void waiting(String handlerType,Car car)
    {
        switch(handlerType) {
            case "simple_semaphore" -> {
                out.println("Car " + car.getId() + " has reached the semaphore, now waiting...");
            }
            case "simple_n_roundabout"
                    ,"simple_strict_x_car_roundabout" ->{
                out.println("Car "+car.getId()+" has reached the roundabout, now waiting...");
            }
            case "simple_strict_1_car_roundabout" -> {
                out.println("Car "+car.getId()+" has reached the roundabout");
            }

            case "railroad" ->{
                out.println("Car "+
                        car.getId()+
                        " from side number "+
                        car.getStartDirection()
                        +" has stopped by the railroad");
            }
            default -> {}
        }
    }

    public static void reached(String handlerType,Car car)
    {
        switch(handlerType) {
            case "simple_n_roundabout" ->{
                out.println("Car "+car.getId()+" has entered the roundabout");
            }
            case "simple_strict_1_car_roundabout" ->{
                out.println("Car "+car.getId()+" has entered the roundabout from lane "+ car.getStartDirection());
            }
            case "simple_strict_x_car_roundabout" -> {
                out.println(
                        "Car "
                                + car.getId()
                                + " was selected to enter the roundabout from lane "
                                + car.getStartDirection());
            }
            case "simple_max_x_car_roundabout" ->{
                out.println("Car "+
                        car.getId()
                +" has reached the roundabout from lane "
                + car.getStartDirection());
            }
            case "simple_maintenance" ->{
                out.println("Car "
                        +car.getId()
                        + " from side number "
                        +car.getStartDirection()
                        + " has reached the bottleneck"
                );
            }
            default -> {}
        }
    }

    public static void entered(String handlerType,Car car)
    {
        switch(handlerType) {
            case "simple_strict_x_car_roundabout"
                    , "simple_max_x_car_roundabout" ->{
                out.println("Car " +car.getId()+ " has entered the roundabout from lane "+car.getStartDirection());
            }

            default -> {}
        }
    }

    public static void exited(String handlerType,Car car)
    {
        switch(handlerType) {
            case "simple_semaphore" -> {
                out.println("Car "+car.getId()+" has waited enough, now driving...");
            }
            case "simple_n_roundabout"
                    , "simple_strict_1_car_roundabout"
                    , "simple_strict_x_car_roundabout"
                    ,"simple_max_x_car_roundabout" ->{
                out.println(
                        "Car "+car.getId() + " has exited the roundabout after "+
                                Constants.waitingTime /1000
                                +" seconds");
            }
            case "simple_maintenance" ->{
                out.println("Car "
                        +car.getId()
                        + " from side number "
                        +car.getStartDirection()
                        + " has passed the bottleneck"
                );
            }
            case "railroad" ->{
                out.println("Car "+
                        car.getId()+
                        " from side number "+
                        car.getStartDirection()
                        +" has started driving");
            }
            default -> {}
        }
    }

    public static void enterWithPriority(Car car)
    {
        if(car.getPriority()>1) {
            System.out.println("Car "
                    + car.getId()
                    + " with high priority has entered the intersection");
        }
        else
        {
            System.out.println("Car "
                    + car.getId()
                    + " with low priority has entered the intersection");
        }

    }

    public static void tryEnterWithPriority(Car car)
    {
        if(car.getPriority()>1) {
        }
        else
        {
            System.out.println("Car "
                    + car.getId()
                    + " with low priority is trying to enter the intersection...");
        }

    }

    public static void exitWithPriority(Car car)
    {
        if(car.getPriority()>1) {
            System.out.println("Car "
                    + car.getId()
                    + " with high priority has exited the intersection");
        }
        else
        {

        }

    }
}
