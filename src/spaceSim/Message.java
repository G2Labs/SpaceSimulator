package spaceSim;

import java.util.List;

import physics.MassObject;

public class Message {
	private List<MassObject> listOfDots;

	public Message(List<MassObject> list) {
		listOfDots = list;
	}

	public List<MassObject> getData() {
		return listOfDots;
	}
}
