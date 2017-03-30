package spaceSim;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Simulator extends Thread {
	private final BlockingQueue<Message> queue;
	private final Random R = new Random();
	ArrayList<Dot> dots;
	private boolean canRun = true;

	public Simulator(BlockingQueue<Message> queue) {
		this.queue = queue;
	}

	public void terminate() {
		canRun = false;
	}

	@Override
	public void run() {
		try {
			while (canRun) {
				Thread.sleep(200);
				// TODO napisanie ca³ego symulowania i wstawiania w kolejkê
				dots = new ArrayList<>();
				for (int i = 0; i < 100; i++) {
					double x = R.nextDouble() * 1000;
					double y = R.nextDouble() * 800;
					double r = R.nextDouble() * 5;
					Color c = new Color(R.nextInt(256), R.nextInt(256), R.nextInt(256));
					dots.add(new Dot(x, y, r, c));
				}
				queue.put(new Message(dots));
			}
		} catch (InterruptedException e) {
		}
	}
}
