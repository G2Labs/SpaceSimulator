package spaceSim;

import java.util.concurrent.BlockingQueue;

public class Simulator extends Thread {
	private BlockingQueue<Integer> queue;

	public Simulator(BlockingQueue<Integer> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			while (true) {
				Thread.sleep(1000);
				// TODO napisanie ca³ego symulowania i wstawiania w kolejkê
			}
		} catch (InterruptedException e) {
		}
	}
}
