package spaceSim;

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
	public void terminate(){
		canRun=false;
	}

	@Override
	public void run() {
		try {
			while (canRun) {
				Thread.sleep(1000);
				// TODO napisanie ca³ego symulowania i wstawiania w kolejkê
				dots=new ArrayList<>();
				for(int i = 0; i<10;i++){
					double x = R.nextDouble()*10 + 5;
					double y=R.nextDouble()*10 + 5;
					double r=R.nextDouble()*0.5 + 0.5;
					dots.add(new Dot(x, y, r));
				}
				queue.put(new Message(dots));
			}
		} catch (InterruptedException e) {
		}
	}
}
