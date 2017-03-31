package spaceSim;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

import physics.Mover;
import physics.Vector2D;

public class Simulator extends Thread {
	private ArrayList<Mover> world;
	private final BlockingQueue<Message> queue;
	private final Random R = new Random();
	private boolean canRun = true;
	private Vector2D force = new Vector2D(1, 1);

	public Simulator(BlockingQueue<Message> queue) {
		this.queue = queue;
	}

	public void terminate() {
		canRun = false;
	}

	@Override
	public void run() {
		world = new ArrayList<>();
		world.add(new Mover("Sun", 50, new Vector2D(500, 400)));
		for (int i = 0; i < 99; i++) {
			world.add(
					new Mover("Mover" + i, R.nextDouble() * 5 + 1, new Vector2D(R.nextDouble() * 1000, R.nextDouble() * 800)));
		}

		try {
			while (canRun) {
				Thread.sleep(50);
				oneIteration();
				queue.put(new Message(world));
			}
		} catch (InterruptedException e) {
		}
	}

	private void oneIteration() {
		ArrayList<Mover> newWorld = new ArrayList<>();

		for (int i = 0; i < world.size(); i++) {
			Mover m1 = world.get(i);
			Vector2D gravity = new Vector2D();

			for (int j = 0; j < world.size(); j++) {
				if (i == j)
					continue;
				Mover m2 = world.get(j);

				Vector2D gravityPortion = m2.getPosition().sub(m1.getPosition());
				double dist = gravityPortion.mag();
				if (dist < 0.01)
					continue;
				double gravityMag = 0.5 * m1.getMass() * m2.getMass() / (dist * dist);
				gravity = gravity.add(gravityPortion.normalize().scale(gravityMag));
			}
			m1.applyForce(gravity);
			newWorld.add(m1.move());
		}
		world = newWorld;
	}
}
