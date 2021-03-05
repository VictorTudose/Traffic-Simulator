package com.apd.tema2.factory;

import com.apd.tema2.Main;
import com.apd.tema2.entities.*;
import com.apd.tema2.utils.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * Returneaza sub forma unor clase anonime implementari pentru metoda de citire din fisier.
 */
public class ReaderHandlerFactory {

    public static ReaderHandler getHandler(String handlerType) {
        // simple semaphore intersection
        // max random N cars roundabout (s time to exit each of them)
        // roundabout with exactly one car from each lane simultaneously
        // roundabout with exactly X cars from each lane simultaneously
        // roundabout with at most X cars from each lane simultaneously
        // entering a road without any priority
        // crosswalk activated on at least a number of people (s time to finish all of them)
        // road in maintenance - 1 lane 2 ways, X cars at a time
        // road in maintenance - N lanes 2 ways, X cars at a time
        // railroad blockage for T seconds for all the cars
        // unmarked intersection
        // cars racing
        return switch (handlerType) {
            case "simple_semaphore" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) {
                    // Exemplu de utilizare:
                    // Main.intersection = IntersectionFactory.getIntersection("simpleIntersection");
                }
            };
            case "simple_n_roundabout" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {

                    String[] lineargs = br.readLine().split(" ");
                    int[] args= Stream.of(lineargs).mapToInt(Integer::parseInt).toArray();

                    int max_no_of_cars=args[0];
                    int roundabout_passing_time=args[1];

                    SingleMaxim.setMaxim(max_no_of_cars);

                    Constants.setWaitingTime(roundabout_passing_time);
                }
            };
            case "simple_strict_1_car_roundabout" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {

                    String[] lineargs = br.readLine().split(" ");
                    int[] args= Stream.of(lineargs).mapToInt(Integer::parseInt).toArray();

                    int no_of_lanes=args[0];
                    int roundabout_passing_time=args[1];

                    LaneMaxim.setNumberOfLanes(no_of_lanes);
                    LaneMaxim.setMaxim(1);
                    SingleEqual.setEqual(no_of_lanes);

                    Constants.setWaitingTime(roundabout_passing_time);
                }
            };
            case "simple_strict_x_car_roundabout" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {

                    String[] lineargs = br.readLine().split(" ");
                    int[] args= Stream.of(lineargs).mapToInt(Integer::parseInt).toArray();

                    int no_of_lanes=args[0];
                    int roundabout_passing_time=args[1];
                    int exact_no_of_cars_passing_at_once=args[2];

                    LaneEqual.setNumberOfLanes(no_of_lanes);
                    LaneEqual.setEqual(exact_no_of_cars_passing_at_once);

                    SingleEqual.setEqual(no_of_lanes*exact_no_of_cars_passing_at_once);
                    SingleMaxim.setMaxim(no_of_lanes*exact_no_of_cars_passing_at_once);

                    LaneMaxim.setNumberOfLanes(no_of_lanes);
                    LaneMaxim.setMaxim(exact_no_of_cars_passing_at_once);

                    Constants.setWaitingTime(roundabout_passing_time);
                }
            };
            case "simple_max_x_car_roundabout" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    String[] lineargs = br.readLine().split(" ");
                    int[] args= Stream.of(lineargs).mapToInt(Integer::parseInt).toArray();

                    int no_of_lanes=args[0];
                    int roundabout_passing_time=args[1];
                    int max_no_of_cars_passing_at_once=args[2];

                    LaneMaxim.setNumberOfLanes(no_of_lanes);
                    LaneMaxim.setMaxim(max_no_of_cars_passing_at_once);

                    Constants.setWaitingTime(roundabout_passing_time);
                }
            };
            case "priority_intersection" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {

                }
            };
            case "crosswalk" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    String[] lineargs = br.readLine().split(" ");
                    int[] args= Stream.of(lineargs).mapToInt(Integer::parseInt).toArray();

                    int execute_time=args[0];
                    int max_no_of_pedestrians=args[1];

                    CrosswalkPrinter.setNumberOfCars();
                    Main.crosswalkPrinter=new CrosswalkPrinter();

                    Main.pedestrians=new Pedestrians(execute_time,max_no_of_pedestrians);

                }
            };
            case "simple_maintenance" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    String[] lineargs = br.readLine().split(" ");
                    int[] args= Stream.of(lineargs).mapToInt(Integer::parseInt).toArray();

                    int no_of_cars_passing_at_once=args[0];

                    PathCombiner.setNumberOfOutputs(1);
                    PathCombiner.setNumberOfInputsPerOutput(2);

                    PathCombiner.redirectInputTo(0,0);
                    PathCombiner.redirectInputTo(1,0);

                    PathCombiner.setHandle(handlerType);

                    PathCombiner.setNumberOfCars(no_of_cars_passing_at_once);

                }
            };
            case "complex_maintenance" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    String[] lineargs = br.readLine().split(" ");
                    int[] args= Stream.of(lineargs).mapToInt(Integer::parseInt).toArray();

                    int free_lanes=args[0];
                    int total_initial_lanes=args[1];
                    int passing_cars_in_one_go=args[2];

                }
            };
            case "railroad" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    RailRoad.setNumberOfLanes(Constants.numberOfLanes);
                    Main.railRoad=new RailRoad();
                }
            };
            default -> null;
        };
    }

}
