package spaceSim;

import java.util.List;

import physics.SpaceObject;

public class Message {
	private List<SpaceObject> listOfDots;

	public Message(List<SpaceObject> list) {
		listOfDots = list;
	}

	public List<SpaceObject> getData() {
		return listOfDots;
	}
}
