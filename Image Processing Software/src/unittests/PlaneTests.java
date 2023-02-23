/**
 * 
 */
package unittests;

import static org.junit.Assert.*;
import org.junit.Test;

import geometries.Plane;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * @author Elinoy Gidoni and Tamara Seban
 *
 */
public class PlaneTests {

	/**
	 * Test method for
	 * {@link geometries.Plane#Constructor(primitives.Point3D, primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
	 * 
	 */
	@Test
	public void testConstructor() {

		// =============== Boundary Values Tests ==================
		Point3D a = new Point3D(2, 1, 0);
		Point3D b = new Point3D(5, 0, 3);
		Point3D c = new Point3D(3.5, 0.5, 1.5);

		// TC02: The first and second points are the same line
		try {
			new Plane(a, a, c);
			fail("Failed constructing a correct plane");
		} catch (IllegalArgumentException e) {
		}

		// TC01: The points were found on the same line try
		try {
			new Plane(a, b, c);
			fail("Failed constructing a correct plane");
		} catch (IllegalArgumentException e) {
		}

	}

	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormalPoint3D() {

		Point3D a = new Point3D(1, 2, 3);
		Point3D b = new Point3D(3, 2, 0);
		Point3D c = new Point3D(4, 5, 6);

		Plane plane = new Plane(a, b, c);

		// ============ Equivalence Partition Tests ==============

		Vector v = new Vector(0, 1, 0);
		Vector v1 = plane.getNormal();

		assertEquals("getNormal() wrong result", v1.length(), v.length(), 0.00001);

		// =============== Boundary Values Tests ==================

	}

	/**
	 * test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void findGeoIntersections() {

		Point3D a = new Point3D(-2, 0, 0);
		Point3D b = new Point3D(0, 0, 3);
		Point3D c = new Point3D(2, 0, 0);
		Plane p = new Plane(a, b, c);
		// ============ Equivalence Partitions Tests ==============
		// EP doesn't intersect plane
		Ray r = new Ray(new Point3D(0, 0, 3), new Vector(0, -2, -3));
		assertEquals("list should be empty! itersections with head of ray not included", p.findGeoIntersections(r), null);
		// EP intersects plane
		Ray e = new Ray(new Point3D(4, 2, 0), new Vector(-10, -2, 6));
		assertEquals("one point should be in list", p.findGeoIntersections(e).get(0).point, new Point3D(-6, 0, 6));
		
		
		// =============== Boundary Values Tests ==================

		// BVA ray is parallel and included
		Ray f = new Ray(new Point3D(0, 0, 0), new Vector(0, 0, 2.44));
		assertEquals("list should be empty!", p.findGeoIntersections(f), null);
		// BVA ray is parallel and isn't included
		Ray g = new Ray(new Point3D(2, -2, 0), new Vector(-4, 0, 0));
		assertEquals("list should be empty! ray is parallel and isn't included", p.findGeoIntersections(g), null);
		// BVA ray is orthogonal before
		Ray k = new Ray(new Point3D(-1.75, 1.19, 0), new Vector(-0.05, 3.3, 0));
		assertEquals("list should be empty! ray is orthogonal before", p.findGeoIntersections(k), null);
		// BVA ray is orthogonal in
		Ray q = new Ray(new Point3D(-2, 0, 0), new Vector(-0.11, -2.26, 0));
		assertEquals("list should be empty!ray is orthogonal in", p.findGeoIntersections(q), null);
		// BVA ray is orthogonal after
		Ray o = new Ray(new Point3D(-2.09, -0.88, 0), new Vector(-0.07, -1.73, 0));
		assertEquals("list should be empty! ray is orthogonal after", p.findGeoIntersections(o), null);
		// BVA Ray is neither orthogonal nor parallel to and begins at the plane
		Ray h = new Ray(new Point3D(0, 0, 6), new Vector(-2.45, -2.08, -6));
		assertEquals("list should be empty!" + "/n" + "Ray is neither orthogonal "
				+ "nor parallel to and begins at the plane", p.findGeoIntersections(h), null);
		// BVA Ray is neither orthogonal nor parallel to the plane and begins in
		// the same point which appears as reference point in the plane
		Ray v = new Ray(new Point3D(-2, 0, 0), new Vector(3.45, 0, 2));
		assertEquals("list should be empty!" + "/n"
				+ " Ray is neither orthogonal nor parallel to the plane and begins in the same "
				+ "point which appears as reference point in the plane", p.findGeoIntersections(v), null);

	}

}
















