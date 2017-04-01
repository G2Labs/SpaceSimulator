package spaceSim;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

import physics.MassObject;
import physics.SpaceObject;
import physics.Universe;
import physics.Vector2D;

public class Simulator extends Thread {
	private Universe world;
	private final BlockingQueue<Message> queue;
	private final Random R = new Random();
	private boolean canRun = true;

	public Simulator(BlockingQueue<Message> queue) {
		this.queue = queue;
	}

	public void terminate() {
		canRun = false;
	}

	@Override
	public void run() {
		world = new Universe();

		world.add(new MassObject("Sun", 80, new Vector2D(500, 400)));
		for (int i = 0; i < 499; i++)
			world.add(generateRandomObject(i));

		MassObject.setDeltaT(0.1);

		try {
			while (canRun) {
				Thread.sleep(100);
				oneIteration();
				queue.put(new Message(world));
			}
		} catch (InterruptedException e) {
		}
	}

	private SpaceObject generateRandomObject(int i) {
		return new MassObject("Mover" + i, R.nextDouble() * 5 + 1,
				new Vector2D(R.nextDouble() * 1000, R.nextDouble() * 800));
	}

	private void oneIteration() {
		world.applyGravity();
		world.move();
		world = world.copy();
	}
}
