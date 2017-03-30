package physics;

public class Vector2D {
	private double x, y, size;

	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
		this.size = Math.sqrt(x * x + y * y);
	}

	public Vector2D() {
		this.x = 0;
		this.y = 0;
	}

	public double mag() {
		return size;
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

	public Vector2D mult(double n) {
		return new Vector2D(this.x * n, this.y * n);
	}

	public Vector2D normalize() {
		return new Vector2D(this.x / size, this.y / size);
	}
}
