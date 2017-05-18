package com.tj.chaersi.rxjavademo;

/**
 * Created by Chaersi on 17/3/3.
 * 无意间写了一个面试题，然后就看了下cpu思密达
 */
public class Test {

    public static void main(String[] args) {
        long startTime = 0;
        int busyTime = 10;
        int idleTime = 10;
        while (true) {
            startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime <= busyTime)
                ;
            try {
                Thread.sleep(idleTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
