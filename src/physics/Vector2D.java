package physics;

public class Vector2D {
	private double x, y;

	public Vector2D() {
		this.x = 0;
		this.y = 0;
	}

	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double mag() {
		return Math.sqrt(x * x + y * y);
	}

	public Vector2D add(Vector2D v2) {
		return new Vector2D(this.x + v2.x, this.y + v2.y);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Vector2D sub(Vector2D v2) {
		return new Vector2D(this.x - v2.x, this.y - v2.y);
	}

	public Vector2D mul(double n) {
		return new Vector2D(this.x * n, this.y * n);
	}

	public Vector2D norm() {
		double size = mag();
		if (size > 0)
			return new Vector2D(this.x / size, this.y / size);
		else
			return new Vector2D();
	}

	@Override
	public boolean equals(Object vec) {
		Vector2D v = (Vector2D) vec;

		boolean xEqual = Math.abs(this.x - v.x) < 0.001;
		boolean yEqual = Math.abs(this.y - v.y) < 0.001;
		return (xEqual && yEqual);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[").append(String.format("%.3f", this.x)).append("; ");
		sb.append(String.format("%.3f", this.y)).append("]");

		return sb.toString();
	}

	public Vector2D turn90Degree() {
		return new Vector2D(getY(), -getX());
	}
}
