package physics;

public class MassObject implements SpaceObject {
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

	@Override
	public String getName() {
		return name;
	}

	@Override
	public double getMass() {
		return mass;
	}

	@Override
	public Vector2D getPosition() {
		return position;
	}

	@Override
	public void move() {
		velocity = velocity.add(acceleration.mul(deltaT));
		position = position.add(velocity.mul(deltaT));
	}

	public static void setDeltaT(double dt) {
		deltaT = dt;
	}

	@Override
	public Vector2D getVelocity() {
		return velocity;
	}

	@Override
	public void applyForce(Vector2D force) {
		this.force = force;
		this.acceleration = this.force.norm().mul(force.mag() / mass);
	}

	@Override
	public int compareTo(SpaceObject m0) {
		double dm = this.mass - m0.getMass();
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

	@Override
	public SpaceObject copy() {
		MassObject result = new MassObject(name, mass, position, velocity);
		result.acceleration = acceleration;
		result.force = force;
		return result;
	}

	@Override
	public SpaceObject collideWith(SpaceObject m1) {
		String m2N = this.getName() + "." + m1.getName();
		double m2M = this.getMass() + m1.getMass();

		Vector2D m2V = this.getVelocity().mul(this.getMass() / m2M);
		m2V = m2V.add(m1.getVelocity().mul(m1.getMass() / m2M));

		Vector2D m2P = m1.getPosition().sub(this.getPosition());
		m2P = m2P.mul(m1.getMass() / m2M);
		m2P = m2P.add(this.getPosition());

		return new MassObject(m2N, m2M, m2P, m2V);
	}
}
