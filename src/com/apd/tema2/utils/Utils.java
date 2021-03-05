package com.apd.tema2.utils;

import com.apd.tema2.entities.Pedestrians;

public class Utils {
    public static void safeSleep(int millis)
    {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
