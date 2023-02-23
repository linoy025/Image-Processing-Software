package unittests;

import static org.junit.Assert.*;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;

import geometries.Intersectable.GeoPoint;
import geometries.Sphere;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class RayTests {

	@Test
	/**
	 * Test method for {@link primitives.Ray#findClosestPoint
	 * (primitives.List<Point3D>)}.
	 */
	    public void findClosestGeoPoint() {

	        List<GeoPoint> geoPointsList = new LinkedList<>();

	        Sphere sphere = new Sphere(new Point3D(1, 1, 1), 0);

	        GeoPoint geoPoint1 = new GeoPoint(sphere, new Point3D(1, 1, 1));
	        GeoPoint geoPoint2 = new GeoPoint(sphere, new Point3D(2, 2, 2));
	        GeoPoint geoPoint3 = new GeoPoint(sphere, new Point3D(3, 3, 3));

	        geoPointsList.add(geoPoint1);
	        geoPointsList.add(geoPoint2);
	        geoPointsList.add(geoPoint3);

	        Vector dirVector = new Vector(0, -0.5, 0);

	        // ============ Equivalence Partitions Tests ==============
	        //TC01: The closest point is in the middle of the list
	        Ray ray1 = new Ray(new Point3D(2, 2.5, 2), dirVector);
	        assertEquals("The point in the middle!!" ,geoPoint2, ray1.findClosestGeoPoint(geoPointsList));

	        // =============== Boundary Values Tests ==================
	        //TC10: The closest point is the first point in the list
	        Ray ray2 = new Ray(new Point3D(1, 1.25, 1), dirVector);
	        assertEquals ("The point is the first one!!" ,geoPoint1, ray2.findClosestGeoPoint(geoPointsList));

	        //TC11: The closest point is the last point in the list
	        Ray ray3 = new Ray(new Point3D(3, 3.5, 3), dirVector);
	        assertEquals( "The point is the last one!!" ,geoPoint3, ray3.findClosestGeoPoint(geoPointsList));

	        //TC12: The list is null
	        geoPointsList.clear();
	        assertNull("The list is empty!!" ,ray3.findClosestGeoPoint(geoPointsList));
	    }
		
		
		
		
		
		
}

















