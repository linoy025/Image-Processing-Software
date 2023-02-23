/**
 * 
 */
package unittests;

import static primitives.Util.isZero;
import static java.lang.System.out;
import static org.junit.Assert.*;

import org.junit.Test;

import primitives.Coordinate;
import primitives.Point3D;
import primitives.Vector;

/**
 * Unit tests for primitives.Vector class
 * 
 * @author Elinoy Gidoni and Tamara Seban
 */
public class VectorTests {

	/**
	 * Test method for
	 * {@link primitives.Vector#constructor(Coordinate x, Coordinate y, Coordinate z)}.
	 */
	@Test
	public void testConstructor1() {
		// ============ Equivalence Partitions Tests ==============

		// TC01: Correct Vector
		try {
			new Vector(new Coordinate(1), new Coordinate(1), new Coordinate(0));
		} catch (IllegalArgumentException e) {
			fail("Failed constructing a correct  vector");
		}
	}

	/**
	 * Test method for
	 * {@link primitives.Vector#constructor(double x, double y, double z)}.
	 */
	@Test
	public void testConstructor2() {
		// ============ Equivalence Partitions Tests ==============

		// TC01: Correct Vector
		try {
			new Vector(1.1, 1, 0);
		} catch (IllegalArgumentException e) {
			fail("Failed constructing a correct  vector");
		}
	}

	/**
	 * Test method for {@link primitives.Vector#constructor(Point3D p)}.
	 */
	@Test
	public void testConstructor3() {
		// ============ Equivalence Partitions Tests ==============

		// TC01: Correct Vector
		try {
			new Vector(new Point3D(1, 1, 1));
		} catch (IllegalArgumentException e) {
			fail("Failed constructing a correct  vector");
		}
	}

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	public void testAdd() {
		Vector v1 = new Vector(2.0, 2.0, 2.0);
		Vector v2 = new Vector(-1.0, -1.0, -1.0);
		Vector v4 = new Vector(-2.0, -2.0, -2.0);
		// ============ Equivalence Partitions Tests ==============

		// + -
		Vector v3 = v1.add(v2);
		assertTrue("add() does not work correctly", v3.equals(new Vector(1.0, 1.0, 1.0)));

		// - -
		Vector v5 = v2.add(v4);
		assertTrue("add() does not work correctly", v5.equals(new Vector(-3.0, -3.0, -3.0)));

		// + +
		Vector v6 = v3.add(v1);
		assertTrue("add() does not work correctly", v6.equals(new Vector(3.0, 3.0, 3.0)));

		// =============== Boundary Values Tests ==================
		// TC11: test zero vector from add
		assertThrows("add() from constructor  for  vectors does not throw an exception", IllegalArgumentException.class,
				() -> v1.add(v4));

		// Test operations with points and vectors
		Point3D p1 = new Point3D(1, 2, 3);
		if (!Point3D.ZERO.equals(p1.add(new Vector(-1, -2, -3))))
			out.println("ERROR: Point + Vector does not work correctly");

	}

