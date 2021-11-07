package org.jomaveger.bookexamples.chapter10.phaser;

import java.util.concurrent.Phaser;

public class StartTogetherTaskTest {

	public static void main(String[] args) {

		// Comienza con 1 participante controlador registrado
		Phaser phaser = new Phaser(1);

		// Empezamos 3 tareas
		final int TASK_COUNT = 3;
		for (int i = 1; i <= TASK_COUNT; i++) {
			// Registramos un nuevo participante para cada tarea
			phaser.register();

			// Ahora creamos la tarea y la comenzamos
			String taskName = "Task #" + i;
			StartTogetherTask task = new StartTogetherTask(taskName, phaser);
			task.start();
		}

		// Ahora, borramos del registro el controlador, de modo que
		// el resto de tareas puedan avanzar
		phaser.arriveAndDeregister();
	}
}