package com.apd.tema2.entities;

import com.apd.tema2.utils.Prints;

import java.util.HashMap;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class PathCombiner {

    private static int inputToOutputCount;
    private static int numberOfOutputs;

    private static Semaphore[] semaphores;
    private static int semaphoreCount;

    private static HashMap<Integer,Integer> transitions;

    private static CyclicBarrier[] outBarriers;
    private static CyclicBarrier[] inBarriers;

    private static String handle;

    private static final PathCombiner instance;

    private PathCombiner(){}

    static {
        instance=new PathCombiner();
    }

    public static void setNumberOfInputsPerOutput(int nr)
    {
        inputToOutputCount =nr;
    }

    public static void setNumberOfOutputs(int nr)
    {
        numberOfOutputs =nr;
    }

    public static void setHandle(String handle) {
        PathCombiner.handle = handle;
    }

    private static int next(int nr){
        return (nr+1)% inputToOutputCount;
    }

    public static void setNumberOfCars(int numberOfCars)
    {

        semaphoreCount = numberOfOutputs * inputToOutputCount;

        semaphores=new Semaphore[semaphoreCount];

        semaphores[0] = new Semaphore(numberOfCars);
        for (int i = 1; i< semaphoreCount; i++) {
            semaphores[i] = new Semaphore(0);
        }

        inBarriers =new CyclicBarrier[semaphoreCount];
        for (int i = 0; i< semaphoreCount; i++){
            inBarriers[i]=new CyclicBarrier(numberOfCars);
        };

        outBarriers =new CyclicBarrier[numberOfOutputs];

        for (int i = 0; i< numberOfOutputs; i++){
            outBarriers[i]=new CyclicBarrier(inputToOutputCount *numberOfCars);
        };

    }

    public static void redirectInputTo(int input,int output)
    {
        if(transitions==null)
            transitions=new HashMap<>();
        transitions.put(input,output);
    }

    public static void enter(Car car)
    {
        instance.private_enter(car);
    }

    public void private_enter(Car car)
    {
        Prints.reached(handle, car);
        acquire(car);
        Prints.exited(handle, car);
        waitAtInBarrier(car);
        release(car);
    }

    private void acquire(Car car)
    {
        try {
            semaphores[car.getStartDirection()].acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void release(Car car)
    {
        semaphores[next(car.getStartDirection())].release();
    }

    private void waitAtInBarrier(Car car)
    {

        try {
            inBarriers[car.getStartDirection()].await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    private void waitAtOutBarrier(Car car)
    {
        try {
            outBarriers[transitions.get(car.getStartDirection())].await();
        } catch (InterruptedException e) {

            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
