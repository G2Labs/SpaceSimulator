package spaceSim;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import physics.SpaceObject;

public class SpaceSimulator {
	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<List<SpaceObject>> queue = new ArrayBlockingQueue<>(10);

		Simulator simulator = new Simulator(queue);
		DisplayThread displayThread = new DisplayThread(queue);

		simulator.start();
		displayThread.start();

		displayThread.join();
		simulator.terminate();
		simulator.join();
	}
}
