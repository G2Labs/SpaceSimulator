package physics;

public class MassObject {
	private static int cnt = 0;
	private double mass;
	private String name;

	public MassObject() {
		this.name = "MassObject" + cnt;
		cnt++;
	}

	public MassObject(String name) {
		this.name = name;
		cnt++;
	}

	public MassObject(String name, double mass) {
		this.name = name;
		this.mass = mass;
	}

	public Object getName() {
		return name;
	}

	public double getMass() {
		return mass;
	}

	public Object getPosition() {
		return new Vector2D();
	}

}
