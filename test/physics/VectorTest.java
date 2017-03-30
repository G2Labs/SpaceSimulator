package physics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VectorTest {

	@Test
	public void test() {
		Vector2D vector = new Vector2D(0.0, 0.0);
		assertEquals(0, vector.mag(), 0.05);
	}

	@Test
	public void testSomeValues() throws Exception {
		Vector2D v = new Vector2D(1, 1);
		assertEquals(1.41, v.mag(), 0.05);
	}

	@Test
	public void testEmpty() throws Exception {
		Vector2D v = new Vector2D();
		assertEquals(0, v.mag(), 0.05);
	}

	@Test
	public void testAddTwoVectors() throws Exception {
		Vector2D v1 = new Vector2D(1, 2);
		Vector2D v2 = new Vector2D(3, 4);
		Vector2D v3 = v1.add(v2);
		assertEquals(4, v3.getX(), 0.05);
		assertEquals(6, v3.getY(), 0.05);
		assertEquals(7.2, v3.mag(), 0.5);
	}

	@Test
	public void testSubstractTwoVectors() throws Exception {
		Vector2D v1 = new Vector2D(1, 2);
		Vector2D v2 = new Vector2D(3, 4);
		Vector2D v3 = v1.sub(v2);
		assertEquals(-2, v3.getX(), 0.05);
		assertEquals(-2, v3.getY(), 0.05);
		assertEquals(2.83, v3.mag(), 0.05);
	}

	@Test
	public void testMultiplyVectorByScale() throws Exception {
		Vector2D v1 = new Vector2D(5, -8);
		Vector2D v2 = v1.scale(3);
		assertEquals(15, v2.getX(), 0.05);
		assertEquals(-24, v2.getY(), 0.05);
		assertEquals(28.3, v2.mag(), 0.5);
	}

	@Test
	public void testNormalizeAVector() throws Exception {
		Vector2D v1 = new Vector2D(5, -8);
		Vector2D v2 = v1.normalize();
		assertEquals(0.53, v2.getX(), 0.05);
		assertEquals(-0.84, v2.getY(), 0.05);
		assertEquals(1, v2.mag(), 0.05);
	}
}
