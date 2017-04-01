package physics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UniverseTest {

	@Test
	public void testForSize() {
		Universe universe = new Universe();
		assertEquals(0, universe.size());

		universe.add(new MassObject());
		assertEquals(1, universe.size());

		universe.add(new MassObject());
		assertEquals(2, universe.size());
	}

	@Test
	public void testForListProperties() throws Exception {
		String[] names = { "Object1", "Something3", "BlackHoleSunX", "TheSun" };
		double[] mass = { 1.0, 3.14, 1000.0, 83.2 };
		Universe universe = new Universe();
		for (int i = 0; i < names.length; i++) {
			universe.add(new MassObject(names[i], mass[i]));
		}
		assertEquals(names.length, universe.size());

		int i = 0;
		for (SpaceObject so : universe) {
			assertEquals(names[i], so.getName());
			assertEquals(mass[i], so.getMass(), 0.05);
			i++;
		}
	}

	@Test
	public void testMoveSimulation() throws Exception {
		Universe u = new Universe();
		for (int i = 0; i < 100; i++)
			u.add(new MassObject("M" + i, 1, new Vector2D(i, i), new Vector2D(1, 1)));

		for (int i = 0; i < 100; i++)
			assertEquals(new Vector2D(i, i), u.get(i).getPosition());

		MassObject.setDeltaT(1);
		u.move();

		for (int i = 0; i < 100; i++)
			assertEquals(new Vector2D(i + 1, i + 1), u.get(i).getPosition());

		Universe newOne = u.copy();
		u.move();

		for (int i = 0; i < 100; i++) {
			assertEquals(new Vector2D(i + 1, i + 1), newOne.get(i).getPosition());
			assertEquals(new Vector2D(i + 2, i + 2), u.get(i).getPosition());
		}
	}

	@Test
	public void testForGravity() throws Exception {
		MassObject.setDeltaT(1);

		Universe u = new Universe();
		u.add(new MassObject("Sun", 40));
		u.add(new MassObject("Planetoid", 2, new Vector2D(10, 0)));

		u.applyGravity();
		u.move();

		assertEquals(new Vector2D(0.02, 0), u.get(0).getPosition());
		assertEquals(new Vector2D(9.6, 0), u.get(1).getPosition());
	}
}
