package org.jomaveger.bookexamples.chapter10.phaser;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Phaser;

public class AdderTask extends Thread {
	
    private Phaser phaser;
    private String taskName;
    private List<Integer> list;
    private static Random rand = new Random();

    public AdderTask(String taskName, Phaser phaser, List<Integer> list) {
        this.taskName = taskName;
        this.phaser = phaser;
        this.list = list;
    }

    @Override
    public void run() {
        do {
            // Genera un numero aleatorio entre 1 y 10  
            int num = rand.nextInt(10) + 1;

            System.out.println(taskName + " added " + num);

            // Pon el entero en la lista  
            list.add(num);

            // Finaliza la fase y espera a los demas participantes  
            phaser.arriveAndAwaitAdvance();
        } 
        while (!phaser.isTerminated());
    }
}