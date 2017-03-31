package physics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MassObjectTest {

	@Test
	public void test() {
		MassObject mo = new MassObject();
		assertEquals("MassObject0", mo.getName());
		MassObject m1 = new MassObject();
		assertEquals("MassObject1", m1.getName());
		assertEquals("MassObject1", m1.getName());
		MassObject m2 = new MassObject("Some object");
		assertEquals("Some object", m2.getName());
		MassObject m3 = new MassObject();
		assertEquals("MassObject3", m3.getName());
	}

	@Test
	public void testMass() throws Exception {
		MassObject m0 = new MassObject("Something", 10.0);
		assertEquals("Something", m0.getName());
		assertEquals(10.0, m0.getMass(), 0.05);
		MassObject m1 = new MassObject("Something Else", 15.0);
		assertEquals("Something Else", m1.getName());
		assertEquals(15.0, m1.getMass(), 0.05);
	}

	@Test
	public void testPosition() throws Exception {
		MassObject m0 = new MassObject("Pointless name", 12.43);
		assertEquals(new Vector2D(), m0.getPosition());
	}

}
