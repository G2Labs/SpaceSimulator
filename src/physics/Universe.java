package physics;

import java.util.ArrayList;

public class Universe extends ArrayList<SpaceObject> {
	private static final double MIN_DISTANCE = 3;

	public void move() {
		for (SpaceObject so : this)
			so.move();
	}

	public Universe copy() {
		Universe newUniverse = new Universe();
		for (SpaceObject so : this)
			newUniverse.add(so.copy());
		return newUniverse;
	}

	public void applyGravity() {
		for (int i = 0; i < size(); i++) {
			Vector2D gravity = new Vector2D();
			SpaceObject s0 = get(i);

			for (int j = 0; j < size(); j++) {
				if (i == j)
					continue;
				SpaceObject s1 = get(j);

				gravity = gravity.add(calcGravityBetween(s0, s1));
			}
			s0.applyForce(gravity);
		}
	}

	private Vector2D calcGravityBetween(SpaceObject s0, SpaceObject s1) {
		Vector2D gravPart = s1.getPosition().sub(s0.getPosition());
		double dist = gravPart.mag();

		double gravMag = 1 * (s0.getMass() * s1.getMass()) / (dist * dist);
		gravPart = gravPart.norm().mul(gravMag);
		return gravPart;
	}

	public void applyCollisions() {
		boolean somethingHasCollided;
		do {
			this.sort(null);
			somethingHasCollided = false;
			for (int i = 0; i < size(); i++) {
				for (int j = 0; j < size(); j++) {
					if (i == j)
						continue;
					SpaceObject s0 = get(i);
					SpaceObject s1 = get(j);
					if (areTooClose(s0, s1)) {
						somethingHasCollided = true;
						collideThem(s0, s1);
					}
				}
			}
		} while (somethingHasCollided);
	}

	private boolean areTooClose(SpaceObject s0, SpaceObject s1) {
		return s0.getPosition().sub(s1.getPosition()).mag() < MIN_DISTANCE;
	}

	private void collideThem(SpaceObject s0, SpaceObject s1) {
		add(s0.collideWith(s1));
		remove(s0);
		remove(s1);
	}
}
