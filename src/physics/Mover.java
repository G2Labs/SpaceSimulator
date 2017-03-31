package physics;

public class Mover {
	private static double deltaT = 0.01;
	private Vector2D position = new Vector2D();
	private Vector2D velocit = new Vector2D();
	private Vector2D force = new Vector2D();
	private double mass;
	private String name;

	public Mover(String name, double mass) {
		this.name = name;
		this.mass = mass;
	}

	public Mover(String name, double mass, Vector2D position) {
		this.name = name;
		this.mass = mass;
		this.position = position;
	}

	public void applyForce(Vector2D externalForce) {
		this.force = externalForce;
	}

	public Mover move() {
		double fMag = force.mag();
		Vector2D a = force.normalize().scale(fMag / mass);
		Vector2D v = velocit.add(a.scale(deltaT));
		Vector2D p = position.add(v.scale(deltaT));

		Mover newMover = new Mover(this.name, this.mass, this.position);
		newMover.force = force;
		newMover.velocit = v;
		newMover.position = p;
		return newMover;
	}

	public Vector2D getPosition() {
		return position;
	}

	public double getMass() {
		return mass;
	}

	public static void setDeltaT(double newVal) {
		deltaT = newVal;
	}
}
