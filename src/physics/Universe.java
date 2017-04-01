package physics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Universe implements Iterable<SpaceObject> {
	private List<SpaceObject> objects = new ArrayList<>();

	public Universe() {

	}

	public int size() {
		return objects.size();
	}

	public void add(SpaceObject spaceObject) {
		objects.add(spaceObject);
	}

	@Override
	public Iterator<SpaceObject> iterator() {
		return objects.iterator();
	}

}
