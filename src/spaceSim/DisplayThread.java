package spaceSim;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class DisplayThread extends Thread {
	private BlockingQueue<Message> queue;
	private Display display;

	public DisplayThread(BlockingQueue<Message> queue) {
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
