package physics;

public class MassObject implements Comparable<MassObject> {
	private static double deltaT = 1;
	private static int cnt = 0;

	private String name = "MassObject" + cnt++;
	private double mass = 1;

	private Vector2D force = new Vector2D();
	private Vector2D acceleration = new Vector2D();
	private Vector2D velocity = new Vector2D();
	private Vector2D position = new Vector2D();

	public MassObject() {
	}

	public MassObject(String name) {
		this.name = name;
	}

	public MassObject(String name, double mass) {
		this.name = name;
		setMass(mass);
	}

	public MassObject(String name, double mass, Vector2D position) {
		this.name = name;
		setMass(mass);
		this.position = position;
	}

	public MassObject(String name, double mass, Vector2D position, Vector2D velocity) {
		this.name = name;
		setMass(mass);
		this.position = position;
		this.velocity = velocity;
	}

	private void setMass(double mass) {
		this.mass = (mass > 0) ? mass : 1;
	}

	public String getName() {
		return name;
	}

	public double getMass() {
		return mass;
	}

	public Vector2D getPosition() {
		return position;
	}

	public void move() {
		velocity = velocity.add(acceleration.mul(deltaT));
		position = position.add(velocity.mul(deltaT));
	}

	public static void setDeltaT(double dt) {
		deltaT = dt;
	}

	public Vector2D getVelocity() {
		return velocity;
	}

	public void applyForce(Vector2D force) {
		this.force = force;
		this.acceleration = this.force.norm().mul(force.mag() / mass);
	}

	@Override
	public int compareTo(MassObject m0) {
		double dm = this.mass - m0.mass;
		return (dm < 0) ? 1 : ((dm > 0) ? -1 : 0);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[").append(name).append("; ");
		sb.append("mass: ").append(String.format("%.1f", mass)).append("; ");
		sb.append("pos: ").append(position).append("; ");
		sb.append("v: ").append(velocity).append("; ");
		sb.append("a: ").append(acceleration).append("; ");
		sb.append("F: ").append(force).append("]");
		return sb.toString();
	}

	public MassObject copy() {
		MassObject result = new MassObject(name, mass, position, velocity);
		result.acceleration = acceleration;
		result.force = force;
		return result;
	}
}
