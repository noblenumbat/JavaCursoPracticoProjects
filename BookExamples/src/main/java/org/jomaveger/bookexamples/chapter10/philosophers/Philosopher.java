package org.jomaveger.bookexamples.chapter10.philosophers;

import java.util.concurrent.Semaphore;

public class Philosopher extends Thread {
    
    private static Semaphore mutex = new Semaphore(1);
    public static Philosopher[] philosophers;

    private enum State {
        THINKING, HUNGRY, EATING
    };

    private final int id;
    private State state;
    private final Semaphore self;

    public Philosopher(int id) {
        this.id = id;
        self = new Semaphore(0);
        state = State.THINKING;
    }

    private Philosopher left() {
        return philosophers[id == 0 ? philosophers.length - 1 : id - 1];
    }

    private Philosopher right() {
        return philosophers[(id + 1) % philosophers.length];
    }

    public void run() {
        try {
            while (true) {
                printState();
                switch (state) {
                    case THINKING:
                        thinkOrEat();
                        mutex.acquire();
                        state = State.HUNGRY;
                        break;
                    case HUNGRY:                        
                        test(this);
                        mutex.release();
                        self.acquire();
                        state = State.EATING;
                        break;
                    case EATING:
                        thinkOrEat();
                        mutex.acquire();
                        state = State.THINKING;
                        test(left());
                        test(right());
                        mutex.release();
                        break;
                }
            }
        } catch (InterruptedException e) {
        }
    }

    private static void test(Philosopher p) {
        if (p.left().state != State.EATING 
                && p.state == State.HUNGRY
                && p.right().state != State.EATING) {
            p.state = State.EATING;
            p.self.release();
        }
    }

    private void thinkOrEat() {
        try {
            Thread.sleep((long) Math.round(Math.random() * 5000));
        } catch (InterruptedException e) {
        }
    }

    private void printState() {
        System.out.println("Philosopher " + id + " is " + state);
    }
}
