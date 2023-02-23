/**
 * 
 */
package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.Tube;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * @author Elinoy Gidoni and Tamara Seban
 *
 */
public class TubeTests {

	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {

		Tube t = new Tube(new Ray(new Point3D(3, 5, 8), new Vector(4, 7, 6)), 4);
		// ============ Equivalence Partitions Tests ==============

		Vector n = t.getNormal(new Point3D(4, 2, 1));

		Vector v = new Vector(0.6736255682471604, 0.21987778192043828, -0.7056077910719512);
		assertEquals("getNormal() does not work correctly", n, v);

		// =============== Boundary Values Tests ==================
		// TC11: test zero vector from add

		try {
			Tube t1 = new Tube(new Ray(new Point3D(3, 4, 0), new Vector(2, -1, 0)), 4);
			t1.getNormal(new Point3D(2, 2, -3));
			fail("Vector 0 was counstructed!!!");
		} catch (IllegalArgumentException e) {
		}

	}

}
