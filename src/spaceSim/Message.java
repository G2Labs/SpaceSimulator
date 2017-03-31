package spaceSim;

import java.util.List;

import physics.Mover;

public class Message {
	private List<Mover> listOfDots;

	public Message(List<Mover> list) {
		listOfDots = list;
	}

	public List<Mover> getData() {
		return listOfDots;
	}
}