	/**
	 * Test method for {@link primitives.Vector#subtract(primitives.Vector)}.
	 */
	@Test
	public void testSubtract() {

		Vector v1 = new Vector(2.0, 2.0, 2.0);
		Vector v2 = new Vector(-1.0, -1.0, -1.0);
		Vector v4 = new Vector(-2.0, -2.0, -2.0);
		// ============ Equivalence Partitions Tests ==============

		// + -
		Vector v3 = v1.subtract(v2);
		assertTrue("add() does not work correctly", v3.equals(new Vector(3.0, 3.0, 3.0)));

		// - -
		Vector v5 = v4.subtract(v2);
		assertTrue("add() does not work correctly", v5.equals(v2));

		// + +
		Vector v6 = v3.subtract(v1);
		assertTrue("add() does not work correctly", v6.equals(new Vector(1.0, 1.0, 1.0)));

		// =============== Boundary Values Tests ==================
		//
		assertThrows("add() from constructor for vectors does not throw an exception", IllegalArgumentException.class,
				() -> v1.subtract(v1));

		// Test operations with points and vectors
		Point3D p1 = new Point3D(1, 2, 3);
		if (!new Vector(1, 1, 1).equals(new Point3D(2, 3, 4).subtract(p1)))
			out.println("ERROR: Point - Point does not work correctly");

	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	public void testScale() {
		Vector v1 = new Vector(-1.0, -1.0, -1.0);

		// ============ Equivalence Partitions Tests ==============

		// - 1
		v1.scale(1);
		assertTrue(v1.equals(v1));

		// - +
		Vector v2 = v1.scale(3);
		assertTrue(v2.equals(new Vector(-3.0, -3.0, -3.0)));

		// - -
		Vector v3 = v2.scale(-2);
		assertTrue(v3.equals(new Vector(6.0, 6.0, 6.0)));

		// + +
		Vector v4 = v3.scale(2);
		assertTrue(v4.equals(new Vector(12.0, 12.0, 12.0)));

		// =============== Boundary Values Tests ==================
		// TC11: test zero vector from scale

		assertThrows("scale() from constructor for vectors does not throw an exception", IllegalArgumentException.class,
				() -> v1.scale(0));

	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	public void testCrossProduct() {
		Vector v1 = new Vector(1, 2, 3);

		// ============ Equivalence Partitions Tests ==============
		Vector v2 = new Vector(0, 3, -2);
		Vector vr = v1.crossProduct(v2);

		// TC01: Test that length of cross-product is proper (orthogonal vectors taken
		// for simplicity)
		assertEquals("crossProduct() wrong result length", v1.length() * v2.length(), vr.length(), 0.00001);

		// TC02: Test cross-product result orthogonality to its operands
		assertTrue("crossProduct() result is not orthogonal to 1st operand", isZero(vr.dotProduct(v1)));
		assertTrue("crossProduct() result is not orthogonal to 2nd operand", isZero(vr.dotProduct(v2)));

		// =============== Boundary Values Tests ==================
		// TC11: test zero vector from cross-product of co-lined vectors
		Vector v3 = new Vector(-2, -4, -6);
		assertThrows("crossProduct() for parallel vectors does not throw an exception", IllegalArgumentException.class,
				() -> v1.crossProduct(v3));

	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	public void testDotProduct() {
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(0, 3, -2);
		Vector v3 = new Vector(3, 2, 4);
		// ============ Equivalence Partitions Tests ==============

		// TC01: Test dot-product is proper
		assertEquals("dotProduct() wrong result", v3.dotProduct(v2), -2, 0.00001);
		assertEquals("dotProduct() wrong result", v1.dotProduct(v3), 19, 0.00001);
		assertEquals("dotProduct() wrong result", v1.dotProduct(v2), 0, 0.00001);

	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	public void testLengthSquared() {
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(0, 3, -2);
		// ============ Equivalence Partitions Tests ==============

		// TC01: Test lengthSquared is proper
		assertEquals("lengthSquared() wrong result", v1.lengthSquared(), 14, 0.00001);
		assertEquals("lengthSquared() wrong result", v2.lengthSquared(), 13, 0.00001);

	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	public void testLength() {
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(0, 3, -2);

		// ============ Equivalence Partitions Tests ==============

		// TC01: Test length is proper
		assertTrue("length() wrong result", v1.length() == Math.sqrt(14));
		assertTrue("length() wrong result", v2.length() == Math.sqrt(13));

	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	public void testNormalize() {
		double s = Math.sqrt(14);
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(1 / s, 2 / s, 3 / s);

		Vector v3 = new Vector(-1, -2, -3);
		Vector v4 = new Vector(-1 / s, -2 / s, -3 / s);

		// ============ Equivalence Partitions Tests ==============

		// TC01: Test normalize is proper

		assertTrue("normalize() wrong result", v1.normalize().equals(v2));
		assertTrue("normalize() wrong result", v3.normalize().equals(v4));
	}

	/**
	 * Test method for {@link primitives.Vector#normalized()}.
	 */
	@Test
	public void testNormalized() {
		double s = Math.sqrt(14);
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(1 / s, 2 / s, 3 / s);

		Vector v3 = new Vector(-1, -2, -3);
		Vector v4 = new Vector(-1 / s, -2 / s, -3 / s);

		// ============ Equivalence Partitions Tests ==============

		// TC01: Test normalize is proper

		assertTrue("normalized() wrong result", v1.normalized().equals(v2));
		assertTrue("normalized() wrong result", v3.normalized().equals(v4));
	}

}
