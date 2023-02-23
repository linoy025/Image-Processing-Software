package unittests;

import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;
import static org.junit.Assert.*;

/**
 * @author Elinoy Gidoni and Tamara Seban
 *
 */

public class Point3DTests {
	Point3D p1 = new Point3D(1, 2, 3);
	Point3D p2 = new Point3D(1, 1, 1);

	/**
	 * Test method for {@link Point3D#subtract(Point3D)}
	 */
	@Test
	public void testSubtract() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: A simple case of subtract
		assertEquals("ERROR: subtract result is incorrect", new Vector(1, 1, 1), new Point3D(2, 3, 4).subtract(p1));

	}

	/**
	 * Test method for {@link Point3D#add(Vector)}
	 */
	@Test
	public void testAdd() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: A simple case of add
		assertEquals("ERROR: add result is incorrect", Point3D.ZERO, p1.add(new Vector(-1, -2, -3)));

	}

	/*
	 * /** Test method for {@link Point3D#distanceSquared(Point3D)}
	 */
	@Test
	public void testDistanceSquared() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: A simple case of a DistanceSquared
		assertEquals("ERROR: distanceSquared result is incorrect", 5, p1.distanceSquared(p2), 0.0001);
	}

	/**
	 * Test method for {@link Point3D#distance(Point3D)}
	 */
	@Test
	public void testDistance() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: A simple case of a Distance
		assertEquals("ERROR: distance result is incorrect", Math.sqrt(5), p1.distance(p2), 0.0001);
	}
}