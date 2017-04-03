package spaceSim;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import physics.SpaceObject;

public class DisplayThread extends Thread {
	private BlockingQueue<List<SpaceObject>> queue;
	private Display display;

	public DisplayThread(BlockingQueue<List<SpaceObject>> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		display = new Display();
		try {
			while (true) {
				display.insertNewData(queue.take());
			}
		} catch (InterruptedException e) {
		}
	}
}
