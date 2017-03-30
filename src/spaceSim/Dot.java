package spaceSim;

import java.awt.Color;

public class Dot {
	private Double x, y, radius;
	private Color color;

	public Dot(Double x, Double y, Double radius, Color color) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.color = color;
	}

	public Double getX() {
		return x;
	}

	public Double getY() {
		return y;
	}

	public Double getRadius() {
		return radius;
	}

	public Color getColor() {
		return color;
	}
}
