package com.apd.tema2.factory;

import com.apd.tema2.Main;
import com.apd.tema2.entities.*;
import com.apd.tema2.utils.*;
import com.apd.tema2.utils.Utils;

import static java.lang.Thread.sleep;

/**
 * Clasa Factory ce returneaza implementari ale InterfaceHandler sub forma unor
 * clase anonime.
 */
public class IntersectionHandlerFactory {

    public static IntersectionHandler getHandler(String handlerType) {
        /*
         simple semaphore intersection
         max random N cars roundabout (s time to exit each of them)
         roundabout with exactly one car from each lane simultaneously
         roundabout with exactly X cars from each lane simultaneously
         roundabout with at most X cars from each lane simultaneously
         entering a road without any priority
         crosswalk activated on at least a number of people (s time to finish all of
         them)
         road in maintenance - 2 ways 1 lane each, X cars at a time
         road in maintenance - 1 way, M out of N lanes are blocked, X cars at a time
         railroad blockage for s seconds for all the cars
         unmarked intersection
         cars racing
        */
        return switch (handlerType) {
            case "simple_semaphore" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    Prints.waiting(handlerType,car);
                    CarEntry.waitForAll();
                    Utils.safeSleep(car.getWaitingTime());
                    Prints.exited(handlerType,car);
                }
            };
            case "simple_n_roundabout" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    Prints.waiting(handlerType,car);
                    CarEntry.waitForAll();
                    SingleMaxim.acquire();
                    Utils.safeSleep(car.getWaitingTime());
                    Prints.reached(handlerType,car);
                    Prints.exited(handlerType,car);
                    SingleMaxim.release();
                }
            };
            case "simple_strict_1_car_roundabout" -> new IntersectionHandler() {
                @Override
                public void handle(Car car)
                {
                    Prints.waiting(handlerType,car);
                    CarEntry.waitForAll();
                    LaneMaxim.acquire(car.getStartDirection());
                    Prints.reached(handlerType,car);
                    Utils.safeSleep(car.getWaitingTime());
                    SingleEqual.waitAtSingle();
                    Prints.exited(handlerType,car);
                    LaneMaxim.release(car.getStartDirection());
                }
            };
            case "simple_strict_x_car_roundabout" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {

                    Prints.waiting(handlerType,car);
                    CarEntry.waitForAll();
                    LaneMaxim.acquire(car.getStartDirection());

                    Prints.reached(handlerType,car);
                    Utils.safeSleep(Constants.waitingTime);
                    Prints.entered(handlerType,car);
                    LaneEqual.wait_at_lane(car.getStartDirection());
                    SingleEqual.waitAtSingle();
                    Prints.exited(handlerType,car);

                    LaneMaxim.release(car.getStartDirection());
                }
            };
            case "simple_max_x_car_roundabout" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    // Get your Intersection instance
                    try {
                        sleep(car.getWaitingTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } // NU MODIFICATI

                    // Continuati de aici

                    LaneMaxim.acquire(car.getStartDirection());
                    Prints.reached(handlerType,car);
                    Utils.safeSleep(Constants.waitingTime);
                    Prints.entered(handlerType,car);
                    Prints.exited(handlerType,car);
                    LaneMaxim.release(car.getStartDirection());
                }
            };
            case "priority_intersection" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    // Get your Intersection instance

                    try {
                        sleep(car.getWaitingTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } // NU MODIFICATI

                    // Continuati de aici
                    PriorityRoad.pass(car);
                }
            };
            case "crosswalk" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {

                    CrosswalkPrinter.subscribe(car);
                }
            };
            case "simple_maintenance" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    CarEntry.waitForAll();
                    PathCombiner.enter(car);
                }
            };
            case "complex_maintenance" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    
                }
            };
            case "railroad" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {

                    CarEntry.waitForAll();
                    RailRoad.waitAtLane(car);
                }
            };
            default -> null;
        };
    }
}
