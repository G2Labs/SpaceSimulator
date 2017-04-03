package physics;

public interface SpaceObject extends Comparable<SpaceObject> {
	public String getName();

	public double getMass();

	public Vector2D getPosition();

	public Vector2D getVelocity();

	public void applyForce(Vector2D force);

	public void move();

	public SpaceObject copy();

	public SpaceObject collideWith(SpaceObject so1);
}
