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

}
