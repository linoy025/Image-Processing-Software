/**
 * 
 */
package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.Triangle;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * @author Elinoy Gidoni and Tamara Seban
 *
 */
public class TriangleTests {

	/**
	 * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here

		Point3D a = new Point3D(0, 0, 1);
		Point3D b = new Point3D(1, 0, 0);
		Point3D c = new Point3D(0, 1, 0);

		Triangle t = new Triangle(a, b, c);
		double sqrt3 = Math.sqrt(3);
		assertEquals("Bad normal to trinagle", new Vector(1 / sqrt3, 1 / sqrt3, 1 / sqrt3), t.getNormal(a));
	}

	@Test
	/**
	 * test method for
	 * {@link geometries.Triangle#findIntersections(primitives.Ray)}.
	 */
	public void findGeoIntersections() {
		// ============ Equivalence Partitions Tests ==============
		// EP inside triangle
		Point3D a = new Point3D(1.0, 3.0, 5.0);
		Point3D b = new Point3D(0, 4, 0);
		Point3D c = new Point3D(0, 2, 1);
		Triangle t = new Triangle(a, b, c);
		Point3D d = new Point3D(0.3441906873614191, 3.1000665188470062, 1.998824833702882);
		Ray r = new Ray(new Point3D(1.66, 0.82, 0), new Vector(new Point3D(-1.31, 2.27, 1.99)));
		assertEquals("one point should be in list", t.findGeoIntersections(r).get(0).point, d);
		// Ep test outside against edge
		Ray f = new Ray(new Point3D(-0.61, 0.07, -3.16), new Vector(-2.41, 3.47, 4.76));
		assertEquals("list should be empty! test outside against edge", t.findGeoIntersections(f), null);
		// EP test outside against vertex
		Ray e = new Ray(new Point3D(2.06, -0.91, 0), new Vector(-4.07, 3.65, 0));
		assertEquals("list should be empty! test outside against vertex", t.findGeoIntersections(e), null);

		// =============== Boundary Values Tests ==================

		// BVA in vertex//
		Ray w = new Ray(new Point3D(3.29, -1.55, 0), new Vector(-3.29, 3.55, 1));
		assertEquals("list should be empty! in vertex", t.findGeoIntersections(w), null);
		// BVA on edge//
		Ray z = new Ray(new Point3D(2.45, -1.05, 0), new Vector(-2.45, 3.87, 0.59));
		assertEquals("list should be empty! on edge", t.findGeoIntersections(z), null);
		// BVA on edge's continuation//
		Ray k = new Ray(new Point3D(2.73, -1.62, 0), new Vector(-2.73, 5.62, -1.59));
		assertEquals("list should be empty! on edge's continuation", t.findGeoIntersections(k), null);
	}

}
