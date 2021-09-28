package org.jomaveger.concurrent.locks;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class DeadlockDetectingLockTest {

    private static Lock a = new DeadlockDetectingLock(false, true);
    private static Lock b = new DeadlockDetectingLock(false, true);
    private static Lock c = new DeadlockDetectingLock(false, true);
    private static Condition wa = a.newCondition();
    private static Condition wb = b.newCondition();
    private static Condition wc = c.newCondition();

    private static void delaySeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ex) {
        }
    }

    private static void awaitSeconds(Condition c, int seconds) {
        try {
            c.await(seconds, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
        }
    }

    private static void testOne() {
        new Thread(() -> {
            System.out.println("thread one grab a");
            a.lock();
            delaySeconds(2);
            System.out.println("thread one grab b");
            b.lock();
            delaySeconds(2);
            a.unlock();
            b.unlock();
        }).start();

        new Thread(() -> {
            System.out.println("thread two grab b");
            b.lock();
            delaySeconds(2);
            System.out.println("thread two grab a");
            a.lock();
            delaySeconds(2);
            a.unlock();
            b.unlock();
        }).start();
    }

    private static void testTwo() {
        new Thread(() -> {
            System.out.println("thread one grab a");
            a.lock();
            delaySeconds(2);
            System.out.println("thread one grab b");
            b.lock();
            delaySeconds(10);
            a.unlock();
            b.unlock();
        }).start();

        new Thread(() -> {
            System.out.println("thread two grab b");
            b.lock();
            delaySeconds(2);
            System.out.println("thread two grab c");
            c.lock();
            delaySeconds(10);
            b.unlock();
            c.unlock();
        }).start();

        new Thread(() -> {
            System.out.println("thread three grab c");
            c.lock();
            delaySeconds(4);
            System.out.println("thread three grab a");
            a.lock();
            delaySeconds(10);
            c.unlock();
            a.unlock();
        }).start();
    }

    private static void testThree() {
        new Thread(() -> {
            System.out.println("thread one grab b");
            b.lock();
            System.out.println("thread one grab a");
            a.lock();
            delaySeconds(2);
            System.out.println("thread one waits on b");
            awaitSeconds(wb, 10);
            a.unlock();
            b.unlock();
        }).start();

        new Thread(() -> {
            delaySeconds(1);
            System.out.println("thread two grab b");
            b.lock();
            System.out.println("thread two grab a");
            a.lock();
            delaySeconds(10);
            b.unlock();
            c.unlock();
        }).start();

    }

    public static void main(String args[]) {
        Random r = new Random();
        int nextInt = r.nextInt(3);
        switch (nextInt) {
            case 0:
                System.out.println("Executing testOne");
                testOne();
                break;
            case 1:
                System.out.println("Executing testTwo");
                testTwo();
                break;
            case 2:
                System.out.println("Executing testThree");
                testThree();
                break;
            default:
                break;
        }
        delaySeconds(60);
        System.out.println("--- End Program ---");
        System.exit(0);
    }
}
