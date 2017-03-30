package spaceSim;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class DisplayThread extends Thread {
	private BlockingQueue<List<Dot>> queue;
	private Display display;

	public DisplayThread(BlockingQueue<List<Dot>> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		display = new Display();
		try {
			while (true) {
				display.getData(queue.take());
			}
		} catch (InterruptedException e) {
		}
	}
}
