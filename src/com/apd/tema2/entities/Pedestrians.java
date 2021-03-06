package com.apd.tema2.entities;

import com.apd.tema2.utils.Constants;

import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;

/**
 * Clasa utilizata pentru gestionarea oamenilor care se strang la trecerea de pietoni.
 */
public class Pedestrians implements Runnable {
    private int pedestriansNo = 0;
    private int maxPedestriansNo;
    private AtomicBoolean pass = new AtomicBoolean(false);
    private AtomicBoolean finished = new AtomicBoolean(false);
    private int executeTime;
    private long startTime;

    public Pedestrians(int executeTime, int maxPedestriansNo) {
        this.startTime = System.currentTimeMillis();
        this.executeTime = executeTime;
        this.maxPedestriansNo = maxPedestriansNo;
    }

    @Override
    public void run() {

        CrosswalkPrinter.acquire();

        while(System.currentTimeMillis() - startTime < executeTime) {
            try {
                pedestriansNo++;
                sleep(Constants.PEDESTRIAN_COUNTER_TIME);

                if(pedestriansNo == maxPedestriansNo) {
                    pedestriansNo = 0;
                    pass.set(true);
                    sleep(Constants.PEDESTRIAN_PASSING_TIME);
                    pass.set(false);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        finished.set(true);
    }

    public boolean isPass() {
        return pass.get();
    }

    public boolean isFinished() {
        return finished.get();
    }
}
