package org.jomaveger.bookexamples.chapter10.phaser;

import java.util.concurrent.Phaser;

public class StartTogetherTaskTest2 {

	public static void main(String[] args) {

		// Comienza con 0 participantes registrados
		Phaser phaser = new Phaser();

		// Empezamos 3 tareas
		final int TASK_COUNT = 3;
				
		//iniciamos 3 tareas en una pasada
		phaser.bulkRegister(TASK_COUNT);
				
		for (int i = 1; i <= TASK_COUNT; i++) {
			// Ahora creamos la tarea y la comenzamos
			// en cada iteracion
			String taskName = "Task #" + i;
			StartTogetherTask task = new StartTogetherTask(taskName, phaser);
			task.start();
		}
	}
}
