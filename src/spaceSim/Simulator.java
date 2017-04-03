package spaceSim;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

import physics.MassObject;
import physics.SpaceObject;
import physics.Universe;
import physics.Vector2D;

public class Simulator extends Thread {
	private static final Random R = new Random();
	private static final int MAX_PLANET_COUNT = 100;
	private static final int MIN_RADIUS = 100;
	private static final int MAX_RADIUS = 1000;
	private static final double MIN_MASS = 0.1;
	private static final double MAX_MASS = 0.5;
	private static final double SUN_MASS = 100;
	private static final double DELTA_T = 0.5;
	private Universe world;
	private final BlockingQueue<List<SpaceObject>> queue;
	private boolean canRun = true;

	public Simulator(BlockingQueue<List<SpaceObject>> queue) {
		this.queue = queue;
	}

	public void terminate() {
		canRun = false;
	}

	@Override
	public void run() {
		MassObject.setDeltaT(DELTA_T);
		world = new Universe();
		generateSolarSystem();

		try {
			while (canRun) {
				Thread.sleep(10);
				oneIteration();
				queue.put(world.copy());
			}
		} catch (InterruptedException e) {
		}
	}

	private void generateSolarSystem() {
		generateSun();
		for (int i = 0; i < MAX_PLANET_COUNT; i++)
			world.add(generateRandomObject("Planet" + i));
	}

	private void generateSun() {
		world.add(new MassObject("Sun", SUN_MASS, new Vector2D()));
	}

	private SpaceObject generateRandomObject(String name) {
		double mass = R.nextDouble() * (MAX_MASS - MIN_MASS) + MIN_MASS;

		double radius = R.nextDouble() * (MAX_RADIUS - MIN_RADIUS) + MIN_RADIUS;
		double phi = R.nextDouble() * 359.9;

		double x = Math.cos(Math.toRadians(phi)) * radius;
		double y = Math.sin(Math.toRadians(phi)) * radius;

		Vector2D pos = new Vector2D(x, y);
		Vector2D vel = pos.turn90Degree().norm();
		vel = vel.mul(Math.sqrt(SUN_MASS / radius)); // first space speed

		return new MassObject(name, mass, pos, vel);
	}

	private void oneIteration() {
		world.applyCollisions();
		world.applyGravity();
		world.move();
	}
}
