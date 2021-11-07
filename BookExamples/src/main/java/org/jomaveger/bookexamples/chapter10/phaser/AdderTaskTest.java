package org.jomaveger.bookexamples.chapter10.phaser;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Phaser;

public class AdderTaskTest {
	
    public static void main(String[] args) {
    	
        final int PHASE_COUNT = 2;
        Phaser phaser
            = new Phaser() {
                public boolean onAdvance(int phase, int parties) {  
                    System.out.println("Phase:" + phase
                        + ", Parties:" + parties
                        + ", Arrived:" + this.getArrivedParties());
                    
                    boolean terminatePhaser = false;

                    // Termina el divisor si es la fase PHASE_COUNT
                    // o si el numero de participantes es cero  
                    if (phase >= PHASE_COUNT - 1 || parties == 0) {
                        terminatePhaser = true;
                    }

                    return terminatePhaser;
                }
            };

        // Usamos una lista sincronizada  
        List<Integer> list = Collections.synchronizedList(new ArrayList<Integer>());

        // Usamos 3 tareas sumadoras para generar numeros aleatorios 
        final int ADDER_COUNT = 3;

        // Registramos 1 participante extra ademas de las 3 tareas sumadoras. 
        // El participante extra sincronizara el calculo de la suma de 
        // todos los enteros generados por todas las tareas sumadoras  
        phaser.bulkRegister(ADDER_COUNT + 1);

        for (int i = 1; i <= ADDER_COUNT; i++) {
            // Crear la tarea y empezarla  
            String taskName = "Task #" + i;
            AdderTask task = new AdderTask(taskName, phaser, list);
            task.start();
        }

        // Esperamos que termine el divisor, de modo que podamos calcular
        // la suma de todos los numeros enteros generados
        while (!phaser.isTerminated()) {
            phaser.arriveAndAwaitAdvance();
        }

        // Divisor ha terminado ahora. Calculamos la suma.  
        int sum = 0;
        for (Integer num : list) {
            sum = sum + num;
        }

        System.out.println("Sum = " + sum);
    }
}
