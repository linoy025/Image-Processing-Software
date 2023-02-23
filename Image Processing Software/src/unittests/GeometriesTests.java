package unittests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import geometries.*;

import primitives.*;

import java.util.*;

import geometries.Intersectable.GeoPoint;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class GeometriesTests {

	/**
	 * test method for
	 * {@link geometries.Geometries#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void findGeoIntersections() {
		Geometries list = new Geometries();
		Ray r = new Ray(new Point3D(3, 4, 2), new Vector(1, 2, 5));

		// =============== Boundary Values Tests ==================
		// BVA empty list
		assertEquals("list should be empty", list.findGeoIntersections(r), null);
		// BVA no intersections
		list.add(new Triangle(new Point3D(2, 0, 5), new Point3D(2, 3, 5), new Point3D(7, 2, 1)));
		list.add(new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(-1, 1, 1)));
		list.add(new Sphere(new Point3D(0, 0, 0), 4));
		assertEquals("intersections should not be in list", list.findGeoIntersections(r), null);
		// BVA one intersection
		list.add(new Triangle(new Point3D(4, 5, 2), new Point3D(5, 6, 7), new Point3D(2, 4, 3)));
		assertEquals("only one intersection should be in list", list.findGeoIntersections(r).size(), 1);

		// BVA all intersect
		Geometries secondList = new Geometries();
		secondList.add(new Sphere(new Point3D(3, 9, 6), 5));
		secondList.add(new Sphere(new Point3D(3, 5, 3), 6));
		secondList.add(new Plane(new Point3D(3, 4, 4), new Vector(1, 5, 4)));
		secondList.add(new Triangle(new Point3D(4, 5, 2), new Point3D(5, 6, 7), new Point3D(2, 4, 3)));
		assertEquals("5 intersections should be in list", secondList.findGeoIntersections(r).size(), 5);
		// ============ Equivalence Partitions Tests ==============
		// EP some intersect

		Geometries thirdList = new Geometries();

		thirdList.add(new Triangle(new Point3D(2, 0, 5), new Point3D(2, 3, 5), new Point3D(7, 2, 1)));
		thirdList.add(
				new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(-1, 1, 1)));
		thirdList.add(new Sphere(new Point3D(0, 0, 0), 4));
		thirdList.add(new Triangle(new Point3D(4, 5, 2), new Point3D(5, 6, 7), new Point3D(2, 4, 3)));
		thirdList.add(new Sphere(new Point3D(3, 9, 6), 5));
		thirdList.add(new Sphere(new Point3D(3, 5, 3), 6));
		thirdList.add(new Plane(new Point3D(3, 4, 4), new Vector(1, 5, 4)));
		thirdList.add(new Triangle(new Point3D(4, 5, 2), new Point3D(5, 6, 7), new Point3D(2, 4, 3)));

		// list.add(secondList);
		assertEquals("at least one intersection should be in list", thirdList.findGeoIntersections(r).size(), 6);

	}
}
