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
	private static final Random R = new Random();
	private static final int MIN_RADIUS = 100;
	private static final int MAX_RADIUS = 400;
	private static final double MIN_MASS = 0.1;
	private static final double MAX_MASS = 1;
	private static final double SUN_MASS = 80;
	private static final int MAX_PLANET_COUNT = 400;
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

		world.add(new MassObject("Sun", SUN_MASS, new Vector2D()));
		for (int i = 0; i < MAX_PLANET_COUNT; i++)
			world.add(generateRandomObject(i));

		MassObject.setDeltaT(0.5);

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
		double m = R.nextDouble() * (MAX_MASS - MIN_MASS) + MIN_MASS;

		double r = R.nextDouble() * (MAX_RADIUS - MIN_RADIUS) + MIN_RADIUS;
		double fi = R.nextDouble() * 359.9;

		double x = Math.cos(Math.toRadians(fi)) * r;
		double y = Math.sin(Math.toRadians(fi)) * r;

		Vector2D pos = new Vector2D(x, y);
		Vector2D vel = pos.turn90Degree().norm();
		vel = vel.mul(Math.sqrt(SUN_MASS / r));

		return new MassObject("Planet" + i, m, pos, vel);
	}

	private void oneIteration() {
		world.applyCollisions();
		world.applyGravity();
		world.move();
		world = world.copy();
	}
}
