package physics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MassObjectTest {

	@Test
	public void test() {
		SpaceObject mo = new MassObject();
		assertTrue(mo.getName().contains("MassObject"));
		mo = new MassObject("Something new");
		assertEquals("Something new", mo.getName());
	}

	@Test
	public void testMass() throws Exception {
		SpaceObject mo = new MassObject();
		assertEquals(1, mo.getMass(), 0.05);
		SpaceObject m0 = new MassObject("Something", 10.0);
		assertEquals("Something", m0.getName());
		assertEquals(10.0, m0.getMass(), 0.05);
		SpaceObject m1 = new MassObject("Something Else", 15.0);
		assertEquals("Something Else", m1.getName());
		assertEquals(15.0, m1.getMass(), 0.05);
		assertEquals(1, new MassObject("Maybe black hole", -1).getMass(), 0.01);
	}

	@Test
	public void testPosition() throws Exception {
		SpaceObject m0 = new MassObject("Pointless name", 12.43);
		assertEquals(new Vector2D(), m0.getPosition());
		m0 = new MassObject("Pointless next", 83.3, new Vector2D(34, 22));
		assertEquals(new Vector2D(34, 22), m0.getPosition());
	}

	@Test
	public void testMovement() throws Exception {
		MassObject.setDeltaT(1);
		SpaceObject m0 = new MassObject();
		m0.move();
		assertEquals(new Vector2D(), m0.getPosition());
		m0 = new MassObject("Some name", 1, new Vector2D(), new Vector2D(1, 1));
		m0.move();
		assertEquals(new Vector2D(1, 1), m0.getPosition());

		MassObject.setDeltaT(0.1);
		m0.move();
		assertEquals(new Vector2D(1.1, 1.1), m0.getPosition());
		assertEquals(new Vector2D(1, 1), m0.getVelocity());
		SpaceObject m1 = new MassObject();
		assertEquals(new Vector2D(), m1.getVelocity());
	}

	@Test
	public void testApplyingForce() throws Exception {
		SpaceObject m0 = new MassObject();
		m0.applyForce(new Vector2D(2, 2));
		assertEquals(new Vector2D(0, 0), m0.getPosition());
		m0.move();
		assertEquals(new Vector2D(2, 2), m0.getPosition());
		m0.move();
		assertEquals(new Vector2D(6, 6), m0.getPosition());

		m0 = new MassObject("BlahBlah", 2);
		m0.applyForce(new Vector2D(0.1, 0.5));
		MassObject.setDeltaT(0.5);
		assertEquals(new Vector2D(0, 0), m0.getPosition());
		m0.move();
		assertEquals(new Vector2D(0.0125, 0.0625), m0.getPosition());
		MassObject.setDeltaT(2);
		m0.move();
		assertEquals(new Vector2D(0.2625, 1.3125), m0.getPosition());

		MassObject.setDeltaT(1);
	}

	@Test
	public void testCompareByMass() throws Exception {
		SpaceObject m0 = new MassObject("M1", 1);
		SpaceObject m1 = new MassObject("M2", 2);
		assertTrue(m1.compareTo(m0) < 0);

		m1 = new MassObject("M3", 0.1);
		assertTrue(m1.compareTo(m0) > 0);
		assertTrue(m1.compareTo(m1) == 0);
	}

	@Test
	public void testToString() throws Exception {
		SpaceObject m0 = new MassObject("Black hole", 100.0, new Vector2D(1, 1), new Vector2D(5, 5));
		m0.applyForce(new Vector2D(30, -20));
		assertEquals("[Black hole; mass: 100,0; pos: [1,00; 1,00]; v: [5,00; 5,00]; a: [0,30; -0,20]; F: [30,00; -20,00]]",
				m0.toString());
	}

	@Test
	public void testCopyMassObject() throws Exception {
		SpaceObject m0 = new MassObject("N1", 23.4, new Vector2D(1, 2), new Vector2D(4, 5));
		SpaceObject m1 = m0.copy();
		assertEquals("N1", m1.getName());
		assertEquals(23.4, m1.getMass(), 0.05);
		assertEquals(new Vector2D(1, 2), m1.getPosition());
		assertEquals(new Vector2D(4, 5), m1.getVelocity());
	}
}
