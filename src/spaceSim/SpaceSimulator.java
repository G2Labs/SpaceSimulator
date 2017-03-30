package spaceSim;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SpaceSimulator {
	public static void main(String[] args) {	
		BlockingQueue<Integer>queue = new ArrayBlockingQueue<>(10);
		Simulator simulator = new Simulator(queue);
		
		Thread displayThread = new Thread(){
			public void run(){
				Display display = new Display();
				display.setVisible(true);			
				while(true){
					// TODO przekazywanie œwiata do narysowania
				}
			}
		};	
		displayThread.start();
		simulator.start();
	}
}
