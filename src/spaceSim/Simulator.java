package spaceSim;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

import physics.MassObject;
import physics.SpaceObject;
import physics.Vector2D;

public class Simulator extends Thread {
	private ArrayList<SpaceObject> world;
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
		world = new ArrayList<>();
		world.add(new MassObject("Sun", 60, new Vector2D(500, 400)));
		for (int i = 0; i < 499; i++) {
			world.add(new MassObject("Mover" + i, R.nextDouble() * 5 + 1,
					new Vector2D(R.nextDouble() * 1000, R.nextDouble() * 800)));
		}
		MassObject.setDeltaT(0.1);
		try {
			while (canRun) {
				Thread.sleep(2);
				oneIteration();
				queue.put(new Message(world));
			}
		} catch (InterruptedException e) {
		}
	}

	private void oneIteration() {
		ArrayList<SpaceObject> newWorld = new ArrayList<>();
		newWorld.add(world.get(0));
		for (int i = 1; i < world.size(); i++) {
			SpaceObject m1 = world.get(i);
			Vector2D gravity = new Vector2D();

			for (int j = 0; j < world.size(); j++) {
				if (i == j)
					continue;
				SpaceObject m2 = world.get(j);

				Vector2D gravityPortion = m2.getPosition().sub(m1.getPosition());
				double dist = gravityPortion.mag();
				if (dist < 0.01)
					continue;

				double gravityMag = 1 * m1.getMass() * m2.getMass() / (dist * dist);
				gravity = gravity.add(gravityPortion.norm().mul(gravityMag));
			}
			m1.applyForce(gravity);
			m1.move();
			newWorld.add(m1.copy());
		}
		world = newWorld;
	}
}
